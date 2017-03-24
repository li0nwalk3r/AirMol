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
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class Connectivity extends AppCompatActivity {
    private Client client;
    private Button connection;
    private Button deconnection;
    private String ipServer;
    private ListenSensor listener;
    private AsyncTask<String, Void, Void> socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //ListenSensor listener;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        connection = (Button) findViewById(R.id.connexion);
        deconnection = (Button) findViewById(R.id.deconnection);

        listener = new ListenSensor(this);
        //listener.register();

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
                client = new Client();
                if (!ipServer.equals("")) {
                    connection.setEnabled(false);
                    deconnection.setEnabled(true);
                    socket = client.execute(ipServer);
                    listener.register();
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
                listener.unregister();
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

    public void onPause(){
        super.onPause();
        this.listener.unregister();
    }

    public void onResume(){
        super.onResume();
        this.listener.register();
    }
}

