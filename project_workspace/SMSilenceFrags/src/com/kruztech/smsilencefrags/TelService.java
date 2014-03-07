package com.kruztech.smsilencefrags;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class TelService extends Service {
	private static final String TAG = "Kruztech";
	String incomingNumber = "number";
	private TelephonyManager telephonyManager;

	PhoneStateListener callStateListener = new PhoneStateListener(){
		
		public void onCallStateChanged(int state, String incomingNumber){
			telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);
			switch(state){
			case TelephonyManager.CALL_STATE_IDLE :
				break;
				case TelephonyManager.CALL_STATE_OFFHOOK :
				break;
				case TelephonyManager.CALL_STATE_RINGING :
					Intent accelIntent = new Intent(getBaseContext(), AccelService.class);
					startService(accelIntent);
					Log.d(TAG,"TelReceiver: Accelerometer and Telephony services started");

					incomingNumber = accelIntent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
					
						Intent i = new Intent(getBaseContext(), AccelService.class);
						i.putExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER", incomingNumber);
						startService(i);
						Log.d(TAG,"TelReceiver: data passed?");
				//break;
				default : break;
			}
			
		}
	};
	
	@Override
	public void onStart(Intent accelIntent, int startId){
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
