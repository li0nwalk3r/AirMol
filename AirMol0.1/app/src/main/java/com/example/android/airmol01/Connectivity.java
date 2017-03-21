package com.example.android.airmol01;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Connectivity extends AppCompatActivity {
    //public Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        final TextInputEditText input = (TextInputEditText) findViewById(R.id.IP);

        final Button bouton = (Button) findViewById(R.id.connexion);
        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //bouton.setEnabled(false);
                Log.d("\n\nINPUT", input.toString());
                Client client = new Client();
                client.execute(input.getText().toString());
            }
        });
    }
}

