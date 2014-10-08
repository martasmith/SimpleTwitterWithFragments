package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.basictwitter.data.UserPreferences;
import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;


public class TimelineActivity extends FragmentActivity {
	
	private final int REQUEST_CODE = 10;
	private String tweetText, screenName;
	private UserPreferences userPrefs;
	private User user;
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		userPrefs = new UserPreferences(this);
		populateUserData();
		setupTabs();
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_action_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home", HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_action_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions", MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public void populateUserData() {
		client.getUserCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				//populate the user model with json data
				userPrefs.updateUserData(User.fromJSON(json));
				user = User.fromJSON(json);
				screenName = user.getScreenName();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getBaseContext(), "Unable to get user credentials from Twitter.", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});		
	}
	
	public void onProfileViews(MenuItem mi) {
    	Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
    	i.putExtra("screenName", screenName);
    	startActivity(i);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	 public void onCompose(MenuItem mi) {
	    	Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
	    	startActivityForResult(i,REQUEST_CODE);	
	    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			tweetText = data.getExtras().getString("tweetText");
			// get the fragment we want to use to insert new tweet		
			getSupportFragmentManager().executePendingTransactions();
			HomeTimelineFragment homeTimelineFragment = (HomeTimelineFragment) getSupportFragmentManager().findFragmentByTag("home");
			// call the fragment's method that inserts new tweet
			homeTimelineFragment.updateStatus(tweetText);
			getActionBar().setSelectedNavigationItem(0);
		}
	}

   
/*	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
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
	*/
}
