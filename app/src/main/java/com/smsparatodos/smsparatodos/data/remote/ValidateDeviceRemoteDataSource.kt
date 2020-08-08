package com.smsparatodos.smsparatodos.data.remote

import com.smsparatodos.smsparatodos.data.ActivateDeviceRequest
import com.smsparatodos.smsparatodos.data.SMSHubService
import com.smsparatodos.smsparatodos.data.ValidateDeviceDataSource
import com.smsparatodos.smsparatodos.data.ValidateDeviceRequest
import com.smsparatodos.smsparatodos.data.local.AppPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Irvin Rosas on June 29, 2020
 */
class ValidateDeviceRemoteDataSource @Inject constructor(
    private val smsHubService: SMSHubService,
    private val appPreferences: AppPreferences
) : ValidateDeviceDataSource {

    override fun validateDevice(
        deviceTokenCode: String,
        validateDeviceCallback: ValidateDeviceDataSource.ValidateDeviceCallback
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                smsHubService.validateDevice(
                    ValidateDeviceRequest(deviceTokenCode, appPreferences.firebaseToken)
                )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Timber.i("Response ${response.body()?.data?.message}")
                    validateDeviceCallback.onValidateDeviceSuccess()
                } else {
                    Timber.e("Response ${response.errorBody()?.toString()}")
                    validateDeviceCallback.onValidateDeviceError()
                }
            }
        }
    }

    override fun activateDevice(
        smsNotificationUid: String,
        activateDeviceCallback: ValidateDeviceDataSource.ActivateDeviceCallback
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =
                smsHubService.activateDevice(
                    ActivateDeviceRequest(
                        smsNotificationUid,
                        appPreferences.firebaseToken
                    )
                )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Timber.i("Response ${response.body()?.data?.message}")
                    activateDeviceCallback.onActivateDeviceSuccess()
                } else {
                    Timber.e("Response ${response.errorBody()?.toString()}")
                    activateDeviceCallback.onActivateDeviceError()
                }
            }
        }
    }
}