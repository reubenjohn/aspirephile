package com.aspirephile.shared.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aspirephile.shared.debug.Logger;
import com.aspirephile.shared.debug.NullPointerAsserter;
import com.funtainment.R;

public class ProcessErrorUI extends Fragment {
    private final Logger l = new Logger(ProcessErrorUI.class);
    private final NullPointerAsserter asserter = new NullPointerAsserter(l);

    TextView error;
    Button retry;

    boolean isErrorSet, isAnimationEnabled;

    public ProcessErrorUI() {
        l.d("Instantiating fragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        l.onCreate();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        l.onCreateView();
        View v = inflater.inflate(R.layout.fragment_process_error_ui, container, false);
        bridgeXML(v);
        initializeFields();
        return v;
    }

    private void initializeFields() {
        l.initializeFields();
        isErrorSet = false;
        isAnimationEnabled = true;
        if (isAdded()) {
            internalHide(false, true);
        }
    }

    private void bridgeXML(View v) {
        l.bridgeXML();
        error = (TextView) v.findViewById(R.id.tv_process_error_ui);
        retry = (Button) v.findViewById(R.id.b_process_error_ui_retry);
        if (asserter.assertPointer(error, retry))
            l.d("Bridging successful");
    }

    @Override
    public void onResume() {
        l.onResume();
        super.onResume();
        if (!isErrorSet) {
            hide(false);
        }
    }

    public void setError(String error) {
        this.error.setText(error);
        internalSetError();
    }

    public void setError(int stringId) {
        this.error.setText(stringId);
        internalSetError();
    }

    private void internalSetError() {
        isErrorSet = true;
        show();
    }


    public void setAnimationsEnabled(boolean isAnimationEnabled) {
        this.isAnimationEnabled = isAnimationEnabled;
    }


    public void resolveErrors() {
        isErrorSet = false;
        hide();
    }

    private void show() {
        if (!isVisible()) {
            l.d("Showing error");
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            if (isAnimationEnabled) {
                manager.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
            manager.show(this).commit();
        }
    }

    private void show(boolean animate) {
        if (!isVisible()) {
            l.d("Showing error");
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            if (animate) {
                manager.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
            manager.show(this).commit();
        }
    }

    private void hide() {
        internalHide(this.isAnimationEnabled, false);
    }

    private void hide(boolean isAnimationEnabled) {
        internalHide(isAnimationEnabled, false);
    }

    private void internalHide(boolean animate, boolean force) {
        if (isVisible() || force) {
            l.d("Hiding error");
            FragmentTransaction manager = getFragmentManager().beginTransaction();
            if (animate) {
                manager.setCustomAnimations(android.R.anim.fade_in, android.R.anim.slide_out_right);
            }
            manager.hide(this).commit();
        }
    }


    public void setOnClickListener(View.OnClickListener l) {
        if (asserter.assertPointer(retry))
            retry.setOnClickListener(l);
    }
}
