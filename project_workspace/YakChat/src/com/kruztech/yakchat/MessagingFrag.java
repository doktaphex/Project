package com.kruztech.yakchat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class MessagingFrag extends Fragment {

	private EditText Message;
	private ImageView ImgPicker;
	private ImageView LocationBtn;
	private ImageView SendBtn;
	private String Location;
	private String username = null;
	private String message = null;
	private String timeStamp = null;
	public static final String MyPrefs = "AppPrefs";
	SharedPreferences prefs;

	List<RowItem> rowItems;
	ListView mylistview;

	static ArrayList<Map<String, String>> messagesList = new ArrayList<Map<String, String>>();

	final static String TAG = "Kruztech: YakChat";
	protected static String DateTimeStamp = null;

	public MessagingFrag() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		rowItems = new ArrayList<RowItem>();

		return inflater.inflate(R.layout.fragment_messaging, container, false);

	}

	public void getFirebaseMessages() {
		ImgPicker.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String url = "https://radiant-fire-6008.firebaseio.com/messages/";
				Firebase dataRef = new Firebase(url);
				dataRef.addValueEventListener(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot snapshot) {
						messagesList.clear();
						Object REFERENCE = snapshot.getValue();
						Map REFERENCES = (Map) ((Map) REFERENCE);

						Set REFERENCEset = REFERENCES.keySet();
						Iterator REFERENCEiterator = REFERENCEset.iterator();

						for (int i = 0; i < REFERENCEset.size(); i++) {

							// Temporary map for the message contents
							Map<String, String> temporaryMap = new HashMap<String, String>();

							Object tempREFERENCE = REFERENCEiterator.next();
							System.out.println("tempReference = "
									+ tempREFERENCE);

							Object contents = REFERENCES.get(tempREFERENCE);
							Map contentsMap = (Map) ((Map) contents);
							System.out.println("contentsMap " + contentsMap);

							String message = (String) contentsMap
									.get("message");
							String timeStamp = (String) contentsMap
									.get("timeStamp");
							String username = (String) contentsMap
									.get("username");
							System.out.println("Message = " + message);

							// Add the contents to the map
							temporaryMap.put("message", message);
							temporaryMap.put("timeStamp", timeStamp);
							temporaryMap.put("username", username);

							// Add the map to array list
							messagesList.add(temporaryMap);
							
						}
						// end of for
						System.out.println("messagesList = " + messagesList);
						Log.d(TAG, "messagesList = " + messagesList);
						populateMessages();

					}

					@Override
					public void onCancelled(FirebaseError error) {
						System.err.println("Listener was cancelled");
					}
				});
			}
		});
	}

	public void populateMessages() {
		// String user_name;

		for (int i = 0; i < messagesList.size(); i++) {

			Log.d(TAG, "ROWITEMS" + rowItems);
			RowItem item = new RowItem(messagesList.get(i).get("username"),
					messagesList.get(i).get("message"), messagesList.get(i)
							.get("timeStamp"));
			rowItems.add(item);
		}

		mylistview = (ListView) getView().findViewById(R.id.list);
		MyListAdapter adapter = new MyListAdapter(getActivity(), rowItems);
		mylistview.setAdapter(adapter);

		// mylistview.setOnItemClickListener((OnItemClickListener) this);

	}

	@Override
	public void onStart() {
		super.onStart();
		initControls();
		getGPS();
		settingsLogic();
		getMessage();
		getFirebaseMessages();
	}

	public void onPause() {
		super.onPause();
		((MainActivity) getActivity()).locationManager
				.removeUpdates(((MainActivity) getActivity()).loc_listener);
		// ((ExecutorService) future).shutdown();
	}

	public void onDestroy() {
		super.onDestroy();
		((MainActivity) getActivity()).locationManager
				.removeUpdates(((MainActivity) getActivity()).loc_listener);

		// ((ExecutorService) future).shutdown();
	}

	protected void settingsLogic() {
		prefs = getActivity().getSharedPreferences(MyPrefs,
				Context.MODE_PRIVATE);

		username = prefs.getString("username", null);
	}

	public void initControls() {
		Message = (EditText) getView().findViewById(R.id.message);
		ImgPicker = (ImageView) getView().findViewById(R.id.imageBtn);
		LocationBtn = (ImageView) getView().findViewById(R.id.locationBtn);
		SendBtn = (ImageView) getView().findViewById(R.id.sendBtn);
	}

	public void getGPS() {
		LocationBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				((MainActivity) getActivity()).getLocation();
				Log.d(TAG, "" + ((MainActivity) getActivity()).lat + ", "
						+ ((MainActivity) getActivity()).lon + "");
				Location = "" + ((MainActivity) getActivity()).lat + ", "
						+ ((MainActivity) getActivity()).lon + "";
				Message.setText("" + ((MainActivity) getActivity()).lat + ", "
						+ ((MainActivity) getActivity()).lon + "");
			}
		});
	}

	public void getMessage() {
		SendBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"EE dd:MMMM HH:mm:ss a");
				timeStamp = sdf.format(c.getTime());
				Log.d(TAG, "Time stamp: " + timeStamp);
				message = Message.getText().toString();
				final Firebase sendMessage = new Firebase(
						"https://radiant-fire-6008.firebaseio.com/messages/");

				sendMessage
						.addListenerForSingleValueEvent(new ValueEventListener() {

							@Override
							public void onCancelled(FirebaseError arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onDataChange(DataSnapshot snapshot) {
								// Object value = snapshot.getValue();
								Message chat = new Message(username, message,
										timeStamp);

								sendMessage.push().setValue(chat);
								Message.setText("");

								// Map<String, Object> toSet = new
								// HashMap<String, Object>();
								// toSet.put("UserName", username);
								// toSet.put("Message", message);
								// toSet.put("TimeStamp", timeStamp);
								// sendMessage.setValue(toSet, new
								// Firebase.CompletionListener() {
								//
								// public void onComplete(FirebaseError error) {
								// if (error != null) {
								// Log.d(TAG,
								// "RegistrationFrag: Data could not be saved: "
								// + error.getMessage());
								// } else {
								// Log.d(TAG,
								// "RegistrationFrag: Data saved successfully.");
								// }
								// }
								//
								// @Override
								// public void onComplete(FirebaseError arg0,
								// Firebase arg1) {
								// Message.setText("");
								//
								// }
								//
								// });

							}
						});
			}
		});
	}
}
