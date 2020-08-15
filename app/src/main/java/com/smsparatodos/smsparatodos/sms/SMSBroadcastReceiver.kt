package com.smsparatodos.smsparatodos.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import timber.log.Timber

/**
 * Created by Irvin Rosas on August 14, 2020
 */
class SMSBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == SMS_RECEIVED_FILTER) {
            val bundle = intent.extras
            var msgs: Array<SmsMessage?>? = null

            if (bundle != null) {
                try {

                    val pdus = bundle["pdus"] as Array<*>?
                    msgs = arrayOfNulls(pdus!!.size)

                    for (i in msgs.indices) {
                        msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        val msgBody: String? = msgs[i]?.messageBody
                        //SMSCallback.smsReceived(msgBody)
                    }

                } catch (e: Exception) {
                    Timber.e(e)
                    //SMSCallback.smsReceiveError(e)
                }
            }
        }
    }

    companion object {
        const val SMS_RECEIVED_FILTER = "android.provider.Telephony.SMS_RECEIVED"
    }
}