package com.smsparatodos.smsparatodos.devicevalidation

import android.content.Context
import androidx.work.*
import com.smsparatodos.smsparatodos.BuildConfig
import com.smsparatodos.smsparatodos.data.SMSHubService
import com.smsparatodos.smsparatodos.data.UpdateStatusRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/**
 * Created by Irvin Rosas on July 02, 2020
 */
class UpdateStatusWorker(
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

        val smsNotificationUid =
            inputData.getString(SMS_NOTIFICATION_UID) ?: return@coroutineScope Result.retry()

        val status =
            inputData.getString(STATUS) ?: return@coroutineScope Result.retry()

        val additionalUpdateInfo =
            inputData.getString(ADDITIONAL_UPDATE_INFO) ?: return@coroutineScope Result.retry()

        val firebaseToken =
            inputData.getString(FIREBASE_TOKEN) ?: return@coroutineScope Result.retry()

        val response =
            smsHubService.updateNotificationStatus(
                UpdateStatusRequest(
                    smsNotificationUid,
                    status,
                    additionalUpdateInfo,
                    firebaseToken
                )
            )

        if (response.isSuccessful) {
            Timber.i("Response ${response.body()?.data}")
            return@coroutineScope Result.success()
        } else {
            Timber.e("Response ${response.errorBody()?.toString()}")
            return@coroutineScope Result.retry()
        }
    }

    companion object {

        const val SMS_NOTIFICATION_UID = "sms_notification_uid"
        const val STATUS = "status"
        const val ADDITIONAL_UPDATE_INFO = "additional_update_info"
        const val FIREBASE_TOKEN = "firebase_token"

        fun run(
            smsNotificationUid: String,
            status: String,
            additionalUpdateInfo: String,
            firebaseToken: String
        ) {
            val work = OneTimeWorkRequestBuilder<UpdateStatusWorker>()
                .setInputData(
                    workDataOf(
                        SMS_NOTIFICATION_UID to smsNotificationUid,
                        STATUS to status,
                        ADDITIONAL_UPDATE_INFO to additionalUpdateInfo,
                        FIREBASE_TOKEN to firebaseToken
                    )
                )
                .build()

            WorkManager.getInstance().beginWith(work).enqueue()
        }
    }
}