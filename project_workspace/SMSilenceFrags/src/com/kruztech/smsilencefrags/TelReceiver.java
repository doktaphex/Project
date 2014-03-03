package com.kruztech.smsilencefrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelReceiver extends BroadcastReceiver {
	private static final String TAG = "Kruztech";

	@Override
	public void onReceive(Context arg0, Intent intent) {
		Intent accelIntent = new Intent(arg0, AccelService.class);
//		Intent telIntent = new Intent(arg0, TelService.class);
		arg0.startService(accelIntent);
//		arg0.startService(telIntent);
		Log.d(TAG,"TelReceiver: Accelerometer and Telephony services started");
	}
}
