package com.example.android.airmol01;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class Connectivity extends AppCompatActivity  {
    public ConnectClient client;
    private Button connection;
    private Button deconnection;
    private String ipServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        connection = (Button) findViewById(R.id.connexion);
        deconnection = (Button) findViewById(R.id.deconnection);

        connection.setEnabled(true);
        deconnection.setEnabled(false);



        clickHandlerConnexion(this);

        clickHandlerDeconnexion();
    }
    protected void clickHandlerConnexion(final Context context){
        final TextInputEditText input = (TextInputEditText) findViewById(R.id.IP);


        connection.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                ipServer = input.getText().toString();

                if (!ipServer.equals("")) {
                    try {
                        client = new ConnectClient(ipServer, context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connection.setEnabled(false);
                    deconnection.setEnabled(true);
                    client.start();
                }
                else{
                    Toast errorConnexion = Toast.makeText(v.getContext(), "Pas d'adresse ip entrée", Toast.LENGTH_LONG);
                    errorConnexion.show();
                }

            }
        });

    }

    protected void clickHandlerDeconnexion(){


        deconnection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deconnection.setEnabled(false);
                connection.setEnabled(true);
                client.cancel();
            }
        });
    }

    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
    }
}

