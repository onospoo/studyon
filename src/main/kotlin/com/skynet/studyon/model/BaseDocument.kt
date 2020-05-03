package com.skynet.studyon.model

import org.springframework.data.annotation.Id

abstract class BaseDocument(

        @Id
        var id: String? = null
)