package com.aspirephile.shared.debug;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

@SuppressWarnings("UnusedDeclaration")
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

    public void v(String msg) {
        Log.v(tag, msg);
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
        Log.d(tag, cls.getSimpleName() + ": onCreateView");
    }

    public void onCreateDialog() {
        Log.d(tag, cls.getSimpleName() + ": onCreateDialog");
    }

    public void onDestroy() {
        Log.w(tag, cls.getSimpleName() + ": onDestroy");
    }

    public void onDestroyView() {
        Log.w(tag, cls.getSimpleName() + ": onDestroyView");
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result;
        if (resultCode == Activity.RESULT_OK) {
            result = "RESULT_OK";
            Log.i(tag, cls.getSimpleName() + ": onActivityResult -> requestCode: " + requestCode
                    + ", result code: " + result
                    + ", data: " + data);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            result = "RESULT_CANCELED";
            Log.e(tag, cls.getSimpleName() + ": onActivityResult -> requestCode: " + requestCode
                    + ", result code: " + result
                    + ", data: " + data);
        } else if (resultCode == Activity.RESULT_FIRST_USER) {
            result = "RESULT_FIRST_USER";
            Log.d(tag, cls.getSimpleName() + ": onActivityResult -> requestCode: " + requestCode
                    + ", result code: " + result
                    + ", data: " + data);
        } else {
            result = String.valueOf(resultCode);
            Log.e(tag, cls.getSimpleName() + ": onActivityResult -> requestCode: " + requestCode
                    + ", result code: " + result
                    + ", data: " + data);
        }
    }

    public void onCreateOptionsMenu() {
        Log.d(tag, cls.getSimpleName() + ": onCreateOptionsMenu");
    }

    public void initializeFields() {
        Log.d(tag, cls.getSimpleName() + ": initializeFields");
    }

    public void bridgeXML() {
        Log.d(tag, cls.getSimpleName() + ": bridgeXML");
    }

    public void bridgeXML(boolean success) {
        Log.d(tag, cls.getSimpleName() + ": bridgeXML -> " + (success ? "success" : "failure"));
    }

    public void onRestoreInstanceState() {
        Log.d(tag, cls.getSimpleName() + ": onRestoreInstanceState");
    }

    public void onSaveInstanceState() {
        Log.d(tag, cls.getSimpleName() + ": onSaveInstanceState");
    }

    public void onAttach() {
        Log.d(tag, cls.getSimpleName() + ": onAttach");
    }

    public void onDetach() {
        Log.d(tag, cls.getSimpleName() + ": onDetach");
    }

}
