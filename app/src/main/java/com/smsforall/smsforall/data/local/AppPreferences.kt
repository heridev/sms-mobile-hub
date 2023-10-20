package com.smsforall.smsforall.data.local

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Irvin Rosas on July 04, 2020
 */
class AppPreferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("${context.packageName}.preferences", Activity.MODE_PRIVATE)

    var firebaseToken: String
        get() = preferences.getString(FIREBASE_TOKEN, "") ?: ""
        set(value) = preferences.edit().putString(FIREBASE_TOKEN, value).apply()

    var deviceActivated: Boolean
        get() = preferences.getBoolean(DEVICE_ACTIVATED, false)
        set(value) = preferences.edit().putBoolean(DEVICE_ACTIVATED, value).apply()

    companion object {
        const val FIREBASE_TOKEN = "firebase_token"
        const val DEVICE_ACTIVATED = "device_activated"
    }
}