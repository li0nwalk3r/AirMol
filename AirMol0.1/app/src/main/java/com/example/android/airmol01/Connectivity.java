package com.example.android.airmol01;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Connectivity extends AppCompatActivity {
    //public Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectivity);

        clickHandler();

        Context context = getApplicationContext();
        CharSequence text = "Welcome mother fucker";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


        /*final TextInputEditText input = (TextInputEditText) findViewById(R.id.IP);

        final Button bouton = (Button) findViewById(R.id.connexion);
        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //bouton.setEnabled(false);
                Client client = new Client();
                client.execute(input.getText().toString());
            }
        });*/
    }

    protected void clickHandler(){
        final TextInputEditText input = (TextInputEditText) findViewById(R.id.IP);

        final Button bouton = (Button) findViewById(R.id.connexion);
        bouton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                CharSequence text = "Connection en cours";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //bouton.setEnabled(false);
                Client client = new Client();
                client.execute(input.getText().toString());
            }
        });
    }


}

