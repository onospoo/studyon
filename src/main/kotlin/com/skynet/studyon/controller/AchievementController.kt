package com.skynet.studyon.controller

import com.skynet.studyon.dto.AchievementDto
import com.skynet.studyon.model.Achievement
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api("Achievements", description = "Апи для работы с ачивками")
@RequestMapping("/achievements")
@RestController
interface AchievementController {

    @ApiOperation("Добавить ачивку")
    @PostMapping("/create")
    fun createAchievement(
            @ApiParam("Информация об ачивке")
            @RequestBody(required = true)
            achievement: AchievementDto
    ) : ResponseEntity<String>

    @ApiOperation("Получить все ачивки")
    @GetMapping("/list")
    fun getAchievementList() : ResponseEntity<List<Achievement>>

    @ApiOperation("Получить ачивку по ID")
    @GetMapping("/{id}")
    fun getAchievementById(
            @ApiParam("ID ачивки")
            @PathVariable(name = "id", required = true)
            id: String
    ) : ResponseEntity<Achievement>
}