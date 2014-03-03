package com.kruztech.smsilencefrags;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AccelService extends Service {
	private static final String TAG = "Kruztech";
	public AccelService() {
	}

	private SensorManager sensorManager;
	private int currentAcceleration = 0;


	private final SensorEventListener sensorEventListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
		public void onSensorChanged(SensorEvent event) {
			int z = (int) event.values[2];
			currentAcceleration = (int) z;
			if(currentAcceleration < -8){
				onDestroy();
				Toast.makeText(getApplicationContext(), "You flipped your phone", Toast.LENGTH_SHORT)
				.show();
			}
		}
	};



	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void onCreate() {
	} 

	@Override
	public void onStart(Intent accelIntent, int startId){
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

		Sensor accelerometer =
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorEventListener,
				accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		Timer updateTimer = new Timer();
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
			}
		}, 0, 100);
		
		Log.d(TAG, "Service started");
	}

	@Override
	public void onDestroy(){
		sensorManager.unregisterListener(sensorEventListener);
		Log.d(TAG, "Service destroyed");
	}
}
