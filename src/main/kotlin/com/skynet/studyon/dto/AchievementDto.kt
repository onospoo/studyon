package com.skynet.studyon.dto

import com.skynet.studyon.model.Achievement
import com.skynet.studyon.model.inner.Stage
import java.math.BigDecimal

data class AchievementDto(
        val name: String,
        val description: String,
        val reward: BigDecimal,
        val stages: List<Stage>,
        val logoLink: String?
) {

    fun toEntity() : Achievement =
            Achievement(
                    name = this.name,
                    description = this.description,
                    reward = this.reward,
                    stages = this.stages,
                    logoLink = this.logoLink
            )
}