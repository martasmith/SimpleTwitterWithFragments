package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.data.UserPreferences;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {
	
	private TwitterClient client;
	private UserPreferences userPrefs;
	private User user;
	private long maxId = 0;
	//private long sinceId = 0;
	//private String tweetText;
	private ArrayList<Tweet> tweets;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		tweets = getTweetsArray();
		populateTimeline();
		userPrefs = new UserPreferences(getActivity());
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJSONArray(json);
				if (tweets.size() != 0) {
					//last id in my currently returned data
					maxId = tweets.get(tweets.size() - 1).getUid();
					maxId -= 1;
					addAll(Tweet.fromJSONArray(json));
				Log.d("debug", json.toString());
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
	
	public void updateStatus(String tweetText) {
		//Toast.makeText(getActivity(),"Posting to Twitter happens here!", Toast.LENGTH_LONG).show();
		client.postStatusUpdate(tweetText, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				//populate the tweet model with json data
				Tweet newTweet = Tweet.fromJSON(json);
				insert(newTweet, 0);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getActivity(),"Posting to Twitter has failed. Please try again!", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});	
	}
	
	
	/*
	// This function was used originally to refresh timeline upon adding a new tweet
	// however it can be used to implement pull-to-refresh, so leaving it here
	public void refreshTimeline() {
		client.getHomeTimelineRefreshed(sinceId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				tweets = Tweet.fromJSONArray(json);
				if (tweets.size() != 0) {
					addAll(Tweet.fromJSONArray(json));
				//Log.d("debug", json.toString());
				}
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}
	*/
	

}
