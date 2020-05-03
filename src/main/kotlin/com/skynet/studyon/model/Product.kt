package com.skynet.studyon.model


class Product(

        val name: String,

        val descrtiption: String,

        val price: String,

        val image: ByteArray?

) : BaseDocument()