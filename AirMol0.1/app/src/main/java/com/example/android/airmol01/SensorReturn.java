/*
 *
 *  This file is part of AirMol.
 *
 *     AirMol is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     AirMol is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 * along with AirMol. If not, see <http://www.gnu.org/licenses/>.
 */

package com.example.android.airmol01;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class SensorReturn extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensor;
    private Sensor mRotationVector;
    private TextView rotationVector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_return);

        rotationVector = (TextView) findViewById(R.id.rotation_vector_values);


        sensor = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mRotationVector = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

    }

    @Override
    protected void onResume() {
        //Method to start sensor activity
        super.onResume();
        sensor.registerListener(this, mRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
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
        Log.d("\nSENSOR", "Changement valeur sensor");
        //Rotation vector values
        float[] values = event.values;
        for (int i = 0; i < values.length; i++){
            values[i] = truncate(values[i]);
        }
        rotationVector.setText("x: " + String.valueOf(values[0]) + "\ny: " + String.valueOf(values[1]) + "\nz: " + String.valueOf(values[2]));
    }


    public float truncate(float value){
        float tmp = value * 100;
        tmp = Math.round(tmp);
        return tmp / 100;
    }
}
