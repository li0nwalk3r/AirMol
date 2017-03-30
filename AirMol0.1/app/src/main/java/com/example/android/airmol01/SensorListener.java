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
import android.opengl.Matrix;

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
    private float[] lastQ={0.0f,0.0f,0.0f,1.0f};

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
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] currentQ = event.values;



        float[] conjugatedQ=new float[4];
        for(int i=0;i<3;i++){
            conjugatedQ[i]=-currentQ[i];
        }
        conjugatedQ[3]=currentQ[3];

        float[] combinedQ={
                conjugatedQ[3]*this.lastQ[0]+conjugatedQ[0]*this.lastQ[3]+conjugatedQ[1]*this.lastQ[2]-conjugatedQ[2]*this.lastQ[1],
                conjugatedQ[3]*this.lastQ[1]-conjugatedQ[0]*this.lastQ[2]+conjugatedQ[1]*this.lastQ[3]+conjugatedQ[2]*this.lastQ[0],
                conjugatedQ[3]*this.lastQ[2]+conjugatedQ[0]*this.lastQ[1]-conjugatedQ[1]*this.lastQ[0]+conjugatedQ[2]*this.lastQ[3],
                conjugatedQ[3]*this.lastQ[3]-conjugatedQ[0]*this.lastQ[0]-conjugatedQ[1]*this.lastQ[1]-conjugatedQ[2]*this.lastQ[2]
        };

        for (int i = 0; i < lastQ.length; i++){
            lastQ[i] = currentQ[i];
        }

        //this.lastQ=currentQ;

        float thetaRad = (float) (2.0f * Math.acos(combinedQ[3]));
        float thetaDeg = (float) (thetaRad * 180.0f/Math.PI);
        float sinThetaSurDeux = (float) Math.sin(thetaRad/2);
        for (int i = 0; i < 3; i ++){
            combinedQ[i]/=sinThetaSurDeux;
        }
        combinedQ[3] = thetaDeg;

        float[] rt = new float[9];
        float[] toTest = {combinedQ[0], combinedQ[1], combinedQ[2]};
        SensorManager.getRotationMatrixFromVector(rt, toTest);


        String x = String.valueOf(truncate(combinedQ[0]));
        String y = String.valueOf(truncate(combinedQ[1]));
        String z = String.valueOf(truncate(combinedQ[2]));
        String theta = String.valueOf(truncate(combinedQ[3]));

        //String time = String.valueOf(System.currentTimeMillis());

        String finalResult = x + ',' + y + ',' + z + ',' + theta;
        byte[] toSend = finalResult.getBytes();

        try {
            output.write(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void register(){
        sensor.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void unregister(){
        sensor.unregisterListener(this);
    }

    public float truncate(float valeur){
        float tmp = valeur * 100;
        tmp = Math.round(tmp);
        return tmp / 100;
    }

}