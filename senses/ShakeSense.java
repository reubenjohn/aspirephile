package com.aspirephile.shared.senses;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.aspirephile.shared.debug.Logger;
import com.aspirephile.shared.debug.NullPointerAsserter;

public class ShakeSense {
    Logger l = new Logger(ShakeSense.class);
    private NullPointerAsserter asserter = new NullPointerAsserter(l);

    private SensorManager mSensorManager;

    private OnShakeListener listener = new OnShakeListener() {

        @Override
        public void onShaken() {
            l.d("Device shaken");
        }
    };
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    long startOfJerk = 0;
    long startOfShakes = 0;
    int shakes = 0;

    private static class preferences {
        protected static short shakesRequired = 4;
        protected static float accelerationRequired = 5;
        protected static int shakeTimeout = 2000;
        protected static int jerkTimeout = 100;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            long cachedSystemTime = System.currentTimeMillis();
            if (mAccel > preferences.accelerationRequired) {
                if (startOfJerk == 0) {
                    startOfJerk = cachedSystemTime;
                }
                if (startOfShakes == 0)
                    startOfShakes = cachedSystemTime;
                else if ((cachedSystemTime - startOfShakes) > preferences.shakeTimeout) {
                    l.d("Shake timeout ("
                            + (cachedSystemTime - startOfShakes) + ")");
                    shakes = 0;
                    startOfShakes = cachedSystemTime;
                }
            } else if (startOfJerk != 0) {
                if ((cachedSystemTime - startOfJerk) > preferences.jerkTimeout) {
                    startOfJerk = 0;
                    shakes++;
                    l.d("Phone jerk " + shakes
                            + " (acceleration>"
                            + preferences.accelerationRequired + ")");
                    if (shakes >= preferences.shakesRequired) {
                        startOfJerk = shakes = 0;
                        listener.onShaken();
                    }
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void onResume() {
        l.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void onStop() {
        l.onStop();
        mSensorManager.unregisterListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    public void initialize(SensorManager sensorManager) {
        l.initializeFields();
        if (asserter.assertPointer(sensorManager)) {
            mSensorManager = sensorManager;
            mSensorManager.registerListener(mSensorListener,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
            mAccel = 0.00f;
            mAccelCurrent = SensorManager.GRAVITY_EARTH;
            mAccelLast = SensorManager.GRAVITY_EARTH;
        }
    }

    public void setOnShakeListener(OnShakeListener listener) {
        if (asserter.assertPointer(listener))
            this.listener = listener;
    }

}