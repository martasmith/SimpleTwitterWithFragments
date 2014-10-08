package com.codepath.apps.basictwitter.models;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private long uid;
	private String name,screenName, profileImageUrl, tagline, following, followers;

	public static User fromJSON(JSONObject json) {
		User user = new User();
		
		//extract values from json to populate member variables
		try {
			user.name = json.getString("name");
			user.screenName = json.getString("screen_name");
			user.uid = json.getLong("id");
			user.profileImageUrl = json.getString("profile_image_url");
			user.tagline = json.getString("description");
			user.followers = json.getString("followers_count");
			user.following = json.getString("friends_count");
			// once we have the user object populated with json object, add to shared preferences
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}		
		return user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}
	
	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
	
	

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}
	

}
