package com.smsparatodos.smsparatodos.devicevalidation

import com.smsparatodos.smsparatodos.data.ValidateDeviceDataSource
import com.smsparatodos.smsparatodos.data.remote.ValidateDeviceRemoteDataSource
import javax.inject.Inject

/**
 * Created by Irvin Rosas on June 30, 2020
 */
class ValidateDeviceRepository @Inject constructor(
    private val validateDeviceRemoteDataSource: ValidateDeviceRemoteDataSource
) : ValidateDeviceDataSource {

    override fun validateDevice(
        deviceTokenCode: String,
        validateDeviceCallback: ValidateDeviceDataSource.ValidateDeviceCallback
    ) {
        validateDeviceRemoteDataSource.validateDevice(deviceTokenCode, validateDeviceCallback)
    }

    override fun activateDevice(
        smsNotificationUid: String,
        activateDeviceCallback: ValidateDeviceDataSource.ActivateDeviceCallback
    ) {
        validateDeviceRemoteDataSource.activateDevice(smsNotificationUid, activateDeviceCallback)
    }
}