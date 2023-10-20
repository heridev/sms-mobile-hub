package com.smsforall.smsforall.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on July 07, 2020
 */
data class UpdateStatusRequest(
    @field:Json(name = "sms_notification_uid")
    val smsNotificationUid: String,

    @field:Json(name = "status")
    val status: String,

    @field:Json(name = "additional_update_info")
    val additionalUpdateInfo: String,

    @field:Json(name = "firebase_token")
    val firebaseToken: String
)