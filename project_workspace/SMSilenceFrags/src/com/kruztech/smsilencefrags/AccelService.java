package com.kruztech.smsilencefrags;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.SmsManager;
//import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AccelService extends Service {
	private static final String TAG = "Kruztech";
	static String contactName = null;
	static String data = null;
	static String smsMessage = null;
	private static SensorManager sensorManager;
	private int currentAcceleration = 0;
	private int proximityNear = 5;
	private NotificationManager notificationManager;
	protected static final Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	protected static final Sensor proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

	private final SensorEventListener sensorEventListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
		public void onSensorChanged(SensorEvent event) {
			Sensor source = event.sensor;
			if(source.equals(accelerometer)){
				int z = (int) event.values[2];
				currentAcceleration = (int) z;
			}
			if(source.equals(proximity)){
				int p = (int) event.values[0];
				proximityNear = (int) p;
			}
			if(currentAcceleration < -8 && proximityNear == 0){
				notificationSender(null);

				try {
					sendSMS(smsMessage, "This is an auto message sent from Jozef Kruszynski.");
				}catch(Exception e){
					e.printStackTrace();
					Log.d(TAG, "sms not sent");
				}
				//				TelephonyManager telMan = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
				//				telMan.endCall();
				onDestroy();
				Toast.makeText(getApplicationContext(), "You flipped your phone", Toast.LENGTH_SHORT)
				.show();
			}
		}
	};

	public void notificationSender(View v) {
		Intent intent = new Intent();

		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		Notification noti = new Notification.Builder(this)
		.setTicker("Someone called")
		.setContentTitle("Incoming Number: " + data)
		.setContentText("SMS sent to the above recipient")
		.setContentIntent(pIntent)
		.setSmallIcon(R.drawable.ic_launcher)
		.build();
		noti.flags=Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}

	public void sendSMS(String address, String message) throws Exception{
		SmsManager smsMgr = SmsManager.getDefault();
		smsMgr.sendTextMessage(address, null, message, null, null);
	}

	public static String getContactName(Context context, String data) {
		ContentResolver cr = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(data));
		Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
		if (cursor == null) {
			return null;
		}

		if(cursor.moveToFirst()) {
			contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
		}

		if(cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return contactName;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void onCreate() {
	} 

	@Override
	public void onStart(Intent accelIntent, int startId){		

		getContactName(getApplicationContext(), accelIntent.getStringExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER"));
		smsMessage = accelIntent.getStringExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER");
		if(contactName != null){
			data = contactName;
		}else{
			data = accelIntent.getStringExtra("com.kruztech.smsilencefrags.INCOMING_NUMBER");
		}

		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

//		Sensor accelerometer =
//				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorEventListener,
				accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		Timer updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
			}
		}, 0, 100);

		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

//		Sensor proximity =
//				sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sensorManager.registerListener(sensorEventListener,
				proximity,
				SensorManager.SENSOR_DELAY_NORMAL);

		Log.d(TAG, "Service started");

		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	@Override
	public void onDestroy(){
		sensorManager.unregisterListener(sensorEventListener);
		Log.d(TAG, "Service destroyed");
	}
}
