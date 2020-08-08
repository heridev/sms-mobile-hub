package com.smsparatodos.smsparatodos.util

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Created by Irvin Rosas on June 30, 2020
 */
fun Fragment.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}