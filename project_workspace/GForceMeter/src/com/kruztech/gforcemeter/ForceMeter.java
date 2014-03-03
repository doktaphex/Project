package com.kruztech.gforcemeter;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class ForceMeter extends Activity {


	private SensorManager sensorManager;
	private TextView accelerationTextView;
	private TextView maxAccelerationTextView;
	private int currentAcceleration = 0;
	private int maxAcceleration = 0;
	private String maxG = null;
	private String currentG = null;

	private final SensorEventListener sensorEventListener = new SensorEventListener() {
		public void onAccuracyChanged(Sensor sensor, int accuracy) { }
		public void onSensorChanged(SensorEvent event) {
			int z = (int) event.values[2];
			currentAcceleration = (int) z;
			if (currentAcceleration > 0) maxAcceleration = currentAcceleration;
			if(currentAcceleration < -7) maxAcceleration = currentAcceleration;
			}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_force_meter);

		accelerationTextView = (TextView)findViewById(R.id.acceleration);
		maxAccelerationTextView = (TextView)findViewById(R.id.maxAcceleration);
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

		Sensor accelerometer =
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorEventListener,
				accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
		Timer updateTimer = new Timer("gForceUpdate");
		updateTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				updateGUI();
			}
		}, 0, 100);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(sensorEventListener,
				accelerometer,
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	@Override
	protected void onPause() {
		sensorManager.unregisterListener(sensorEventListener);
		super.onPause();
	}

	private void updateGUI() {
		runOnUiThread(new Runnable() {
			public void run() {
				currentG = currentAcceleration + "Gs";
				accelerationTextView.setText(currentG);
				accelerationTextView.invalidate();
				maxG = maxAcceleration + "Gs";
				maxAccelerationTextView.setText(maxG);
				maxAccelerationTextView.invalidate();
			}
		});
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.force_meter, menu);
		return true;
	}

}
