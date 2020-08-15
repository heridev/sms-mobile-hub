package com.smsparatodos.smsparatodos.devicevalidation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.smsparatodos.smsparatodos.BaseFragment
import com.smsparatodos.smsparatodos.R
import com.smsparatodos.smsparatodos.sms.ProcessingNotificationsActivity
import com.smsparatodos.smsparatodos.util.toast
import kotlinx.android.synthetic.main.add_device_fragment.*

class ValidateDeviceFragment : BaseFragment() {

    private val viewModel by activityViewModels<ValidateDeviceViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_device_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateDeviceButton.setOnClickListener {
            viewModel.attemptValidateDevice(devicePinView.text.toString())
        }

        with(viewModel) {
            showMessage.observe(viewLifecycleOwner, Observer { stringRes ->
                toast(stringRes)
            })

            validatePinSuccessEvent.observe(viewLifecycleOwner, Observer {
                textView.text = getString(R.string.label_sending_test_sms)
                textInputLayout.visibility = GONE
                devicePinView.visibility = GONE
                validateDeviceButton.visibility = GONE
            })

            validatePinErrorEvent.observe(viewLifecycleOwner, Observer {
                startActivity(Intent(context, PinValidationErrorActivity::class.java))
            })

            activateDeviceSuccessEvent.observe(viewLifecycleOwner, Observer {
                appPreferences.deviceActivated = true
                startActivity(Intent(context, ProcessingNotificationsActivity::class.java))
                activity?.finish()
            })

            activateDeviceErrorEvent.observe(viewLifecycleOwner, Observer {
                textView.text = getString(R.string.label_enter_activation_pin_message)
                textInputLayout.visibility = VISIBLE
                devicePinView.visibility = VISIBLE
                validateDeviceButton.visibility = VISIBLE

                startActivity(Intent(context, ActivateDeviceErrorActivity::class.java))
            })
        }
    }

    companion object {
        fun newInstance() = ValidateDeviceFragment()
    }
}