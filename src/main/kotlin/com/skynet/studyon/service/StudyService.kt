package com.skynet.studyon.service

import com.skynet.studyon.dto.UserDto
import com.skynet.studyon.exception.BusinessException
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
    fun createUser(userDto: UserDto) : String {
        val user = userDto.toEntity()
        userRepository.save(user)

        return user.id.toString()
    }

    fun updateUser(id: String, userDto: UserDto) : String {
        userRepository
            .findById(id)
            .map {user ->
                userDto.name?.let { user.name = it }
                userDto.dateOfBirth?.let { user.dateOfBirth = it }
                userDto.gender?.let { user.gender = it }
                userDto.country?.let { user.country = it }
                userDto.city?.let { user.city = it }
                userDto.grade?.let { user.grade = it }
                userDto.isTeacher?.let { user.isTeacher = it }

                userRepository.save(user)
            }.orElseThrow {
                BusinessException ("Пользователя с таким id не существует")
            }

        return id
    }

    fun deleteUser(id: String) : String {
        userRepository.deleteById(id)
        return id
    }

    fun getUsersList() : List<User> =
            userRepository.findAll()

    fun getUserById(id: String) : User =
            userRepository
                    .findById(id)
                    .orElseThrow {
                        BusinessException("Пользователя с таким id не существует")
                    }
}