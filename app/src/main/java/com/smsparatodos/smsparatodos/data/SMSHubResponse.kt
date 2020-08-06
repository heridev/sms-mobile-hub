package com.smsparatodos.smsparatodos.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on June 29, 2020
 */
data class SMSHubResponse(
    @field:Json(name = "data")
    val data: Data
)

data class Data(
    @field:Json(name = "message")
    val message: String,

    @field:Json(name = "error")
    val error: String
)