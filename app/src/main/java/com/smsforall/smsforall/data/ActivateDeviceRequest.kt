package com.smsforall.smsforall.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on July 02, 2020
 */
data class ActivateDeviceRequest(
    @field:Json(name = "sms_notification_uid")
    val smsNotificationUid: String,

    @field:Json(name = "firebase_token")
    val firebaseToken: String
)