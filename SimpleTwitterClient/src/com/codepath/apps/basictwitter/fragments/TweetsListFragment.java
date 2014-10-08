package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.adapters.EndlessScrollListener;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
	//private UserPreferences userPrefs;
	//private User user;
	private ArrayList<Tweet> tweets;
	private TweetArrayAdapter aTweets;
	private ListView lvTweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate the view
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		// Assign our view preferences
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		// set up scrolling mechanism
		lvTweets.setOnScrollListener(new EndlessScrollListener() {	
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// here we need to extract max_id and since_id from tweet
				// pass it on to client.getHomeTimeline as argument
				populateTimeline();
			}
		});
		
		// return the view
		return v;
	}
	
	//return the adapter to the activity
	//public TweetArrayAdapter getAdapter() {
	//	return aTweets;
	//}
	
	// Delegate to the adding to the internal adapter
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public void insert(Tweet tweet, int position) {
		aTweets.insert(tweet, position);
	}
	
	public ArrayList<Tweet> getTweetsArray() {
		return tweets;
	}
	
	public abstract void populateTimeline();
}
