package com.skynet.studyon.model

import com.skynet.studyon.model.inner.AccountService

class Account(

        val accountService: AccountService,

        val link: String
) : BaseDocument()