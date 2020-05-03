//package com.skynet.studyon.mappers
//
//import com.skynet.studyon.dto.UserDto
//import com.skynet.studyon.model.User
//import com.skynet.studyon.model.inner.Gender
//import java.time.LocalDate
//
//fun UserDto.toUser() : User =
//        User(
//                name = this.name ?: "",
//                dateOfBirth = this.dateOfBirth ?: LocalDate.now(),
//                gender = this.gender ?: Gender.MAN,
//                country = this.country ?: "",
//                city = this.city ?: "",
//                grade = this.grade,
//                isTeacher = this.isTeacher
//        )