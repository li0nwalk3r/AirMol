package com.example.android.airmol01;

import android.os.AsyncTask;
import android.os.Looper;
import android.os.SystemClock;
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
            ListenSensor listener = new ListenSensor();
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            while (true) {
                byte[] tmp = listener.getValues().getBytes();
                output.write(tmp);
                SystemClock.sleep(2000L);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onPostExecute(Void result){
        if (this.socket != null) {
            try {
                socket.close();
                this.socket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() throws IOException {
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
        }
    }
}
