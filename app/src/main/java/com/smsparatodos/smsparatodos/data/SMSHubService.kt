package com.smsparatodos.smsparatodos.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

/**
 * Created by Irvin Rosas on June 29, 2020
 */
interface SMSHubService {
    @POST("v1/sms_mobile_hubs/validate")
    suspend fun validateDevice(@Body validateDeviceRequest: ValidateDeviceRequest): Response<SMSHubResponse>

    @POST("v1/sms_mobile_hubs/activate")
    suspend fun activateDevice(@Body activateDeviceRequest: ActivateDeviceRequest): Response<SMSHubResponse>

    @PUT("v1/sms_notifications/update_status")
    suspend fun updateNotificationStatus(@Body updateStatusRequest: UpdateStatusRequest): Response<UpdateSMSResponse>
}