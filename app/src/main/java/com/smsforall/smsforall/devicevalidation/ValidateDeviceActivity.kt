package com.smsforall.smsforall.devicevalidation

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.smsforall.smsforall.BaseAppCompatActivity
import com.smsforall.smsforall.R
import com.smsforall.smsforall.receivesms.ProcessingNotificationsActivity
import com.smsforall.smsforall.util.executePermissionRequest
import com.smsforall.smsforall.util.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class ValidateDeviceActivity : BaseAppCompatActivity() {

    private val viewModel by viewModels<ValidateDeviceViewModel> { viewModelFactory }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val notificationSent = intent.extras?.getBoolean(SMS_MESSAGE_SENT) ?: false

            if (notificationSent) {
                intent.extras?.getString(SMS_NOTIFICATION_UID_KEY)?.let { smsNotificationUid ->
                    viewModel.attemptActivateDevice(smsNotificationUid)
                }
            } else {
                Timber.e("Error sending sms")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (appPreferences.deviceActivated) {
            startActivity(Intent(this, ProcessingNotificationsActivity::class.java))
            finish()
        } else {
            if (hasSendSMSPermission()) {
                replaceFragmentInActivity(obtainViewFragment(), R.id.mainActivityContentFrame)
            } else {
                requestPermissions()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, IntentFilter(ACTIVATE_DEVICE_INTENT_FILTER))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    private fun hasSendSMSPermission() =
        ContextCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED

    private fun obtainViewFragment() =
        supportFragmentManager.findFragmentById(R.id.mainActivityContentFrame)
            ?: ValidateDeviceFragment.newInstance()

    private fun requestPermissions() {
        when {
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.SEND_SMS
            ) -> {
                requestPermissionsRationale()
            }
            else -> {
                executePermissionRequest(
                    arrayOf(Manifest.permission.SEND_SMS),
                    PERMISSIONS_REQUEST_SEND_SMS
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
            PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    replaceFragmentInActivity(obtainViewFragment(), R.id.mainActivityContentFrame)
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.SEND_SMS
                        )
                    ) {
                        requestPermissionsRationale()
                    } else {
                        requestPermissionsRationaleExplanation()
                    }
                    return
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
                    arrayOf(Manifest.permission.SEND_SMS),
                    PERMISSIONS_REQUEST_SEND_SMS
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
        private const val PERMISSIONS_REQUEST_SEND_SMS = 0
        const val ACTIVATE_DEVICE_INTENT_FILTER = "com.smsforall.smsforall.ACTIVATE_DEVICE"
        const val SMS_MESSAGE_SENT = "sms_message_sent"
        const val SMS_NOTIFICATION_UID_KEY = "sms_notification_uid"
    }
}