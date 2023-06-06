package com.smsforall.smsforall.devicevalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smsforall.smsforall.R
import kotlinx.android.synthetic.main.activity_pin_validation_error.*

class PinValidationErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_validation_error)

        returnButton.setOnClickListener { finish() }
    }
}