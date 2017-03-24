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

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by thomas on 21/03/17.
 */

public class Client extends AsyncTask<String, Void, Void> { // String : l'adresse entrée par l'utilisateur | Void : onProgress (on en a pas) | Void : La valeur retournée par doInBackground

    private int port = 8888;
    private Socket socket;

    @Override
    public Void doInBackground(String... params) {
        Looper.prepare();
        try {

            socket = new Socket(params[0], port);
            final DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            while (true) {
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg){
                        String data = msg.getData().getString("sensorData");
                        Log.d("\n\nMESSAGE", data);
                        try {
                            output.writeUTF("Hello world");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };



                //byte[] tmp = listener.getValues().getBytes();
                //output.write(tmp);
                //SystemClock.sleep(2000L);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onPostExecute(Void Result){
        if (this.socket != null) {
            try {
                socket.close();
                socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() throws IOException {
        if (this.socket != null) {
            this.socket.close();
            socket = null;
        }
    }
}
