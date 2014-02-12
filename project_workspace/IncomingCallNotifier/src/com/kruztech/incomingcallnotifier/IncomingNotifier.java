package com.kruztech.incomingcallnotifier;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Menu;

public class IncomingNotifier extends Activity {
	public class MyReceiver extends BroadcastReceiver {
		 NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		@SuppressLint("NewApi") @Override
		public void onReceive(Context context, Intent intent) {
			// TODO: This method is called when the BroadcastReceiver is receiving
			// an Intent broadcast.
				String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
				if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				String phoneNumber =
				intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
				
				Notification noti = new Notification.Builder(context)
		         .setContentTitle("Incoming Call From: " + phoneNumber.toString())
		         .build();
				noti.vibrate = new long[] { 100, 250, 100, 500};
				int id = 1;
				nm.notify(id , noti);
				}
				}
		}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_notifier);
        MyReceiver = new MyReceiver();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.incoming_notifier, menu);
        return true;
    }
    
}
