package com.smsparatodos.smsparatodos.data

import com.squareup.moshi.Json

/**
 * Created by Irvin Rosas on July 17, 2020
 */
data class AssignedToMobileHub(
    @Json(name = "data")
    val data: AssignedToMobileHubData?
)

data class AssignedToMobileHubData(
    @Json(name = "attributes")
    val attributes: AssignedToMobileHubAttributes?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "type")
    val type: String?
)

data class AssignedToMobileHubAttributes(
    @Json(name = "country_international_code")
    val countryInternationalCode: String?,
    @Json(name = "device_name")
    val deviceName: String?,
    @Json(name = "device_number")
    val deviceNumber: String?,
    @Json(name = "friendly_status_name")
    val friendlyStatusName: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "temporal_password")
    val temporalPassword: String?,
    @Json(name = "uuid")
    val uuid: String?
)