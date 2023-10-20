package com.smsforall.smsforall.devicevalidation

import com.smsforall.smsforall.data.ValidateDeviceDataSource
import com.smsforall.smsforall.data.remote.ValidateDeviceRemoteDataSource
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