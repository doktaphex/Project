package com.kruztech.smsilence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Switch serviceSwitch;
	private Switch bootSwitch;
	private Switch notificationSwitch;
	public static final String Service = "serviceKey";
	public static final String Boot = "bootKey";
	public static final String Noti = "notiKey";
	public static final String MyPreferences = "AppPrefs";
	SharedPreferences settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initControls();
		settingsLogic();
	}

	protected void initControls(){
		serviceSwitch = (Switch) findViewById(R.id.serviceSwitch);
		bootSwitch = (Switch) findViewById(R.id.bootSwitch);
		notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
		bootSwitch.setEnabled(false);
		notificationSwitch.setEnabled(false);
	}

	protected void settingsLogic(){
		settings = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

		if (settings.contains(Service))
		{
			serviceSwitch.setChecked(true);
			bootSwitch.setEnabled(true);
			notificationSwitch.setEnabled(true);
		}
		if (settings.contains(Boot))
		{
			bootSwitch.setChecked(true);
		}
		if (settings.contains(Noti))
		{
			notificationSwitch.setChecked(true);
		}

		serviceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					bootSwitch.setEnabled(true);
					notificationSwitch.setEnabled(true);
					Editor edit = settings.edit();
					edit.putBoolean("serviceKey", true);
					edit.apply();

					Toast.makeText(getApplicationContext(), 
							"Service Started", Toast.LENGTH_SHORT).show();
				}else{
					bootSwitch.setEnabled(false);
					notificationSwitch.setEnabled(false);
					Editor edit = settings.edit();
					edit.remove("serviceKey");
					edit.apply();

					Toast.makeText(getApplicationContext(), 
							"Service Stopped", Toast.LENGTH_SHORT).show();
				}

			}
		});

		bootSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Editor edit = settings.edit();
					edit.putBoolean("bootKey", true);
					edit.apply();
					Toast.makeText(getApplicationContext(), 
							"Service will start at device bootup", Toast.LENGTH_SHORT).show();
				}else{
					Editor edit = settings.edit();
					edit.remove("bootKey");
					edit.apply();
					Toast.makeText(getApplicationContext(), 
							"Service will NOT start at device bootup", Toast.LENGTH_SHORT).show();
				}

			}
		});

		notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					Editor edit = settings.edit();
					edit.putBoolean("notiKey", true);
					edit.apply();
					Toast.makeText(getApplicationContext(), 
							"Service will display notifications", Toast.LENGTH_SHORT).show();
				}else{
					Editor edit = settings.edit();
					edit.remove("notiKey");
					edit.apply();
					Toast.makeText(getApplicationContext(), 
							"Service will NOT display notifications", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		initControls();
		settingsLogic();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.about:
			startActivity(new Intent(this, AboutActivity.class));
			return true;
		case R.id.website:
			Toast.makeText(getApplicationContext(), 
					"WWW.KRUZTECH.COM", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
}