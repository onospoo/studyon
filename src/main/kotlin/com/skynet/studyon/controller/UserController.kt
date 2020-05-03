package com.skynet.studyon.controller

import com.skynet.studyon.model.User
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RequestMapping("/user")
@RestController
interface UserController {

    @ApiOperation("Регистрация нового пользователя")
    @PostMapping("/create")
    fun createUser(
            @ApiParam("Информация о пользователе")
            @RequestBody(required = true)
            user: User
    ) : ResponseEntity<String>
}