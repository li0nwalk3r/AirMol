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
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SensorListener implements SensorEventListener {
    public SensorManager sensor;
    public Sensor rotationVector;
    public float[] data;
    public Socket socket;
    public DataOutputStream output;
    private Context context;

    public SensorListener(Socket socket, Context context) {
        this.socket = socket;
        this.context = context;

        sensor = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        rotationVector = sensor.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("\n\nSENSOR OK", "");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        data = event.values;
        //Log.d("\nDATA : ", "Hello World !");
        try {
            output.flush();
            output.writeUTF(" X : " + String.valueOf(data[0]) + "\n Y : " + String.valueOf(data[1]) + "\n Z : " + String.valueOf(data[2]) + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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