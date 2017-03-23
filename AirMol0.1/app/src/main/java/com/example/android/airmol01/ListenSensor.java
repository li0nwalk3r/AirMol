package com.example.android.airmol01;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ListenSensor extends Activity implements SensorEventListener, Runnable {
    public SensorManager sensor;
    public Sensor rotationVector;
    public float[] values;
    public Handler sensorData;

    public ListenSensor(){}

    public String getValues(){
        return "Hello World";
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("\n\nLISTEN SENSOR", this.values.toString());
        this.values = event.values;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void run() {
        Looper.prepare();
        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationVector = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensor.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
    }
}