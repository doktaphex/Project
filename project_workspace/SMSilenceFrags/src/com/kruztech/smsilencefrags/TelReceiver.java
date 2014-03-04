package com.kruztech.smsilencefrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class TelReceiver extends BroadcastReceiver {
	private static final String TAG = "Kruztech";
	String incomingNumber = "number";

	@Override
	public void onReceive(Context arg0, Intent intent) {
		Intent accelIntent = new Intent(arg0, AccelService.class);
		arg0.startService(accelIntent);
		Log.d(TAG,"TelReceiver: Accelerometer and Telephony services started");
		
		String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
		Intent i = new Intent(arg0, AccelService.class);
		i.putExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER", incomingNumber);
		arg0.startService(i);
		Log.d(TAG,"TelReceiver: data passed?");
	}
}

