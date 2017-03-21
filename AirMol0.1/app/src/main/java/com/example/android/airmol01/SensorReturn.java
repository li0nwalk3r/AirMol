package com.example.android.airmol01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class SensorReturn extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensor;
    private Sensor mRotationVector;
    private Sensor mGyroscope;
    private Sensor mAccelerometer;
    private Sensor mGravity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_return);
        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //List of sensors needed
        mRotationVector = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mGyroscope = sensor.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mAccelerometer = sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravity = sensor.getDefaultSensor(Sensor.TYPE_GRAVITY);

    }

    @Override
    protected void onResume() {
        //Method to start sensor activity
        super.onResume();
        sensor.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        sensor.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensor.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensor.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        //Method to stop sensors activity
        super.onPause();
        sensor.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor test, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Method to extract sensors values and prints them into referenced xml TextView

        //Rotation vector values
        float[] values = event.values;
        for (int i = 0; i < values.length; i++){
            values[i] = truncate(values[i]);
        }
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            TextView rotationVector = (TextView) findViewById(R.id.rotation_vector_values);
            rotationVector.setText("x: " + String.valueOf(values[0]) + "\ny: " + String.valueOf(values[1]) + "\nz: " + String.valueOf(values[2]));
        }

        //Gyroscope values
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            TextView gyroscope = (TextView) findViewById(R.id.gyroscope_values);
            gyroscope.setText("x: " + String.valueOf(values[0]) + "\ny: " + String.valueOf(values[1]) + "\nz: " + String.valueOf(values[2]));
        }

        //Accelerometer values
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            TextView accelerometer = (TextView) findViewById(R.id.accelerometer_values);
            accelerometer.setText("x: " + String.valueOf(values[0]) + "\ny: " + String.valueOf(values[1]) + "\nz: " + String.valueOf(values[2]));
        }

        //Gravity vector values
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            TextView gravity = (TextView) findViewById(R.id.gravity_values);
            gravity.setText("x: " + String.valueOf(values[0]) + "\ny: " + String.valueOf(values[1]) + "\nz: " + String.valueOf(values[2]));
        }
    }

    public float truncate(float value){
        float tmp = value * 100;
        tmp = Math.round(tmp);
        return tmp / 100;
    }
}
