package com.kruztech.smsilencefrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelReceiver extends BroadcastReceiver {
	private static final String TAG = "Kruztech";
	String incomingNumber = "number";

	@Override
	public void onReceive(Context arg0, Intent intent) {
		try
        {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
            	Intent accelIntent = new Intent(arg0, AccelService.class);
        		arg0.startService(accelIntent);
        		Log.d(TAG,"TelReceiver: Accelerometer and Telephony services started");

        		incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        		Intent i = new Intent(arg0, AccelService.class);
        		i.putExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER", incomingNumber);
        		arg0.startService(i);
        		Log.d(TAG,"TelReceiver: data passed?");
            }
            
            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                 return;
            }
            
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {
              return;
            }
        }
        catch(Exception e)
        {
            //your custom message
        }
     
   }
		
			
	}

