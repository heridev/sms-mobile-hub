package com.example.smsmobilehub;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
        String from = remoteMessage.getFrom();

        Log.d(TAG, "Before checking the remoteMessage data: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
            Map<String, String> processedData = remoteMessage.getData();
            String cellPhoneNumber = processedData.get("cellphone_number");
            String smsContent = processedData.get("sms_content");

            Log.d(TAG, "cellPhoneNumber: " + cellPhoneNumber);
            Log.d(TAG, "smsContent: " + smsContent);

            if(cellPhoneNumber.isEmpty() || smsContent.isEmpty()) {
                return;
            }

            sendSMS(cellPhoneNumber, smsContent);

            // here we will trigger the SMS service and pass the values so this could be triggered
            // and once the sms is sent successfully we would request to our api
            // informing about the sent was successfully did or in case it failed
        }
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    private void sendSMS(String phoneNumber, String message)
    {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        final String finalPhoneNumber = phoneNumber;

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS enviado a " + decoratePhoneNumberDigits(finalPhoneNumber),
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    private String decoratePhoneNumberDigits(String phoneNumber)  {
        String lastFourDigits = "";     //substring containing last 4 characters

        String firstDigits = new String();
        for(int i = 0; i < phoneNumber.length() - 4; i++) {
            char c = phoneNumber.charAt(i);
            firstDigits += "*";
        }

        if (phoneNumber.length() > 4) {
            lastFourDigits = phoneNumber.substring(phoneNumber.length() - 4);
        } else {
            lastFourDigits = phoneNumber;
        }

        for(int i = 0; i < lastFourDigits.length(); i++)
        {
            char c = lastFourDigits.charAt(i);
            firstDigits += c;
        }

        return firstDigits;
    }
}