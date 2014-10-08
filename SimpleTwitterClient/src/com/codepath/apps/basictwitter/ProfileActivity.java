package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import com.codepath.apps.basictwitter.fragments.UserProfileFragment;
import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;

public class ProfileActivity extends FragmentActivity {
	
	private String screenName;
	private UserTimelineFragment userTimelineFragment;
    private UserProfileFragment userProfileFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		screenName = getIntent().getStringExtra("screenName");
		populateProfileActivity();
	}

	private void populateProfileActivity() {
		 // set action bar title to current user's screen name
		 getActionBar().setTitle("@"+screenName);

	     FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	     
	     //pull in the user profile info fragment
	     userProfileFragment = UserProfileFragment.newInstance(screenName);
	     ft.replace(R.id.flProfileView, userProfileFragment);
	     
	     // pull in the user timeline fragment
	     userTimelineFragment = UserTimelineFragment.newInstance(screenName);
	     ft.replace(R.id.flListView,userTimelineFragment); 
	     
	     ft.commit();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
