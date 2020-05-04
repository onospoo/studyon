package com.skynet.studyon.controller.impl

import com.skynet.studyon.controller.UserController
import com.skynet.studyon.dto.Account
import com.skynet.studyon.dto.UserDto
import com.skynet.studyon.dto.UserWithRating
import com.skynet.studyon.model.User
import com.skynet.studyon.service.StudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class UserControllerImpl(
        private val studyService: StudyService
) : UserController {

    override fun createUser(user: UserDto): ResponseEntity<String> =
            ResponseEntity.ok(studyService.createUser(user))

    override fun getUsersList(): ResponseEntity<List<User>> =
            ResponseEntity.ok(studyService.getUsersList())

    override fun gerUsersListByRating(): ResponseEntity<List<UserWithRating>> =
            ResponseEntity.ok(studyService.getUserListByRating())

    override fun getUser(id: String): ResponseEntity<User> =
            ResponseEntity.ok(studyService.getUserById(id))

    override fun updateUser(id: String, user: UserDto): ResponseEntity<String> =
            ResponseEntity.ok(studyService.updateUser(id, user))

    override fun deleteUser(id: String): ResponseEntity<String> =
            ResponseEntity.ok(studyService.deleteUser(id))

    override fun addAccountToUser(id: String, accountList: List<Account>): ResponseEntity<Boolean> =
            ResponseEntity.ok(studyService.addAccountToUser(id, accountList))

    override fun addAchievementInWork(id: String, achievementId: String): ResponseEntity<Boolean> =
            ResponseEntity.ok(studyService.addAchievementInWorkToUser(id, achievementId))

    override fun getUserAchievements(id: String): ResponseEntity<HashMap<String, Boolean>> =
            ResponseEntity.ok(studyService.getUserAchievements(id))
}