package com.kruztech.yakchat;

import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
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

//	public void launchRingDialog(View view) {
//		final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity().getApplicationContext(), "Please Wait..!", "Registering New User");
//		ringProgressDialog.setCancelable(false);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					writeFireBaseData();
//					Thread.sleep(5000);
//				} catch (Exception e) {
//
//				}
//				ringProgressDialog.dismiss();
//			}
//		}).start();
//	}
	
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
						writeFireBaseData();
					}else{
						Toast passVal = Toast.makeText(getActivity().getApplicationContext(), "Passwords entered do not match! Please re-enter password", Toast.LENGTH_SHORT);
						passVal.show();
					}
				}

			}
		});
	}
	
	public void writeFireBaseData(){
		Firebase firebase = new Firebase("https://radiant-fire-6008.firebaseio.com/users/" + UserName);
		firebase.child("/UserName").setValue(uName);
		firebase.child("/EmailAddress").setValue(eMail);
		firebase.child("/PassWord").setValue(pWord);
	}
}