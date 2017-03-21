package com.example.android.airmol01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openConnectivity (View view){
        Intent connectivity = new Intent(this, Connectivity.class);
        startActivity(connectivity);
    }

    public void openSensorList (View view){
        Intent sensorList = new Intent(this, SensorList.class);
        startActivity(sensorList);
    }

    public void openSensorReturn (View view){
        Intent sensorReturn = new Intent(this, SensorReturn.class);
        startActivity(sensorReturn);
    }
}