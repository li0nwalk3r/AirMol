package com.example.android.airmol01;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static android.R.attr.data;

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
