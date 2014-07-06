package com.kruztech.yakchat;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
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
	final static String TAG = "Kruztech: YakChat";
	private static String emailPattern;
	private static String email;
	private Pattern pattern;
	private Matcher matcher;
	public static final String UNAME = "uname";
	public static final String MyPrefs = "AppPrefs";
	private  int unameValid = 0;
	private  int emailValid = 0;
	private  int pwordValid = 0;
	private  int pvalValid = 0;
	SharedPreferences prefs;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_registration, container, false);
	}

	@Override
	public void onStart(){
		super.onStart();
		initControls();
		registerCheck();
		settingsLogic();

	}

	protected void settingsLogic(){
		prefs = getActivity().getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

		if (prefs.contains(UNAME))
		{
			LoginFrag fragL = new LoginFrag();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();

			transaction.replace(R.id.content_frame, fragL);
			transaction.commit();
		}else{
			return;
		}
	}


	public void launchRingDialog() {

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
					Thread.sleep(3000);
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
				unameValidation();
				emailValidation();
				pwordValidation();
				pvalValidation();
				if(unameValid == 1 && emailValid == 1 && pwordValid == 1 && pvalValid == 1)
				{
					fullValidation();
				}
			}
		});
	}

	public void unameValidation(){
		//		unameValid = false;
		if(Uname == null) {
			Log.d(TAG, "uname blank");
			Uname.setError("Username must be 6 characters or more! Please try again.");
			Uname.requestFocus();
			unameValid = 0;

		}
		else if(Uname.getText().toString().length() <= 5){
			Log.d(TAG, "uname too short");
			Uname.setError("Username must be 6 characters or more! Please try again.");
			Uname.requestFocus();
			unameValid = 0;

		}
		else if(Uname.getText().toString().length() > 16){
			Log.d(TAG, "uname too long");
			Uname.setError("Username limited to 16 characters! Please try again.");
			Uname.requestFocus();
			unameValid = 0;

		}else {
			uName = Uname.getText().toString();
			UserName = uName;
			unameValid = 1;
			Uname.setError(null);
			Log.d(TAG, "uname valid");
		}
		
		}

	public void emailValidation() {
		  //  emailValid = false;
		  email = Email.getText().toString().trim();
		  emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		  pattern = Pattern.compile(emailPattern);
		  matcher = pattern.matcher(email);
		//  
		  
		  if(email.equals("")) {
		   Email.setError("Please enter a valid email address!");
		   Email.requestFocus();
		   emailValid = 0;
		   Log.d(TAG, "email blank");
		  }
//		  else if(!matcher.find()) {
//		   Email.setError("Please enter a valid email address!");
//		   Email.requestFocus();
//		   emailValid = 0;
//		   Log.d(TAG, "email non-conform" + "email = " + email + "emailValid = " + emailValid);
//		  }
		  else if(matcher.find()) {
		   eMail = Email.getText().toString();
		   emailValid = 1;
		   Email.setError(null);
		   Log.d(TAG, "email conform" + "email = " + email + "emailValid = " + emailValid);
		  }
		 }

	public void pwordValidation() {
		//		pwordValid = false;
		if(Pword == null) {
			Pword.setError("Password must be 6 characters or more! Please try again.");
			Pword.requestFocus();
			pwordValid = 0;
			Log.d(TAG, "pword blank");
		}
		else if(Pword.getText().toString().length() <= 5){
			Pword.setError("Password must be 6 characters or more! Please try again.");
			Pword.requestFocus();
			pwordValid = 0;
			Log.d(TAG, "pword too short");
		}
		else if(Pword.getText().toString().length() > 16){
			Pword.setError("Password limited to 16 characters! Please try again.");
			Pword.requestFocus();
			pwordValid = 0;
			Log.d(TAG, "pword too long");
		}else{
			pwordValid = 1;
			Pword.setError(null);
		}
	}

	public void pvalValidation() {
		//		pvalValid = false;
		pWord = Pword.getText().toString();
		pVal = Pval.getText().toString();

		if(pVal.equals("")) {
			Pval.setError("Password must be 6 characters or more! Please try again.");
			Pval.requestFocus();
			pvalValid = 0;
			Log.d(TAG, "pval blank");
		}
		else if(pVal.equals(pWord)) {
			pWord = Pword.getText().toString();
			pvalValid = 1;
			Log.d(TAG, "pval = " + pVal + "; pvalValid = " + pvalValid);
			Pval.setError(null);
		}
	}

	public void fullValidation() {
		launchRingDialog();
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

							Editor edit = prefs.edit();
							edit.putBoolean("uname", true);
							edit.putString("username", uName);
							edit.putString("password", pWord);
							edit.apply();

							LoginFrag fragL = new LoginFrag();
							FragmentTransaction transaction = getFragmentManager().beginTransaction();

							transaction.replace(R.id.content_frame, fragL);
							transaction.commit();

						}

					});
				}
			}

		});
	}
}