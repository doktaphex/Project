package com.kruztech.smsilencefrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelReceiver extends BroadcastReceiver {
	private static final String TAG = "Kruztech";

	@Override
	public void onReceive(Context arg0, Intent intent) {
		Intent i = new Intent(arg0, TelService.class);
		arg0.startService(i);
		Log.d(TAG,"TelReceiver: Tel service started");
	}
}