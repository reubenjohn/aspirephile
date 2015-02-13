package com.aspirephile.shared.debug;

import android.util.Log;

public class Logger {

    String tag;
    Class cls;

    public Logger(String tag) {
        setTag(tag);
    }

    public Logger(Class<?> cls) {
        setTag(cls);
        this.cls = cls;
    }

    public void setTag(String tag) throws NullPointerException {
        if (tag != null) {
            this.tag = tag;
        } else
            throw new NullPointerException();
    }

    public void setTag(Class<?> cls) throws NullPointerException {
        if (cls != null) {
            this.tag = cls.getName();
        } else
            throw new NullPointerException();
    }

    public void d(String msg) {
        Log.d(tag, msg);
    }

    public void e(String msg) {
        Log.e(tag, msg);
    }

    public void i(String msg) {
        Log.i(tag, msg);
    }

    public void w(String msg) {
        Log.w(tag, msg);
    }

    public void onConstructor() {
        Log.d(tag, cls.getSimpleName() + ": Constructor called");
    }

    public void onCreate() {
        Log.d(tag, cls.getSimpleName() + ": onCreate");
    }

    public void onCreateView() {
        Log.d(tag, cls.getSimpleName() + ": onCreate");
    }

    public void onDestroy() {
        Log.d(tag, cls.getSimpleName() + ": onDestroy");
    }

    public void onResume() {
        Log.d(tag, cls.getSimpleName() + ": onResume");
    }

    public void onPostResume() {
        Log.d(tag, cls.getSimpleName() + ": onPostResume");
    }

    public void onPause() {
        Log.d(tag, cls.getSimpleName() + ": onPause");
    }

    public void onStart() {
        Log.d(tag, cls.getSimpleName() + ": onStart");
    }

    public void onStop() {
        Log.d(tag, cls.getSimpleName() + ": onStop");
    }

    public void onActivityResult() {
        Log.d(tag, cls.getSimpleName() + ": onActivityResult");
    }

    public void onCreateOptionsMenu() {
        Log.d(tag, cls.getSimpleName() + ": onCreateOptionsMenu");
    }

    public void initializeFields() {
        Log.d(tag, cls.getSimpleName() + ": onCreateOptionsMenu");
    }

    public void bridgeXML() {
        Log.d(tag, cls.getSimpleName() + ": initializeFields");
    }

    public void bridgeXML(boolean success) {
        Log.d(tag, cls.getSimpleName() + ": bridgeXML -> " + (success ? "success" : "failure"));
    }

}
