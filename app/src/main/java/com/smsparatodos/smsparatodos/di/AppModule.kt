package com.smsparatodos.smsparatodos.di

import android.app.Application
import com.smsparatodos.smsparatodos.data.local.AppPreferences
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