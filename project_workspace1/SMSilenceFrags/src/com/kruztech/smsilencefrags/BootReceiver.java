package com.kruztech.smsilencefrags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	private static final String TAG = "Kruztech";

	@Override
	public void onReceive(Context arg0, Intent intent){
			Intent i = new Intent(arg0, AccelService.class);
			//i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
			arg0.startService(i);
			Log.d(TAG,"Boot Completed");
	}
}