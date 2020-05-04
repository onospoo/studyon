package com.skynet.studyon.controller.impl

import com.skynet.studyon.controller.AchievementController
import com.skynet.studyon.dto.AchievementDto
import com.skynet.studyon.model.Achievement
import com.skynet.studyon.service.StudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController


@RestController
class AchievementControllerImpl(
        private val studyService: StudyService
) : AchievementController {

    override fun createAchievement(achievement: AchievementDto): ResponseEntity<String> =
            ResponseEntity.ok(studyService.createAchievement(achievement))

    override fun getAchievementList(): ResponseEntity<List<Achievement>> =
            ResponseEntity.ok(studyService.getAchievementList())

    override fun getAchievementById(id: String): ResponseEntity<Achievement> =
            ResponseEntity.ok(studyService.getAchievementById(id))
}