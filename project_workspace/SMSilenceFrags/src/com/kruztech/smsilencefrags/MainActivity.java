package com.kruztech.smsilencefrags;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	protected static CharSequence mTitle;
	private String[] mDrawerItems;
	private static final String TAG = "Kruztech";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		disableTelReceiver(mDrawerLayout);

		// Started adding left drawer menu.
		mTitle = mDrawerTitle = getTitle();
		mDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mDrawerItems));
		//mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (findViewById(R.id.fragment_container) != null) {

			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}

			// Create a new Fragment to be placed in the activity layout
			SettingsFragment firstFragment = new SettingsFragment();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			firstFragment.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.add(R.id.fragment_container, firstFragment).commit();
		}
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	}




	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	//Options menu stuff here.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.about).setVisible(!drawerOpen);
		menu.findItem(R.id.website).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch(item.getItemId()) {
		case R.id.about:
			AboutFragment aFragment = new AboutFragment();

			getFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, aFragment)
			.addToBackStack(null)
			.commit();
			Log.d(TAG,"Opened about fragment");
			setTitle("About");
			return true;
		case R.id.website:
			Toast.makeText(getApplicationContext(), 
					"WWW.KRUZTECH.COM", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	public class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		// update the main content by replacing fragments
		Log.d(TAG,"switched fragments using nav-drawer");
		if(position == 0){
			SettingsFragment fragment1 = new SettingsFragment();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			fragment1.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, fragment1)
			.commit();
		}
		if(position == 1){
			ManagementFragment fragment2 = new ManagementFragment();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			fragment2.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, fragment2)
			.commit();
		}

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mDrawerItems[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	 /**
     * This method enables the Broadcast receiver registered in the AndroidManifest file.
     * @param view
     */
   public void enableTelReceiver(View view){
      ComponentName receiver = new ComponentName(this, TelReceiver.class);
      PackageManager pm = this.getPackageManager();
 
      pm.setComponentEnabledSetting(receiver,
              PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
              PackageManager.DONT_KILL_APP);
      Toast.makeText(this, "Enabled broadcast receiver", Toast.LENGTH_SHORT).show();
   }
   /**
    * This method disables the Broadcast receiver registered in the AndroidManifest file.
    * @param view
    */
   public void disableTelReceiver(View view){
      ComponentName receiver = new ComponentName(this, TelReceiver.class);
      PackageManager pm = this.getPackageManager();
 
      pm.setComponentEnabledSetting(receiver,
              PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
              PackageManager.DONT_KILL_APP);
      Toast.makeText(this, "Disabled broadcst receiver", Toast.LENGTH_SHORT).show();
   }
	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Fragment that appears in the "content_frame", shows a planet
	 */
	public static class MainFragment extends Fragment {
		public static final String ARG_FRAGMENT_NUMBER = "fragment_number";

		public MainFragment() {
			// Empty constructor required for fragment subclasses

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
			int i = getArguments().getInt(ARG_FRAGMENT_NUMBER);
			String title = getResources().getStringArray(R.array.nav_drawer_items)[i];

			getActivity().setTitle(title);
			return rootView;
		}
	}

}


