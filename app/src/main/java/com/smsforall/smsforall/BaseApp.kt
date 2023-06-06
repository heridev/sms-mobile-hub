package com.smsforall.smsforall

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.PowerManager
import com.smsforall.smsforall.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Irvin Rosas on June 28, 2020
 */
class BaseApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var wakeLock: PowerManager.WakeLock

    @SuppressLint("WakelockTimeout")
    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)

        wakeLock = (getSystemService(Context.POWER_SERVICE) as PowerManager).run {
            newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BaseApp::WakelockTag").apply {
                acquire()
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        wakeLock.release()
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}