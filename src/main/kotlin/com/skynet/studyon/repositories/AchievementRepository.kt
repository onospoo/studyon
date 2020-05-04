package com.skynet.studyon.repositories

import com.skynet.studyon.model.Achievement
import org.springframework.data.mongodb.repository.MongoRepository

interface AchievementRepository : MongoRepository<Achievement, String> {
}