package com.smsparatodos.smsparatodos.di

import androidx.lifecycle.ViewModel
import com.smsparatodos.smsparatodos.devicevalidation.ValidateDeviceActivity
import com.smsparatodos.smsparatodos.devicevalidation.ValidateDeviceFragment
import com.smsparatodos.smsparatodos.devicevalidation.ValidateDeviceViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Irvin Rosas on June 28, 2020
 */
@Module
abstract class DevicesModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun addDeviceActivity(): ValidateDeviceActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    internal abstract fun addDeviceFragment(): ValidateDeviceFragment

    @Binds
    @IntoMap
    @ViewModelKey(ValidateDeviceViewModel::class)
    abstract fun bindViewModel(viewModel: ValidateDeviceViewModel): ViewModel
}