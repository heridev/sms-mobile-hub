package com.smsforall.smsforall

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smsforall.smsforall.data.local.AppPreferences
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Irvin Rosas on June 28, 2020
 */
abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}