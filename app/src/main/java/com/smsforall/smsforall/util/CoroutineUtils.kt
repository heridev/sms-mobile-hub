package com.smsforall.smsforall.util

import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Created by Irvin Rosas on July 27, 2020
 */
suspend fun <T> retryIO(times: Int = 3, block: suspend () -> T): T {
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        delay(500)
    }
    return block()
}