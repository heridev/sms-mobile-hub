package com.smsparatodos.smsparatodos.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.smsparatodos.smsparatodos.BuildConfig
import com.smsparatodos.smsparatodos.data.SMSHubService
import com.smsparatodos.smsparatodos.util.OnlineChecker
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Irvin Rosas on June 30, 2020
 */
@Module
class NetworkModule {

    @Provides
    fun provideSMSHubService(okHttpClient: OkHttpClient): SMSHubService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SMSHubService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun provideOnlineChecker(connectivityManager: ConnectivityManager?) =
        OnlineChecker(connectivityManager)

    @Provides
    fun provideConnectivityManager(context: Application): ConnectivityManager? {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}