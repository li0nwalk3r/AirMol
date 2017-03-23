package com.example.android.airmol01;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Connectivity extends AppCompatActivity {
    private Client client;
    private Button connection;
    private Button deconnection;
    private String ipServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        connection = (Button) findViewById(R.id.connexion);
        deconnection = (Button) findViewById(R.id.deconnection);

        connection.setEnabled(true);
        deconnection.setEnabled(false);

        clickHandlerConnexion();

        clickHandlerDeconnexion();
    }
    protected void clickHandlerConnexion(){
        final TextInputEditText input = (TextInputEditText) findViewById(R.id.IP);


        connection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ipServer = input.getText().toString();
                if (!ipServer.equals("")) {
                    connection.setEnabled(false);
                    deconnection.setEnabled(true);
                    client = new Client();
                    client.execute(ipServer);
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
                try {
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

