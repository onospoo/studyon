package com.skynet.studyon.dto

import com.skynet.studyon.model.inner.AccountService

class Account(

        val accountService: AccountService,

        val authorizationCode: String
)