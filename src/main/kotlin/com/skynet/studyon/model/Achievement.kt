package com.skynet.studyon.model

import com.skynet.studyon.model.inner.Stage

class Achievement(
        val name: String,

        val reward: String,

        val isComplete: Boolean = false,

        val stages: List<Stage>,

        val logo: ByteArray?
) : BaseDocument()