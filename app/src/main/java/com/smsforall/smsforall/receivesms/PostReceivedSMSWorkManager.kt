package com.smsforall.smsforall.receivesms

import android.content.Context
import androidx.work.*
import com.smsforall.smsforall.BuildConfig
import com.smsforall.smsforall.data.PostReceivedMessageRequest
import com.smsforall.smsforall.data.SMSHubService
import com.smsforall.smsforall.util.retryIO
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

/**
 * Created by Irvin Rosas on August 17, 2020
 */
class PostReceivedSMSWorkManager(
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
            inputData.getString(SMS_PHONE_NUMBER)
                ?: return@coroutineScope Result.retry()

        val message =
            inputData.getString(SMS_MESSAGE)
                ?: return@coroutineScope Result.retry()

        val firebaseToken =
            inputData.getString(FIREBASE_TOKEN)
                ?: return@coroutineScope Result.retry()

        CoroutineScope(Dispatchers.IO).launch {
            retryIO(times = 3) {
                smsHubService.postReceivedMessage(
                    PostReceivedMessageRequest(
                        phoneNumber,
                        message,
                        firebaseToken
                    )
                ).also {
                    Timber.d("Response $it")
                }
            }
        }

        return@coroutineScope Result.success()
    }

    companion object {

        private const val SMS_PHONE_NUMBER = "phone_number"
        private const val SMS_MESSAGE = "message"
        private const val FIREBASE_TOKEN = "firebase_token"

        fun run(
            phoneNumber: String,
            message: String,
            firebaseToken: String
        ) {
            val work = OneTimeWorkRequestBuilder<PostReceivedSMSWorkManager>()
                .setInputData(
                    workDataOf(
                        SMS_PHONE_NUMBER to phoneNumber,
                        SMS_MESSAGE to message,
                        FIREBASE_TOKEN to firebaseToken
                    )
                )
                .build()

            WorkManager.getInstance().beginWith(work).enqueue()
        }
    }
}