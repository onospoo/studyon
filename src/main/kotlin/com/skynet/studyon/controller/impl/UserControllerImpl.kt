package com.skynet.studyon.controller.impl

import com.skynet.studyon.controller.UserController
import com.skynet.studyon.model.User
import com.skynet.studyon.service.StudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class UserControllerImpl(
        private val studyService: StudyService
) : UserController {

    override fun createUser(user: User): ResponseEntity<String> =
            ResponseEntity.ok(studyService.createUser(user))

}