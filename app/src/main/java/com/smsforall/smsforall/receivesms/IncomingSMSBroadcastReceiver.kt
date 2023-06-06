package com.smsforall.smsforall.receivesms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import com.smsforall.smsforall.data.local.AppPreferences

/**
 * Created by Irvin Rosas on August 14, 2020
 */
class IncomingSMSBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SMS_RECEIVED_ACTION) {

            val smsMessages =
                Telephony.Sms.Intents.getMessagesFromIntent(intent)

            val phoneNumber = smsMessages[0].displayOriginatingAddress

            val smsMessageBuilder = StringBuilder()

            for (message in smsMessages) {
                smsMessageBuilder.append(message.displayMessageBody)
                smsMessageBuilder.append("\n")
            }

            val firebaseToken = AppPreferences(context).firebaseToken

            PostReceivedSMSWorkManager.run(phoneNumber, smsMessageBuilder.toString(), firebaseToken)
        }
    }
}