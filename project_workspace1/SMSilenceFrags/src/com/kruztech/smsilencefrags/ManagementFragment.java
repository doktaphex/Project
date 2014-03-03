package com.kruztech.smsilencefrags;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ManagementFragment extends Fragment {
	
	private Button LocBtn;
	private Button TimeDateBtn;
	private Button RespBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.fragment_management, container, false);
	}
	
	
	
	
	protected void initControls(){
		LocBtn = (Button) getView().findViewById(R.id.LocBtn);
		TimeDateBtn = (Button) getView().findViewById(R.id.TimeDateBtn);
		RespBtn = (Button) getView().findViewById(R.id.RespBtn);
	}
}
