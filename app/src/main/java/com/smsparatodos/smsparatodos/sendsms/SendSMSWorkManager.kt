package com.smsparatodos.smsparatodos.sendsms

import android.app.Activity
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.telephony.SmsManager
import androidx.work.*
import com.smsparatodos.smsparatodos.BuildConfig
import com.smsparatodos.smsparatodos.data.SMSHubService
import com.smsparatodos.smsparatodos.data.UpdateStatusRequest
import com.smsparatodos.smsparatodos.firebase.AppMessagingService
import com.smsparatodos.smsparatodos.util.retryIO
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/**
 * Created by Irvin Rosas on August 04, 2020
 */
class SendSMSWorkManager(
    appContext: Context, workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    override val coroutineContext: CoroutineDispatcher = Dispatchers.IO

    override suspend fun doWork(): Result = coroutineScope {
        val smsHubService = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SMSHubService::class.java)

        val phoneNumber =
            inputData.getString(SMS_PHONE_NUMBER) ?: return@coroutineScope Result.retry()

        val message =
            inputData.getString(SMS_MESSAGE) ?: return@coroutineScope Result.retry()

        val smsNotificationUid =
            inputData.getString(SMS_NOTIFICATION_UID) ?: return@coroutineScope Result.retry()

        val firebaseToken =
            inputData.getString(FIREBASE_TOKEN) ?: return@coroutineScope Result.retry()

        applicationContext.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                val status: String
                val additionalInfo: String

                when (resultCode) {
                    Activity.RESULT_OK -> {
                        status = AppMessagingService.DELIVERED
                        additionalInfo = AppMessagingService.SUCCESS
                    }
                    SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.GENERIC_FAILURE
                    }
                    SmsManager.RESULT_ERROR_RADIO_OFF -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.RADIO_OFF
                    }
                    SmsManager.RESULT_ERROR_NULL_PDU -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.NULL_PDU
                    }
                    SmsManager.RESULT_ERROR_NO_SERVICE -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.NO_SERVICE
                    }
                    SmsManager.RESULT_ERROR_LIMIT_EXCEEDED -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.LIMIT_EXCEEDED
                    }
                    else -> {
                        status = AppMessagingService.UNDELIVERED
                        additionalInfo = AppMessagingService.UNKNOWN
                    }
                }

                CoroutineScope(Dispatchers.IO).launch {
                    retryIO(times = 3) {
                        smsHubService.updateNotificationStatus(
                            UpdateStatusRequest(
                                smsNotificationUid,
                                status,
                                additionalInfo,
                                firebaseToken
                            )
                        ).also {
                            Timber.d("Response $it")
                        }
                    }
                }

                applicationContext.unregisterReceiver(this)
            }
        }, IntentFilter(SEND_SMS_INTENT_FILTER))

        val smsManager = SmsManager.getDefault()
        val sentPI =
            PendingIntent.getBroadcast(applicationContext, 0, Intent(SEND_SMS_INTENT_FILTER), 0)
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)

        return@coroutineScope Result.success()
    }

    companion object {

        private const val SMS_PHONE_NUMBER = "phone_number"
        private const val SMS_MESSAGE = "message"
        private const val SMS_NOTIFICATION_UID = "sms_notification_uid"
        private const val FIREBASE_TOKEN = "firebase_token"

        const val SEND_SMS_INTENT_FILTER = "send_sms_intent_filter"

        fun run(
            phoneNumber: String,
            message: String,
            smsNotificationUid: String,
            firebaseToken: String
        ) {
            val work = OneTimeWorkRequestBuilder<SendSMSWorkManager>()
                .setInputData(
                    workDataOf(
                        SMS_PHONE_NUMBER to phoneNumber,
                        SMS_MESSAGE to message,
                        SMS_NOTIFICATION_UID to smsNotificationUid,
                        FIREBASE_TOKEN to firebaseToken
                    )
                )
                .build()

            WorkManager.getInstance().beginWith(work).enqueue()
        }
    }
}