package com.smsparatodos.smsparatodos

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

class MainActivity : AppCompatActivity() {
    private val infoTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener { instanceIdResult ->
                val instanceId = instanceIdResult.id
                Log.d(TAG, "InstanceId: $instanceId")
            }

        FirebaseInstanceId.getInstance().instanceId
            .addOnSuccessListener(this@MainActivity) { instanceIdResult ->
                val newToken = instanceIdResult.token
                Log.e("newToken", newToken)
            }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
                Log.d("primero", "The onCreate() event")
            } else {
                Log.d("segundo", "The onCreate() event")
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            }
        } else {
            Log.d("granteeed", "The onCreate() event")
        }
    }

    private fun testingCode(): String {
        val input = "123456789" //input string
        Log.d("veamos", input)
        return input
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.d("onRequest", "The onCreate() event")
        val msgPermissionGranted = "permission granted"
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(msgPermissionGranted, "The onCreate() event")
                } else {
                    Toast.makeText(
                        applicationContext,
                        "SMS faild, please try again.", Toast.LENGTH_LONG
                    ).show()
                    return
                }
            }
        }
    }

    companion object {
        const val TAG = "SmsMobileHubActivity"
        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 0
    }
}