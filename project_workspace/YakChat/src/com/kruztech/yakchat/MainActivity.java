package com.kruztech.yakchat;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
 
public class MainActivity extends Activity {
 
    public static CharSequence mTitle;
	private String[] drawerListViewItems;
    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static final String TAG = "Kruztech: YakChat";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SpannableString s = new SpannableString("YakChat");
        s.setSpan(new TypefaceSpan(this, "tiptoe.ttf", 2, 7, 7, 0xff990000), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // Update the action bar title with the TypefaceSpan instance
        ActionBar actionBar = getActionBar();
        actionBar.setTitle(s);
        
        // get list items from strings.xml
        drawerListViewItems = getResources().getStringArray(R.array.items);
        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);
        
        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_listview_item, drawerListViewItems));
 
        // 2. App Icon 
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
 
        // 2.1 create ActionBarDrawerToggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                );
 
        // 2.2 Set actionBarDrawerToggle as the DrawerListener
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        if (findViewById(R.id.content_frame) != null) {
			// However, if we're being restored from a previous state,
			// then we don't need to do anything and should return or else
			// we could end up with overlapping fragments.
			if (savedInstanceState != null) {
				return;
			}

			// Create a new Fragment to be placed in the activity layout
			RegistrationFrag frag1 = new RegistrationFrag();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			frag1.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.add(R.id.content_frame, frag1).commit();
		}
        // 2.3 enable and show "up" arrow
        getActionBar().setDisplayHomeAsUpEnabled(true); 
 
        // just styling option
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
 
        drawerListView.setOnItemClickListener(new DrawerItemClickListener());
    }
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
         actionBarDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
         // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event
 
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
 
    @Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
        	selectItem(position);
        	Toast.makeText(MainActivity.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            drawerLayout.closeDrawer(drawerListView);
 
        }
    }
    
    public void selectItem(int position) {
		// update the main content by replacing fragments
		Log.d(TAG,"switched fragments using nav-drawer");
		if(position == 0){
			LoginFrag fragL = new LoginFrag();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			fragL.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.replace(R.id.content_frame, fragL)
			.commit();
		}
		if(position == 3){
			RegistrationFrag fragR = new RegistrationFrag();

			// In case this activity was started with special instructions from an
			// Intent, pass the Intent's extras to the fragment as arguments
			fragR.setArguments(getIntent().getExtras());

			// Add the fragment to the 'fragment_container' FrameLayout
			getFragmentManager().beginTransaction()
			.replace(R.id.content_frame, fragR)
			.commit();
		}

		// update selected item and title, then close the drawer
		drawerListView.setItemChecked(position, true);
		//setTitle(drawerListViewItems[position]);
		drawerLayout.closeDrawer(drawerListView);
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
			View rootView = inflater.inflate(R.layout.fragment_login, container, false);
			int i = getArguments().getInt(ARG_FRAGMENT_NUMBER);
			String title = getResources().getStringArray(R.array.items)[i];

			getActivity().setTitle(title);
			return rootView;
		}
	}

}