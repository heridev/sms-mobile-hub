package com.smsparatodos.smsparatodos.devicevalidation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smsparatodos.smsparatodos.R
import kotlinx.android.synthetic.main.activity_pin_validation_error.*

class PinValidationErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_validation_error)

        returnButton.setOnClickListener { finish() }
    }
}