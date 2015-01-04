package com.aspirephile.shared;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.aspirephile.shared.debug.Logger;
import com.funtainment.eventbooker.EventBookerProps;

public class FirstRunManager {
	Logger l = new Logger(FirstRunManager.class);

	public static class defaults {
		public static final boolean isfirstRun = true;
	}

	private final Runnable launchNextActivity = new Runnable() {

		@Override
		public void run() {
			launchNextActivity();
		}
	};

	private Activity activity;

	private boolean isFirstRun;
	private boolean firstRunChecked = false;
	private String booleanKey;
	private String sharedPrefsName;
	private Class<?> firstRunClass;
	private Class<?> notFirstRunClass;
	SharedPreferences prefs;

	public FirstRunManager(FragmentActivity activity) {
		this.activity = activity;
		prefs = this.activity.getSharedPreferences(sharedPrefsName,
				Context.MODE_PRIVATE);
	}

	public boolean isFirstRun() {
		if (!firstRunChecked) {
			isFirstRun = prefs.getBoolean(booleanKey, defaults.isfirstRun);
			firstRunChecked = true;
		}
		return isFirstRun;
	}

	public FirstRunManager setOnFirstRunClass(Class<?> cls) {
		this.firstRunClass = cls;
		return this;
	}

	public FirstRunManager setOnNotFirstRunClass(Class<?> cls) {
		this.notFirstRunClass = cls;
		return this;
	}

	public FirstRunManager setSharedPrefsName(String sharedPrefsName) {
		this.sharedPrefsName = sharedPrefsName;
		return this;
	}

	public FirstRunManager setBooleanKey(String booleanKey) {
		this.booleanKey = booleanKey;
		return this;
	}

	public FirstRunManager launchNextActivity() {
		Class<?> cls;
		if (isFirstRun()) {
			l.i("Initiating first run protocol");
			cls = firstRunClass;
		} else {

			l.i("Previous run detected. Skipping first run protocol");
			cls = notFirstRunClass;
		}
		Intent i = new Intent(activity, cls);
		l.d(activity + " launching next activity: " + cls + "(" + i + ")");
		activity.startActivityForResult(i, EventBookerProps.codes.welcome);
		return this;
	}

	public FirstRunManager scheduleNextActivity(int splashscreenduration) {
		l.d("Scheduling next activity launch for " + splashscreenduration
				+ "ms delay");
		new Handler().postDelayed(launchNextActivity, splashscreenduration);
		return this;
	}

	public void sendFirstRunCompletionResult(boolean isFirstRunSuccessful) {
		l.d("Received message: First run successful: " + isFirstRunSuccessful);
		if (isFirstRunSuccessful) {
			l.d("Marking indicator of successful first run");
			prefs.edit().putBoolean(booleanKey, !isFirstRunSuccessful).commit();
		}
	}

}
