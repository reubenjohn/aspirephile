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

	private String booleanKey;
	private String sharedPrefsName;

	private class RunConfig {
		public Class<?> launchActivity;
		public int enterAnim;
		public int exitAnim;

		public RunConfig() {
			launchActivity = null;
			enterAnim = 0;
			exitAnim = 0;
		}

		public boolean isLaunchConfigurationValid() {
			return ((launchActivity != null));
		}

		public boolean isAnimationConfigurationValid() {
			return (enterAnim > 0) && (exitAnim > 0);
		}
	}

	RunConfig firstRunConfigs, subsequentRunConfigs;

	SharedPreferences prefs;

	public FirstRunManager(FragmentActivity activity) {
		this.activity = activity;
		prefs = this.activity.getSharedPreferences(sharedPrefsName,
				Context.MODE_PRIVATE);
		firstRunConfigs = new RunConfig();
		subsequentRunConfigs = new RunConfig();
	}

	public boolean isFirstRun() {
		return prefs.getBoolean(booleanKey, defaults.isfirstRun);
	}

	public FirstRunManager setOnFirstRunClass(Class<?> cls) {
		firstRunConfigs.launchActivity = cls;
		return this;
	}

	public FirstRunManager setOnSubsequentRunClass(Class<?> cls) {
		subsequentRunConfigs.launchActivity = cls;
		return this;
	}

	public FirstRunManager setFirstRunTransition(int enterAnim, int exitAnim) {
		firstRunConfigs.enterAnim = enterAnim;
		firstRunConfigs.exitAnim = exitAnim;
		return FirstRunManager.this;
	}

	public FirstRunManager setSubsequentRunTransition(int enterAnim,
			int exitAnim) {
		subsequentRunConfigs.enterAnim = enterAnim;
		subsequentRunConfigs.exitAnim = exitAnim;
		return FirstRunManager.this;
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
		RunConfig runConfigs;
		boolean isFirstRun = isFirstRun();
		// Setup appropriate launch configurations
		if (isFirstRun) {
			l.i("Initiating first run protocol");
			runConfigs = firstRunConfigs;
		} else {

			l.i("Previous run detected. Skipping first run protocol");
			runConfigs = subsequentRunConfigs;
		}
		// Validate and start activity
		if (runConfigs.isLaunchConfigurationValid()) {
			Intent i = new Intent(activity, runConfigs.launchActivity);
			l.d(activity + " launching next activity: "
					+ runConfigs.launchActivity + "(" + i + ")");
			activity.startActivityForResult(i, EventBookerProps.codes.welcome);
		}
		// Validate and override pending transition
		if (runConfigs.isAnimationConfigurationValid()) {
			activity.overridePendingTransition(runConfigs.enterAnim,
					runConfigs.exitAnim);
		}

		// Lastly finish root activity to prevent return to it on back button
		// press from the subsequent activity
		if (!isFirstRun) {
			activity.finish();
		}
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
		launchNextActivity();
	}

}
