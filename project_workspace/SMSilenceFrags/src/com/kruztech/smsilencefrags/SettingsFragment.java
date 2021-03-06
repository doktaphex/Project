package com.kruztech.smsilencefrags;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsFragment extends Fragment {

	private Switch serviceSwitch;
	private Switch bootSwitch;
	private Switch notificationSwitch;
	public static final String Service = "serviceKey";
	public static final String Boot = "bootKey";
	public static final String Noti = "notiKey";
	public static final String MyPreferences = "AppPrefs";
	SharedPreferences settings;
	final static String TAG = "Kruztech";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		initControls();
		settingsLogic();
	}

	public void startNewService(Intent intent){
		startNewService(new Intent());
	}

	public void stopNewService(Intent intent){
		startNewService(new Intent());
	}

	protected void initControls(){
		serviceSwitch = (Switch) getView().findViewById(R.id.serviceSwitch);
		bootSwitch = (Switch) getView().findViewById(R.id.bootSwitch);
		notificationSwitch = (Switch) getView().findViewById(R.id.notificationSwitch);
		bootSwitch.setEnabled(false);
		notificationSwitch.setEnabled(false);
	}

	protected void settingsLogic(){
		settings = getActivity().getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

		if (settings.contains(Service))
		{
			serviceSwitch.setChecked(true);
			((MainActivity) getActivity()).enableTelReceiver(serviceSwitch);
			bootSwitch.setEnabled(true);
			notificationSwitch.setEnabled(true);
		}
		if (settings.contains(Boot))
		{
			bootSwitch.setChecked(true);
			serviceSwitch.setChecked(true);
			((MainActivity) getActivity()).enableTelReceiver(serviceSwitch);
			bootSwitch.setEnabled(true);
			notificationSwitch.setEnabled(true);
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

					Toast.makeText(getActivity().getApplicationContext(), 
							"Service Started", Toast.LENGTH_SHORT).show();

					((MainActivity) getActivity()).enableTelReceiver(serviceSwitch);

				}else{
					bootSwitch.setEnabled(false);
					notificationSwitch.setEnabled(false);
					Editor edit = settings.edit();
					edit.remove("serviceKey");
					edit.apply();

					Toast.makeText(getActivity().getApplicationContext(), 
							"Service Stopped", Toast.LENGTH_SHORT).show();

					((MainActivity) getActivity()).disableTelReceiver(serviceSwitch);				}

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
					Toast.makeText(getActivity().getApplicationContext(), 
							"Service will start at device bootup", Toast.LENGTH_SHORT).show();

					Log.d(TAG, "Boot switch on");

				}else{
					Editor edit = settings.edit();
					edit.remove("bootKey");
					edit.apply();
					Toast.makeText(getActivity().getApplicationContext(), 
							"Service will NOT start at device bootup", Toast.LENGTH_SHORT).show();

					Log.d(TAG, "Boot switch off");
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
					Toast.makeText(getActivity().getApplicationContext(), 
							"Service will display notifications", Toast.LENGTH_SHORT).show();
				}else{
					Editor edit = settings.edit();
					edit.remove("notiKey");
					edit.apply();
					Toast.makeText(getActivity().getApplicationContext(), 
							"Service will NOT display notifications", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}
}