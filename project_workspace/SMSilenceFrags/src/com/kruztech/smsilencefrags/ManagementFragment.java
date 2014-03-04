package com.kruztech.smsilencefrags;

import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

public class ManagementFragment extends Fragment {
	
	private Button LocBtn;
	private Button TimeDateBtn;
	private Button RespBtn;
	//private ProgressBar progBar;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_management, container, false);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		initControls();
		//launchRingDialog(this);
	}
	
	protected void initControls(){
		LocBtn = (Button) getView().findViewById(R.id.LocBtn);
		TimeDateBtn = (Button) getView().findViewById(R.id.TimeDateBtn);
		RespBtn = (Button) getView().findViewById(R.id.RespBtn);
		//progBar = (ProgressBar) getView().findViewById(R.id.progBar);
	}
	
//	public void launchRingDialog(ManagementFragment managementFragment) {
//		final ProgressDialog progDialog = ProgressDialog.show(getActivity(), "Please wait ...",	"Data Loading ...", true);
//		progDialog.setCancelable(false);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					// Here you should write your time consuming task...
//					// Let the progress ring for 10 seconds...
//					Thread.sleep(5000);
//				} catch (Exception e) {
//
//				}
//				progDialog.dismiss();
//			}
//		}).start();
//		WindowManager.LayoutParams lp = progDialog.getWindow().getAttributes();
//		lp.dimAmount=0.7f;
//		progDialog.getWindow().setAttributes(lp);
//	}
}
