package com.skynet.studyon.service

import com.skynet.studyon.model.User
import com.skynet.studyon.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class StudyService(
        private val userRepository: UserRepository
) {

    /**
     * Работа с пользователями
     */
    fun createUser(user: User) : String {
        userRepository.save(user)

        return user.id ?: "11"
    }
}