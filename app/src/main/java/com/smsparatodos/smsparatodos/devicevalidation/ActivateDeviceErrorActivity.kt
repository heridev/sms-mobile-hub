package com.smsparatodos.smsparatodos.devicevalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smsparatodos.smsparatodos.R
import kotlinx.android.synthetic.main.activity_activate_device_error.*

class ActivateDeviceErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activate_device_error)

        tryAgainButton.setOnClickListener { finish() }
    }
}