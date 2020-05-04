package com.skynet.studyon.dto.inner

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class CoursesResponse(
        var id: String,
        var title: String?,
        var description: String?,
        var score: Int?,
        var cost: Int?,
        @JsonProperty("is_complete")
        var isComplete: Boolean
)