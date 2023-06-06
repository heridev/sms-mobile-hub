package com.smsforall.smsforall.di

import android.app.Application
import com.smsforall.smsforall.data.local.AppPreferences
import dagger.Module
import dagger.Provides

/**
 * Created by Irvin Rosas on July 04, 2020
 */
@Module(includes = [ContextModule::class])
class AppModule {

    @Provides
    fun provideSharedPreferences(context: Application) = AppPreferences(context)
}