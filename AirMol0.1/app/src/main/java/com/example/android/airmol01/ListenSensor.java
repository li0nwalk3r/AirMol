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
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ListenSensor implements SensorEventListener {
    public SensorManager sensor;
    public Sensor rotationVector;
    public float[] values;
    public Handler sensorData;
    public Message msg;
    public Bundle b;

    public ListenSensor(Context context) {
        sensorData = new Handler();
        //msg = new Message();

        sensor = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVector = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] data = event.values;
        Log.d("\n\nLISTEN SENSOR", String.valueOf(data[0]));
        msg = Message.obtain();
        b = new Bundle();
        b.putString("sensorData", "TEST");
        this.msg.setData(b);
        sensorData.sendMessage(msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void register(){
        sensor.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister(){
        sensor.unregisterListener(this);
    }
}