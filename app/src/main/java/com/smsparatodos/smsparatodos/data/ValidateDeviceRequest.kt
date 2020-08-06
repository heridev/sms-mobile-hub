package com.smsparatodos.smsparatodos.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on June 29, 2020
 */
data class ValidateDeviceRequest(
    @field:Json(name = "sms_mobile_hub")
    val smsMobileHub: SMSMobileHub
)

data class SMSMobileHub(
    @field:Json(name = "device_token_code")
    val deviceTokenCode: String,

    @field:Json(name = "firebase_token")
    val firebaseToken: String
)