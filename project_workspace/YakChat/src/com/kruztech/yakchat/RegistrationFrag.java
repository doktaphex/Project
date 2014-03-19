package com.kruztech.yakchat;

import java.util.HashMap;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link RegistrationFrag.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link RegistrationFrag#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class RegistrationFrag extends Fragment {
	private EditText Uname;
	private String uName;
	private String UserName;
	private EditText Email;
	private String eMail;
	private EditText Pword;
	private String pWord;
	private EditText Pval;
	private String pVal;
	private Button RegBtn;
	final static String TAG = "Kruztech";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_registration, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initControls();
		registerCheck();
	}
	
	public void launchRingDialog(View view) {
		final ProgressDialog rpd = new ProgressDialog (getActivity());
		rpd.setTitle("Please Wait!");
		rpd.setMessage("Creating New User");
		rpd.setIcon(R.drawable.ic_launcher);
		rpd.setCancelable(false);
		rpd.show();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					writeFireBaseData();
					Thread.sleep(5000);
				} catch (Exception e) {

				}
				rpd.dismiss();
			}
		}).start();
	}

	public void setTitle(CharSequence title) {
		MainActivity.mTitle = title;
		getActivity().getActionBar().setTitle(MainActivity.mTitle);
	}

	public void initControls(){
		Uname = (EditText) getView().findViewById(R.id.uNameReg);
		Email = (EditText) getView().findViewById(R.id.emailReg);
		Pword = (EditText) getView().findViewById(R.id.pWordReg);
		Pval = (EditText) getView().findViewById(R.id.pWordVal);
		RegBtn = (Button) getView().findViewById(R.id.regBtn);
	}

	public void registerCheck(){
		RegBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(Uname != null && Email != null && Pword != null && Pval != null){
					if(Pword.getText().toString().matches(Pval.getText().toString())){
						uName = Uname.getText().toString();
						UserName = uName;
						Log.d(TAG, "uname is: " + uName);
						eMail = Email.getText().toString();
						Log.d(TAG, "email is: " + eMail);
						pWord = Pword.getText().toString();
						Log.d(TAG, "pword is: " + pWord);
						launchRingDialog(v);
					}else{
						Toast passVal = Toast.makeText(getActivity().getApplicationContext(), "Passwords entered do not match! Please re-enter password", Toast.LENGTH_SHORT);
						passVal.show();
					}
				}

			}
		});
	}

	public void writeFireBaseData(){
		final Firebase createUser = new Firebase("https://radiant-fire-6008.firebaseio.com/users/" + UserName);

		createUser.addListenerForSingleValueEvent(new ValueEventListener(){

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDataChange(DataSnapshot snapshot) {
				Object value = snapshot.getValue();
				if (value != null){
					Uname.setText("");
					UserName = "";
					Log.i(TAG, "RegistrationFrag: username taken");
					Toast failedReg = Toast.makeText(getActivity(), "Sorry, username already taken, please choose another one.", Toast.LENGTH_LONG);
					failedReg.show();

				}else{
					Map<String, Object> toSet = new HashMap<String, Object>();
					toSet.put("UserName", uName);
					toSet.put("EmailAddress", eMail);
					toSet.put("PassWord", pWord);
					createUser.setValue(toSet, new Firebase.CompletionListener() {

						public void onComplete(FirebaseError error) {
							if (error != null) {
								Log.d(TAG, "RegistrationFrag: Data could not be saved: " + error.getMessage());
							} else {
								Log.d(TAG, "RegistrationFrag: Data saved successfully.");
							}
						}

						@Override
						public void onComplete(FirebaseError arg0, Firebase arg1) {
							Toast confirm = Toast.makeText(getActivity(), "Succesfully Registered", Toast.LENGTH_LONG);
							confirm.show();
							Uname.setText("");
							Email.setText("");
							Pword.setText("");
							Pval.setText("");
						}

					});
				}
			}

		});
	}
}