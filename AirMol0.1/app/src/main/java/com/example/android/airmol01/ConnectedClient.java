package com.example.android.airmol01;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by thomas on 24/03/17.
 */

public class ConnectedClient extends Thread{
    private final Socket mSocket;
    private DataOutputStream output;
    private SensorListener sensorListener;
    private Context context;

    public ConnectedClient(Socket socket, Context context){
        this.mSocket = socket;
        this.context = context;
        try {
            output = new DataOutputStream(this.mSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        sensorListener = new SensorListener(this.mSocket, this.context);
        Log.d("\n\nCONNEXION 2 OK", "");
    }

    public void run(){
        Looper.prepare();
        this.sensorListener.register();
        /*while (true) {
            try {
                this.output.writeUTF("Hello World !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    public void cancel(){
        this.sensorListener.unregister();
    }

}
