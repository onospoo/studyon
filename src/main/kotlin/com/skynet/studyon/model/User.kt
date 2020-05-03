package com.skynet.studyon.model

import com.skynet.studyon.model.inner.Gender
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document("user")
class User(

     val name: String,

     val dateOfBirth: LocalDate,

     val gender: Gender,

     val country: String,

     val city: String,

     val grade: String,

     val interests: List<String>,

     val accounts: List<Account>,

     val isTeacher: Boolean = false,

     val achievements: List<Achievement>
) : BaseDocument()