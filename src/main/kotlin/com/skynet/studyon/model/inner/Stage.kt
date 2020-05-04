package com.skynet.studyon.model.inner

class Stage(
        val id: String,

        val type: StageType,

        val name: String,

        val description: String,

        val platform: AccountService?
)