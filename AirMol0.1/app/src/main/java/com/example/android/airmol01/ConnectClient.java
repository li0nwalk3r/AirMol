package com.example.android.airmol01;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by thomas on 24/03/17.
 */

public class ConnectClient extends Thread{
    private Socket Socket;
    private String ipServer;
    private final int port = 8888;
    private Context context;
    private SensorListener sensorListener;

    public ConnectClient(String ipServer, Context context) throws IOException {
        this.ipServer = ipServer;
        this.context = context;
    }

    public void run(){
        Looper.prepare();
        try {
            this.Socket = new Socket(this.ipServer, this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.sensorListener = new SensorListener(this.Socket, this.context);
        this.sensorListener.register();
        Looper.loop();
    }

    public void cancel(){
        try {
            this.sensorListener.unregister();
            this.Socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
