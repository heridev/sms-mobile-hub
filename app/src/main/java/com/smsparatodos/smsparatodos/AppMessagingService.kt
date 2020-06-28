package com.smsparatodos.smsparatodos

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val from = remoteMessage.from
        Log.d(
            TAG,
            "Before checking the remoteMessage data: " + remoteMessage.data
        )
        if (remoteMessage.data.isNotEmpty()) {

            // Move this logic about data conversion into its own class
            Log.d(
                TAG,
                "Data: " + remoteMessage.data
            )
            val processedData =
                remoteMessage.data
            val smsNumber = processedData["sms_number"]
            val smsContent = processedData["sms_content"]
            val smsNotificationId = processedData["sms_notification_id"]
            val smsType = processedData["sms_type"]
            Log.d(TAG, "smsNumber: $smsNumber")
            Log.d(
                TAG,
                "smsContent: $smsContent"
            )
            val invalidSmsContent = smsContent == null || smsContent.isEmpty()
            val invalidPhoneNumber = smsNumber == null || smsNumber.isEmpty()
            if (invalidPhoneNumber || invalidSmsContent) {
                return
            }
            var validSmsType = "transactional"
            if (smsType != null && !smsType.isEmpty()) {
                validSmsType = smsType
            }
            val invalidNotificationId = ""
            var validSmsNotificationId = invalidNotificationId
            if (smsNotificationId != null && !smsNotificationId.isEmpty()) {
                validSmsNotificationId = smsNotificationId
            }
            if (validSmsType === "device_validation") {
                // Send a smsMessage and if it was successful
                // make http request to sms_mobile_hubs#activate after
                return
            }
            sendSMS(smsNumber, smsContent, validSmsNotificationId, validSmsType)

            // here we will trigger the SMS service and pass the values so this could be triggered
            // and once the sms is sent successfully we would request to our api
            // informing about the sent was successfully did or in case it failed
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendSMS(
            "+523121231517",
            "the onNewToken was triggered for this device, why?",
            "xxx",
            "transactional"
        )

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    // Move this logic into its own class
    private fun sendSMS(
        phoneNumber: String?,
        message: String?,
        smsNotificationId: String,
        smsType: String
    ) {
        val SENT = "SMS_SENT"
        val DELIVERED = "SMS_DELIVERED"
        val sentPI = PendingIntent.getBroadcast(
            this, 0,
            Intent(SENT), 0
        )
        val deliveredPI = PendingIntent.getBroadcast(
            this, 0,
            Intent(DELIVERED), 0
        )

        //---when the SMS has been sent---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(
                arg0: Context,
                arg1: Intent
            ) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(
                        baseContext,
                        "SMS enviado a " + decoratePhoneNumberDigits(phoneNumber),
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> Toast.makeText(
                        baseContext, "Generic failure",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_NO_SERVICE -> Toast.makeText(
                        baseContext, "No service",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_NULL_PDU -> Toast.makeText(
                        baseContext, "Null PDU",
                        Toast.LENGTH_SHORT
                    ).show()
                    SmsManager.RESULT_ERROR_RADIO_OFF -> Toast.makeText(
                        baseContext, "Radio off",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, IntentFilter(SENT))

        //---when the SMS has been delivered---
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(
                arg0: Context,
                arg1: Intent
            ) {
                when (resultCode) {
                    Activity.RESULT_OK -> Toast.makeText(
                        baseContext, "SMS delivered",
                        Toast.LENGTH_SHORT
                    ).show()
                    Activity.RESULT_CANCELED -> Toast.makeText(
                        baseContext, "SMS not delivered",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, IntentFilter(DELIVERED))
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI)
    }

    // Move this logic into its own class
    private fun decoratePhoneNumberDigits(phoneNumber: String?): String {
        var lastFourDigits = "" //substring containing last 4 characters
        var firstDigits = String()
        for (i in 0 until phoneNumber!!.length - 4) {
            val c = phoneNumber[i]
            firstDigits += "*"
        }
        lastFourDigits = if (phoneNumber.length > 4) {
            phoneNumber.substring(phoneNumber.length - 4)
        } else {
            phoneNumber
        }
        for (i in 0 until lastFourDigits.length) {
            val c = lastFourDigits[i]
            firstDigits += c
        }
        return firstDigits
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}