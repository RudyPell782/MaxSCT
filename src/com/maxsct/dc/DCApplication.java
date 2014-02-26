package com.maxsct.dc;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class DCApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		/*
		 * Register Parse Objects for use
		 */
		ParseObject.registerSubclass(Workout.class);
		ParseObject.registerSubclass(Exercise.class);

		/*
		 * Fill in this section with your Parse credentials
		 */
		Parse.initialize(this, "8NfCrj533xXnPs1ZFpp3v8Pm4iB3KlTh6aepm4b5", "WO8CUqukRh5H2uIGVRYUXv3ryKIxXNOoXMGg3JmJ");

		/*
		 * For more information on app security and Parse ACL:
		 * https://www.parse.com/docs/android_guide#security-recommendations
		 */
		ParseACL defaultACL = new ParseACL();

		/*
		 * If you would like all objects to be private by default, remove this
		 * line
		 */
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
		
		
	}

}
