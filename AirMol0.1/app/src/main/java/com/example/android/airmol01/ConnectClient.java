package com.example.android.airmol01;

import android.content.Context;
import android.os.Looper;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by thomas on 24/03/17.
 */

public class ConnectClient extends Thread{
    private Socket Socket;
    private String ipServer;
    private final int port = 49776;
    private Context context;
    private SensorListener sensorListener;

    public ConnectClient(String ipServer, Context context){
        this.ipServer = ipServer;
        this.context = context;
    }

    public void run(){
        Looper.prepare();
        try {
            this.Socket = new Socket(this.ipServer, this.port);
            this.sensorListener = new SensorListener(this.Socket, this.context);
            this.sensorListener.register();
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Looper.loop();
    }

    public void lockRotation() {
        if (this.Socket != null) {
            this.sensorListener.unregister();
        }
    }

    public void unlockRotation(){
        if (this.Socket != null) {
            this.sensorListener.register();
        }
    }

    public void cancel(){
        try {
            this.sensorListener.unregister();
            this.Socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void closeSocket(){
        if (this.Socket != null) {
            try {
                this.Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
