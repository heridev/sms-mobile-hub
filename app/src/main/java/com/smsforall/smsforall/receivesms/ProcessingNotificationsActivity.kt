package com.smsforall.smsforall.receivesms

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.smsforall.smsforall.R
import com.smsforall.smsforall.util.executePermissionRequest

class ProcessingNotificationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_processing_notifications)

        if (!hasReceiveSMSPermission()) {
            requestPermissions()
        }
    }

    private fun hasReceiveSMSPermission() =
        ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.RECEIVE_SMS
            ) -> {
                requestPermissionsRationale()
            }
            else -> {
                executePermissionRequest(
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    PERMISSIONS_REQUEST_RECEIVE_SMS
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_DENIED
                ) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.RECEIVE_SMS
                        )
                    ) {
                        requestPermissionsRationale()
                    } else {
                        requestPermissionsRationaleExplanation()
                    }
                }
            }
        }
    }

    private fun requestPermissionsRationale() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.label_permissions)
            .setMessage(R.string.label_permissions_message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                executePermissionRequest(
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    PERMISSIONS_REQUEST_RECEIVE_SMS
                )
            }
            .create()
            .show()
    }

    private fun requestPermissionsRationaleExplanation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.label_permissions)
            .setMessage(R.string.label_permissions_message_mandatory)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                finish()
            }
            .create()
            .show()
    }

    companion object {
        private const val PERMISSIONS_REQUEST_RECEIVE_SMS = 100
    }
}