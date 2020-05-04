package com.skynet.studyon.dto

data class AchievementRequest(
        var provider: String?,
        var token: String?,
        var ids: List<String>?,
        var columns: List<String>?
)