package com.skynet.studyon.service

import com.skynet.studyon.dto.Account
import com.skynet.studyon.dto.AchievementDto
import com.skynet.studyon.dto.UserDto
import com.skynet.studyon.exception.BusinessException
import com.skynet.studyon.model.Achievement
import com.skynet.studyon.model.User
import com.skynet.studyon.model.inner.AccountService
import com.skynet.studyon.repositories.AchievementRepository
import com.skynet.studyon.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class StudyService(
        private val userRepository: UserRepository,
        private val achievementRepository: AchievementRepository
) {

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

    fun getUserAchievements(userId: String) :HashMap<String, Boolean> =
            userRepository
                    .findById(userId)
                    .map { it.achievements }
                    .orElse(hashMapOf())

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
        stages.forEach {
            if (!userList.contains(it.id))  {
                val result = getProgress(user.accounts[AccountService.STEPIK]!!, it.id)
                if (result) {
                    userList.add(it.id)
                } else {
                    isComplete = false
                }
            }
        }

        return isComplete
    }

    private fun getProgress(userCode: String, courseId: String) = true
}