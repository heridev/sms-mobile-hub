package com.smsforall.smsforall.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on June 29, 2020
 */
data class SMSHubResponse(
    @field:Json(name = "data")
    var data: Data
)

data class Data(
    @field:Json(name = "message")
    var message: String? = null,

    @field:Json(name = "error")
    var error: String? = null
)