package com.smsforall.smsforall.data

/**
 * Created by Irvin Rosas on June 29, 2020
 */
interface ValidateDeviceDataSource {

    interface ValidateDeviceCallback {

        fun onValidateDeviceSuccess()

        fun onValidateDeviceError()
    }

    fun validateDevice(deviceTokenCode: String, validateDeviceCallback: ValidateDeviceCallback)

    interface ActivateDeviceCallback {

        fun onActivateDeviceSuccess()

        fun onActivateDeviceError()
    }

    fun activateDevice(smsNotificationUid: String, activateDeviceCallback: ActivateDeviceCallback)
}