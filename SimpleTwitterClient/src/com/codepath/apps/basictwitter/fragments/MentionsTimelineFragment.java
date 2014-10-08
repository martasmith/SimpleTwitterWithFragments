package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;
	private long maxId = 0;
	private ArrayList<Tweet> tweets;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		tweets = getTweetsArray();
		populateTimeline();
	}
	
	public void populateTimeline() {
		client.getMentionsTimeline(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJSONArray(json);
				if (tweets.size() != 0) {
					//last id in my currently returned data
					maxId = tweets.get(tweets.size() - 1).getUid();
					maxId -= 1;
					addAll(Tweet.fromJSONArray(json));
				//Log.d("debug", json.toString());
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getActivity(), "Twitter has reached a rate limit. Please try again later.", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

}
