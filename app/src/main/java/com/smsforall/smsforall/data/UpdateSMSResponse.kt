package com.smsforall.smsforall.data

import com.squareup.moshi.Json


/**
 * Created by Irvin Rosas on July 17, 2020
 */
data class UpdateSMSResponse(
    @Json(name = "data")
    val data: UpdateSMSResponseData?
)

data class UpdateSMSResponseData(
    @Json(name = "attributes")
    val attributes: Attributes?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)

data class Attributes(
    @Json(name = "assigned_to_mobile_hub")
    val assignedToMobileHub: AssignedToMobileHub?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "decorated_delivered_at")
    val decoratedDeliveredAt: String?,
    @Json(name = "decorated_status")
    val decoratedStatus: String?,
    @Json(name = "kind_of_notification")
    val kindOfNotification: String?,
    @Json(name = "processed_by_sms_mobile_hub")
    val processedBySmsMobileHub: ProcessedBySmsMobileHub?,
    @Json(name = "sms_content")
    val smsContent: String?,
    @Json(name = "sms_number")
    val smsNumber: String?,
    @Json(name = "sms_type")
    val smsType: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "unique_id")
    val uniqueId: String?
)