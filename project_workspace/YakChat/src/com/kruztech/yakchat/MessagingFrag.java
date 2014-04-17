package com.kruztech.yakchat;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MessagingFrag extends Fragment {

	private EditText Message;
	private TextView Message1;
	private ImageView ImgPicker;
	private ImageView LocationBtn;
	private ImageView SendBtn;
	private double lat;
	private double lon;
	private String Location;
	LocationManager locationManager;
	LocationListener loc_listener;
	final static String TAG = "Kruztech: YakChat";

	public MessagingFrag() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_messaging, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initControls();
		getGPS();
		getMessage();
	}

	public void initControls(){
		Message = (EditText) getView().findViewById(R.id.message);
		Message1 = (TextView) getView().findViewById(R.id.message1);
		Message1.setMovementMethod(LinkMovementMethod.getInstance());
		ImgPicker = (ImageView) getView().findViewById(R.id.imageBtn);
		LocationBtn = (ImageView) getView().findViewById(R.id.locationBtn);
		SendBtn = (ImageView) getView().findViewById(R.id.sendBtn);
	}

	private void getLocation() {
		// Get the location manager
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setVerticalAccuracy(Criteria.ACCURACY_MEDIUM);
		criteria.setBearingAccuracy(Criteria.ACCURACY_LOW);
		criteria.setSpeedAccuracy(Criteria.ACCURACY_LOW);
		String bestProvider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(bestProvider);
		loc_listener = new LocationListener() {

	        public void onLocationChanged(Location l) {}

	        public void onProviderEnabled(String p) {}

	        public void onProviderDisabled(String p) {}

	        public void onStatusChanged(String p, int status, Bundle extras) {}
	    };
	    locationManager
	            .requestLocationUpdates(bestProvider, 0, 0, loc_listener);
		try {
			lat = location.getLatitude();
			lon = location.getLongitude();
		} catch (NullPointerException e) {
			Log.d(TAG, "Error" + e);
			
			lat = -1.0;
			lon = -1.0;
		}
	}

	public void getGPS(){
		LocationBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				getLocation();
				Log.d(TAG, "" + lat + ", " + lon + "");
				Location = "" + lat + ", " + lon + "";
				
				Message1.setText(Location);
//				locationManager.removeUpdates(loc_listener);
				
			}
		});
	}
	
	public void getMessage(){
		SendBtn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Message1.setText(Message.getText());
				Message.setText(null);
			}
		});
	}
	
	
}