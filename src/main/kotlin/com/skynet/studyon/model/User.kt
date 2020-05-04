package com.skynet.studyon.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.skynet.studyon.model.inner.AccountService
import com.skynet.studyon.model.inner.Gender
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate


@Document("user")
class User(

        var name: String,

        var dateOfBirth: LocalDate,

        var gender: Gender,

        var country: String,

        var city: String,

        var grade: String?,

        var email: String?,

        var zoomId: String?,

        @JsonIgnore
        val kt: BigDecimal = BigDecimal(0),

        val directions: HashSet<String> = hashSetOf(),

        val accounts: HashMap<AccountService, String> = hashMapOf(),

        var isTeacher: Boolean = false,

        @JsonIgnore
        val achievements: HashMap<String, Boolean> = hashMapOf()

) : BaseDocument()