package com.example.android.airmol01;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by thomas on 21/03/17.
 */

public class Client extends AsyncTask<String, Void, Void> { // String : l'adresse entrée par l'utilisateur | Void : onProgress (on en a pas) | Void : La valeur retournée par doInBackground

    public int port = 8888;

    @Override
    public Void doInBackground(String... params) {

        try {
            //this.serverAddress = InetAddress.getByName(params[0]);
            Socket socket = new Socket(params[0], port);
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            //String msg = "Hello World !";
            //byte[] toSend = msg.getBytes();
            float[] move =  new float[]{0.11f, 0.59f, .022f};
            //output.write(toSend);
            output.writeFloat(move[0]);
            output.writeFloat(move[1]);

            output.write("fin".getBytes());

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
