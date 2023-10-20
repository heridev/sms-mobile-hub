package com.smsforall.smsforall.firebase

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.smsforall.smsforall.data.SMSHubService
import com.smsforall.smsforall.data.local.AppPreferences
import com.smsforall.smsforall.devicevalidation.ValidateDeviceActivity.Companion.ACTIVATE_DEVICE_INTENT_FILTER
import com.smsforall.smsforall.devicevalidation.ValidateDeviceActivity.Companion.SMS_MESSAGE_SENT
import com.smsforall.smsforall.devicevalidation.ValidateDeviceActivity.Companion.SMS_NOTIFICATION_UID_KEY
import com.smsforall.smsforall.sendsms.SendSMSWorkManager
import dagger.android.AndroidInjection
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var appPreferences: AppPreferences

    @Inject
    lateinit var smsHubService: SMSHubService

    private var receiver: BroadcastReceiver? = null

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        receiver ?: return

        try {
            unregisterReceiver(receiver)
        } catch (exception: Exception) {
            Timber.d(exception)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {

            Timber.d("Remote message data: ${remoteMessage.data}")

            val processedData = remoteMessage.data

            val smsNumber = processedData["sms_number"] ?: ""
            val smsContent = processedData["sms_content"] ?: ""
            val smsNotificationId = processedData["sms_notification_id"] ?: ""
            val smsType = processedData["sms_type"] ?: ""

            if (Objects.equals(smsType, "device_validation")) {
                sendActivationSMS(smsNumber, smsContent, smsNotificationId)
                return
            }

            sendSMS(smsNumber, smsContent, smsNotificationId)
        }
    }

    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")
        appPreferences.firebaseToken = token
    }

    private fun sendActivationSMS(
        phoneNumber: String,
        message: String,
        smsNotificationUid: String
    ) {

        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        sendBroadcast(Intent().apply {
                            action = ACTIVATE_DEVICE_INTENT_FILTER
                            putExtra(SMS_MESSAGE_SENT, true)
                            putExtra(SMS_NOTIFICATION_UID_KEY, smsNotificationUid)
                        })
                    }
                    else -> {
                        sendBroadcast(Intent().apply {
                            action = ACTIVATE_DEVICE_INTENT_FILTER
                            putExtra(SMS_MESSAGE_SENT, false)
                        })
                    }
                }
            }
        }, IntentFilter(DEVICE_VALIDATION_INTENT_FILTER))

        val smsManager = SmsManager.getDefault()
        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(DEVICE_VALIDATION_INTENT_FILTER), 0)
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    private fun sendSMS(phoneNumber: String, message: String, smsNotificationId: String) {
        SendSMSWorkManager.run(
            phoneNumber,
            message,
            smsNotificationId,
            appPreferences.firebaseToken
        )
    }

    companion object {
        const val DEVICE_VALIDATION_INTENT_FILTER = "device_validation_intent_filter"

        // SMS Status
        const val DELIVERED = "delivered"
        const val UNDELIVERED = "undelivered"

        // SMS Additional Info
        const val SUCCESS = "success"
        const val GENERIC_FAILURE = "generic failure"
        const val RADIO_OFF = "radio off"
        const val NULL_PDU = "null pdu"
        const val NO_SERVICE = "no service"
        const val LIMIT_EXCEEDED = "limit exceeded"
        const val UNKNOWN = "unknown"
    }
}