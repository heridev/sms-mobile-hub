package com.smsforall.smsforall.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on June 29, 2020
 */
data class ValidateDeviceRequest(
    @field:Json(name = "device_token_code")
    val deviceTokenCode: String,

    @field:Json(name = "firebase_token")
    val firebaseToken: String
)