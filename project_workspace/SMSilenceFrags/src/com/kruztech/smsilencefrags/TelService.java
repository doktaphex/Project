package com.kruztech.smsilencefrags;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class TelService extends Service {
	public TelService() {
	}

	PhoneStateListener callStateListener = new PhoneStateListener(){
		public void onCallStateChanged(int state, String incomingNumber){
			String callStateStr = "Unknown";
			
			switch(state){
			case TelephonyManager.CALL_STATE_IDLE :
				callStateStr = "idle"; break;
				case TelephonyManager.CALL_STATE_OFFHOOK :
				callStateStr = "offhook"; break;
				case TelephonyManager.CALL_STATE_RINGING :
				callStateStr = "ringing. Incoming number is: "
				+ incomingNumber;
				break;
				default : break;
			}
			
		}
	};
	
//	telephonyManager.listen(callStateListener,
//			PhoneStateListener.LISTEN_CALL_STATE);
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
