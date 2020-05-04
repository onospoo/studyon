package com.skynet.studyon.controller

import com.skynet.studyon.dto.Account
import com.skynet.studyon.dto.UserDto
import com.skynet.studyon.dto.UserWithRating
import com.skynet.studyon.model.User
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@Api("Users", description = "Апи для работы с пользователями")
@RequestMapping("/user")
@RestController
interface UserController {

    @ApiOperation("Регистрация нового пользователя")
    @PostMapping("/create")
    fun createUser(
            @ApiParam("Информация о пользователе")
            @RequestBody(required = true)
            user: UserDto
    ) : ResponseEntity<String>

    @ApiOperation("Получить всех пользователей")
    @GetMapping("/list")
    fun getUsersList() : ResponseEntity<List<User>>

    @ApiOperation("Получить пользователей отсортированных по рейтингу")
    @GetMapping("/rating")
    fun gerUsersListByRating() : ResponseEntity<List<UserWithRating>>

    @ApiOperation("Получить пользователя по ID")
    @GetMapping("/{id}")
    fun getUser(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String
    ) : ResponseEntity<User>

    @ApiOperation("Редактировать пользователя")
    @PutMapping("/{id}")
    fun updateUser(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String,

            @ApiParam("Информация о пользователе")
            @RequestBody(required = true)
            user: UserDto
    ) : ResponseEntity<String>

    @ApiOperation("Удалить пользователя")
    @DeleteMapping("/{id}")
    fun deleteUser(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String
    ) : ResponseEntity<String>

    @ApiOperation("Привязать учебный аккаунт")
    @PostMapping("/{id}/account")
    fun addAccountToUser(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String,

            @ApiParam("Информация об аккаунте")
            @RequestBody(required = true)
            accountList: List<Account>
    ) : ResponseEntity<Boolean>

    @ApiOperation("Добавить ачивку в работу пользователю")
    @PostMapping("/{id}/achievement/{achievementId}")
    fun addAchievementInWork(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String,

            @ApiParam("ID ачивки")
            @PathVariable(name = "achievementId", required = true)
            achievementId: String
    ) : ResponseEntity<Boolean>

    @ApiOperation("Информация об ачивка пользователя")
    @GetMapping("/{id}/achievement")
    fun getUserAchievements(
            @ApiParam("ID пользователя")
            @PathVariable(name = "id", required = true)
            id: String
    ) : ResponseEntity<HashMap<String, Boolean>>
}