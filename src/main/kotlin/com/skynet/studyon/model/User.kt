package com.skynet.studyon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.skynet.studyon.model.inner.Gender
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate


@Document("user")
class User(

     var name: String,

     var dateOfBirth: LocalDate,

     var gender: Gender,

     var country: String,

     var city: String,

     var grade: String?,

     val interests: List<String> = mutableListOf(),

     @JsonIgnore
     val accounts: List<Account> = mutableListOf(),

     var isTeacher: Boolean = false,

     @JsonIgnore
     val achievements: List<Achievement> = mutableListOf()

) : BaseDocument()