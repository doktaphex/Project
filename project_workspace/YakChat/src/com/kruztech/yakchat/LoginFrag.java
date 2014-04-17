package com.kruztech.yakchat;

import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link LoginFrag.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link LoginFrag#newInstance} factory method to
 * create an instance of this fragment.
 * 
 */
public class LoginFrag extends Fragment implements LocationListener{
	private EditText Uname;
	private String uName;
	private EditText Pword;
	private String pWord;
	private Button LogBtn;
	private String UserName;
	final static String TAG = "Kruztech: YakChat";

	public static final String MyPrefs = "AppPrefs";
	SharedPreferences prefs;
	Firebase userRef;

	public LoginFrag() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initControls();
		getUserName();
		getPassWord();
		setCredentials();
		userCheck();
	}

	protected String getUserName(){
		prefs = getActivity().getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

		if (prefs.contains("username")){
			uName = prefs.getString("username", "");
		}return uName;
	}

	protected String getPassWord(){
		prefs = getActivity().getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

		if (prefs.contains("password")){
			pWord = prefs.getString("password", "");	
		}return pWord;
	}

	public void setCredentials(){
		Uname.setText(uName);
		UserName = uName;
		Log.d(TAG, "output: " + uName);
		Pword.setText(pWord);
		Log.d(TAG, "output: " + pWord);
	}


	public void launchRingDialog(View view) {
		final ProgressDialog rpd = new ProgressDialog (getActivity());
		rpd.setTitle("Please Wait!");
		rpd.setMessage("Attempting to login");
		rpd.setIcon(R.drawable.ic_launcher);
		rpd.setCancelable(false);
		rpd.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					//writeFireBaseData();
					Thread.sleep(3000);
				} catch (Exception e) {

				}
				rpd.dismiss();
			}
		}).start();
	}

	public void initControls(){
		Uname = (EditText) getView().findViewById(R.id.uName);
		Pword = (EditText) getView().findViewById(R.id.pWord);
		LogBtn = (Button) getView().findViewById(R.id.loginBtn);
	}
	

	public void userCheck(){
		LogBtn.setOnClickListener(new OnClickListener(){

			
			public void onClick(View v) {
					
				launchRingDialog(v);
				
				final Firebase userRef = new Firebase("https://radiant-fire-6008.firebaseio.com/users/" + UserName);
					userRef.addListenerForSingleValueEvent(new ValueEventListener() {
					     @Override
					     public void onDataChange(DataSnapshot snapshot) {
					         Object value = snapshot.getValue();
					         if (value == null) {
					             System.out.println("User " + uName + " doesn't exist");
					         } else {
					             String PWORD = (String)((Map)value).get("PassWord");
					             String UNAME = (String)((Map)value).get("UserName");
					             if(PWORD.equals(pWord) && UNAME.equals(uName)){
					            	 Log.d(TAG, "u: " + UNAME + " p: " + PWORD);
					            	 userRef.child("LoggedIn").setValue(true, new Firebase.CompletionListener(){

										@Override
										public void onComplete(
												FirebaseError arg0,
												Firebase arg1) {
											MessagingFrag fragM = new MessagingFrag();
											FragmentTransaction transaction = getFragmentManager().beginTransaction();
											
											transaction.replace(R.id.content_frame, fragM);
											transaction.commit();
											
										}
					            		 
					            	 });
					             }
					             System.out.println("User " + uName + " password is: " + PWORD);
					             
					         }
					     }


						@Override
						public void onCancelled(FirebaseError arg0) {
							// TODO Auto-generated method stub
							
						}
					 });
				}
			});

}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
}

