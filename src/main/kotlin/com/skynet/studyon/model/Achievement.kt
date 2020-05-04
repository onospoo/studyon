package com.skynet.studyon.model

import com.skynet.studyon.model.inner.Stage
import java.math.BigDecimal

class Achievement(
        var name: String,

        var description: String,

        var reward: BigDecimal,

        val stages: List<Stage>,

        var logoLink: String?
) : BaseDocument()