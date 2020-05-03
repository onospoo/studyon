package com.skynet.studyon.dto

import com.skynet.studyon.model.User
import com.skynet.studyon.model.inner.Gender
import java.time.LocalDate

data class UserDto(
        val name: String?,
        val dateOfBirth: LocalDate?,
        val gender: Gender?,
        val country: String?,
        val city: String?,
        val grade: String?,
        val isTeacher: Boolean = false
) {

    fun toEntity(): User =
            User(
                    name = this.name ?: "",
                    dateOfBirth = this.dateOfBirth ?: LocalDate.now(),
                    gender = this.gender ?: Gender.MAN,
                    country = this.country ?: "",
                    city = this.city ?: "",
                    grade = this.grade,
                    isTeacher = this.isTeacher
            )
}