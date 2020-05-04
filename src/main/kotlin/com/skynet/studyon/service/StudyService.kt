package com.skynet.studyon.service

import com.skynet.studyon.dto.*
import com.skynet.studyon.dto.inner.CoursesResponse
import com.skynet.studyon.exception.BusinessException
import com.skynet.studyon.model.Achievement
import com.skynet.studyon.model.User
import com.skynet.studyon.model.inner.AccountService
import com.skynet.studyon.repositories.AchievementRepository
import com.skynet.studyon.repositories.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class StudyService(
        private val userRepository: UserRepository,
        private val achievementRepository: AchievementRepository,
        private val restTemplate: RestTemplate
) {

    @Value("\${app.python-service}")
    lateinit var uriPython: String

    /**
     * Кэш пройденных курсов
     */
    val courseCache: HashMap<String, MutableSet<String>> = hashMapOf()

    /**
     * Работа с пользователями
     */
    fun createUser(userDto: UserDto) : String {
        val user = userDto.toEntity()
        userRepository.save(user)

        return user.id.toString()
    }

    fun updateUser(id: String, userDto: UserDto) : String {
        userRepository
            .findById(id)
            .map {user ->
                userDto.name?.let { user.name = it }
                userDto.dateOfBirth?.let { user.dateOfBirth = it }
                userDto.gender?.let { user.gender = it }
                userDto.country?.let { user.country = it }
                userDto.city?.let { user.city = it }
                userDto.email?.let { user.email = it }
                userDto.zoomId?.let { user.zoomId = it }
                userDto.grade?.let { user.grade = it }
                userDto.directions?.let { directions ->
                    directions.forEach {
                        user.directions.add(it)
                    }
                }
                userDto.isTeacher?.let { user.isTeacher = it }

                userRepository.save(user)
            }.orElseThrow {
                BusinessException ("Пользователя с таким id не существует")
            }

        return id
    }

    fun deleteUser(id: String) : String {
        userRepository.deleteById(id)
        return id
    }

    fun getUsersList() : List<User> =
            userRepository.findAll()

    fun getUserListByRating() : List<UserWithRating> =
            getUsersList()
                    .map {
                        UserWithRating(
                                id = it.id.toString(),
                                name = it.name,
                                rating = getUserCountOfTasks(it.accounts[AccountService.STEPIK]!!)
                        )
                    }.toList()
                    .sortedByDescending { it.rating }


    fun getUserById(id: String) : User =
            userRepository
                    .findById(id)
                    .orElseThrow {
                        BusinessException("Пользователя с таким id не существует")
                    }

    fun addAccountToUser(id: String, accountList: List<Account>) : Boolean {
        userRepository
                .findById(id)
                .map { user ->
                    accountList.forEach {
                        user.accounts[it.accountService] = it.authorizationCode
                    }
                    userRepository.save(user)
                }.orElseThrow {
                    BusinessException("Пользователя с таким id не существует")
                }

        return true
    }

    fun addAchievementInWorkToUser(userId: String, achievementId: String) : Boolean {
        achievementRepository
                .findById(achievementId)
                .map {
                    userRepository
                            .findById(userId)
                            .map {
                                if (it.achievements.containsKey(achievementId)) {
                                    throw BusinessException("Пользователь уже выполняет данную ачивку")
                                } else {
                                    it.achievements[achievementId] = false
                                    userRepository.save(it)
                                }
                            }.orElseThrow {
                                BusinessException("Пользователя с таким id не существует")
                            }
                }.orElseThrow {
                    BusinessException("Ачивки с таким id не существует")
                }

        return true
    }

    fun getUserAchievements(userId: String) :HashMap<String, Boolean> {
        val user = getUserById(userId)
        if (user.accounts.containsKey(AccountService.STEPIK)) {
            user.achievements.map { (k, _) ->
                user.achievements[k] = achievementRepository
                        .findById(k)
                        .map {
                            it.checkProgress(user)
                        }.orElse(false)
            }
            userRepository.save(user)
        }

        return user.achievements
    }

    /**
     * Работа с ачивками
     */
    fun createAchievement(achievementDto: AchievementDto) : String {
        val achievement = achievementDto.toEntity()
        achievementRepository.save(achievement)

        return achievement.id.toString()
    }

    fun getAchievementList() : List<Achievement> =
            achievementRepository.findAll()

    fun getAchievementById(id: String) : Achievement =
            achievementRepository
                    .findById(id)
                    .orElseThrow { BusinessException("Ачивки с таким id не существует") }

    fun Achievement.checkProgress(user: User) : Boolean {
        val userList = courseCache.getOrPut(user.id.toString()){ mutableSetOf() }
        var isComplete = true
        val listId = stages.map{it.id}

        if ( listId.all { it in userList }) {
            return isComplete
        } else {
            val result = getProgress(user.accounts[AccountService.STEPIK]!!, listId)
            result.forEach {
                if (it.isComplete) {
                    userList.add(it.id)
                } else {
                    isComplete = false
                }
            }
        }

        return isComplete
    }

    private fun getProgress(userCode: String, courseList: List<String>) : List<CoursesResponse> {
        val uri = "$uriPython/api/v1/courses"
        val request = AchievementRequest(
                provider = "stepik",
                token = userCode,
                ids = courseList,
                columns = listOf("id", "is_complete")
        )
        val response: AchievementResponse? = restTemplate.postForObject(
                uri, request, AchievementResponse::class.java
        )
        return response?.courses ?: listOf()
    }

    private fun getUserCountOfTasks(userCode: String) : Int {
        val uri = "$uriPython/api/v1/courses"
        val request = AchievementRequest(
                provider = "stepik",
                token = userCode,
                ids = listOf(),
                columns = listOf("id", "score")
        )
        val response: AchievementResponse? = restTemplate.postForObject(
                uri, request, AchievementResponse::class.java
        )

        return response!!.courses.map { it.score!! }.sum()
    }

}