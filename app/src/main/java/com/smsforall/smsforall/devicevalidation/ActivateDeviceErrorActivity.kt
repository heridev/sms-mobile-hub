package com.smsforall.smsforall.devicevalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smsforall.smsforall.R
import kotlinx.android.synthetic.main.activity_activate_device_error.*

class ActivateDeviceErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_device_error)

        tryAgainButton.setOnClickListener { finish() }
    }
}