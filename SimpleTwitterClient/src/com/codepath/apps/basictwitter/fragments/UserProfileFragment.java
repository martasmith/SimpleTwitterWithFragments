package com.codepath.apps.basictwitter.fragments;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileFragment extends Fragment {
	private User user;
	private String screenName="";
	private TextView tvFullNameProfile,tvTaglineProfile,tvFollowingProfile,tvFollowersProfile;
	private ImageView imgProfile;
	private TwitterClient client;
	
	public static UserProfileFragment newInstance(String screenName) {
		 UserProfileFragment userProfileFragment = new UserProfileFragment();
	     Bundle args = new Bundle();
	     args.putString("screenName", screenName);
	     userProfileFragment.setArguments(args);
	     return userProfileFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		screenName = getArguments().getString("screenName", "");	
		Log.d("martas", "UserProfileFragment sreenName: " + screenName);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_user_profile, container, false);
		populateProfileInfo(v);
		return v;
	}
	
	
	protected void populateProfileInfo(View v) {
		tvFullNameProfile = (TextView) v.findViewById(R.id.tvFullNameProfile);
		tvTaglineProfile = (TextView) v.findViewById(R.id.tvTaglineProfile);
		tvFollowersProfile = (TextView) v.findViewById(R.id.tvFollowerProfile);
		tvFollowingProfile = (TextView) v.findViewById(R.id.tvFollowingProfile);
		imgProfile = (ImageView) v.findViewById(R.id.imgProfileImage);
		populateProfileData();

	}
	
	private void populateProfileData() {
		client.getUserShowInfo(screenName, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
				tvFullNameProfile.setText(user.getName());
				tvTaglineProfile.setText(user.getTagline());
				tvFollowersProfile.setText(user.getFollowers() + "Followers");
				tvFollowingProfile.setText(user.getFollowing() + "Following");
				ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imgProfile);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Toast.makeText(getActivity(), "Twitter was unable to retrieve user information.", Toast.LENGTH_LONG).show();
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

}
