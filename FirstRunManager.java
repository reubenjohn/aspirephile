package com.aspirephile.shared;

import android.content.Context;
import android.content.Intent;
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

	private Context ctx;
	private boolean isFirstRun;
	private boolean firstRunChecked = false;
	private Class<?> firstRunClass;
	private Class<?> notFirstRunClass;

	public FirstRunManager(FragmentActivity activity) {
		ctx = activity;
	}

	public boolean isFirstRun() {
		if (!firstRunChecked) {
			isFirstRun = ctx.getSharedPreferences(
					EventBookerProps.files.appPrefs, Context.MODE_PRIVATE)
					.getBoolean(EventBookerProps.keys.prefs.firstRun,
							defaults.isfirstRun);
			firstRunChecked = true;
		}
		return isFirstRun;
	}

	public void setOnFirstRunClass(Class<?> cls) {
		this.firstRunClass = cls;
	}

	public void setOnNotFirstRunClass(Class<?> cls) {
		this.notFirstRunClass = cls;
	}

	public void launchNextActivity() {
		Class<?> cls;
		if (isFirstRun())
			cls = firstRunClass;
		else
			cls = notFirstRunClass;
		Intent i = new Intent(ctx, cls);
		l.d(ctx + " launching next activity: " + cls + "(" + i + ")");
		ctx.startActivity(i);
	}

	public void scheduleNextActivity(int splashscreenduration) {
		l.d("Scheduling next activity launch for " + splashscreenduration
				+ "ms delay");
		new Handler().postDelayed(launchNextActivity, splashscreenduration);
	}

}
