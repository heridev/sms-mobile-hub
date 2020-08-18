package com.smsparatodos.smsparatodos.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on August 17, 2020
 */
data class PostReceivedMessageRequest(
    @field:Json(name = "sms_number")
    val phoneNumber: String,

    @field:Json(name = "sms_content")
    val message: String,

    @field:Json(name = "firebase_token")
    val firebaseToken: String
)