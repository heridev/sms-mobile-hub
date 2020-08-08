package com.smsparatodos.smsparatodos.di

import android.app.Application
import com.smsparatodos.smsparatodos.BaseApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Irvin Rosas on June 28, 2020
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DevicesModule::class,
        NetworkModule::class,
        ServiceModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<BaseApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(instance: BaseApp)
}