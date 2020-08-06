package com.smsparatodos.smsparatodos.di

import com.smsparatodos.smsparatodos.firebase.AppMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by Irvin Rosas on July 04, 2020
 */
@Module
abstract class ServiceModule {

    @ContributesAndroidInjector
    internal abstract fun contributeAppMessagingService(): AppMessagingService
}