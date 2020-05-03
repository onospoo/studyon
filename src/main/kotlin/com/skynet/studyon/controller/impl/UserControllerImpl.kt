package com.skynet.studyon.controller.impl

import com.skynet.studyon.controller.UserController
import com.skynet.studyon.dto.UserDto
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

    override fun getUser(id: String): ResponseEntity<User> =
            ResponseEntity.ok(studyService.getUserById(id))

    override fun updateUser(id: String, user: UserDto): ResponseEntity<String> =
            ResponseEntity.ok(studyService.updateUser(id, user))

    override fun deleteUser(id: String): ResponseEntity<String> =
            ResponseEntity.ok(studyService.deleteUser(id))
}