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
    private Socket mSocket;
    private String ipServer;
    private final int port = 8888;
    private Context context;
    private ConnectedClient client;

    public ConnectClient(String ipServer, Context context) throws IOException {
        Log.d("\n\nCONNEXION1 OK", "");
        this.ipServer = ipServer;
        this.context = context;
    }

    public void run(){
        Looper.prepare();
        try {
            this.mSocket = new Socket(this.ipServer, this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.client = new ConnectedClient(this.mSocket, this.context);
        this.client.start();
        Looper.loop();
    }

    public void cancel(){
        try {
            client.cancel();
            mSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
