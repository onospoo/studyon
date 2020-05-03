package com.skynet.studyon.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

abstract class BaseDocument(

        @Id
        var id: ObjectId = ObjectId()

) {
        @JsonProperty("id")
        fun getId() = id.toString()
}