package com.smsparatodos.smsparatodos.devicevalidation

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smsparatodos.smsparatodos.R
import com.smsparatodos.smsparatodos.data.ValidateDeviceDataSource
import javax.inject.Inject

class ValidateDeviceViewModel @Inject constructor(
    private val validateDeviceRepository: ValidateDeviceRepository
) : ViewModel() {

    private val _showMessage = MutableLiveData<Int>()
    val showMessage: LiveData<Int> = _showMessage

    private val _validatePinSuccessEvent = MutableLiveData<Unit>()
    val validatePinSuccessEvent: LiveData<Unit> = _validatePinSuccessEvent

    private val _validatePinErrorEvent = MutableLiveData<Unit>()
    val validatePinErrorEvent: LiveData<Unit> = _validatePinErrorEvent

    private val _activateDeviceSuccessEvent = MutableLiveData<Unit>()
    val activateDeviceSuccessEvent: LiveData<Unit> = _activateDeviceSuccessEvent

    private val _activateDeviceErrorEvent = MutableLiveData<Unit>()
    val activateDeviceErrorEvent: LiveData<Unit> = _activateDeviceErrorEvent

    fun attemptValidateDevice(deviceTokenCode: String) {
        if (deviceTokenCode.isEmpty()) return

        validateDeviceRepository.validateDevice(
            deviceTokenCode,
            object : ValidateDeviceDataSource.ValidateDeviceCallback {
                override fun onValidateDeviceSuccess() {
                    showMessage(R.string.label_sms_successfully_validated)
                    _validatePinSuccessEvent.postValue(Unit)
                }

                override fun onValidateDeviceError() {
                    showMessage(R.string.error_invalid_device)
                    _validatePinErrorEvent.postValue(Unit)
                }
            })
    }

    fun attemptActivateDevice(smsNotificationUid: String) {
        validateDeviceRepository.activateDevice(
            smsNotificationUid,
            object : ValidateDeviceDataSource.ActivateDeviceCallback {
                override fun onActivateDeviceSuccess() {
                    showMessage(R.string.label_sms_successfully_activated)
                    _activateDeviceSuccessEvent.postValue(Unit)
                }

                override fun onActivateDeviceError() {
                    showMessage(R.string.error_invalid_device)
                    _activateDeviceErrorEvent.postValue(Unit)
                }
            })
    }

    fun showMessage(@StringRes resId: Int) {
        _showMessage.postValue(resId)
    }
}