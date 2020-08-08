package com.smsparatodos.smsparatodos.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Created by Irvin Rosas on July 04, 2020
 */
class OnlineChecker constructor(private val connectivityManager: ConnectivityManager?) {

    fun isNotOnline(): Boolean = !isOnline()

    private fun isOnline(): Boolean {
        val activeNetwork = connectivityManager?.activeNetwork ?: return false

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}