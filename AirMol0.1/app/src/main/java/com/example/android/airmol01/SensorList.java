package com.example.android.airmol01;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;



public class SensorList extends AppCompatActivity {
    // The sensor manager
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);
        // Instanicer le SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Faire la liste des capteurs de l'appareil
        listSensor();
    }

    /**
     * Lister les capteurs existant
     *
     * Trouve la liste de tous les capteurs existants, trouve un capteur spécifique ou l'ensemble des capteurs d'un type fixé.
     */
    private void listSensor() {

        // Trouver tous les capteurs de l'appareil :
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        // La chaîne descriptive de chaque capteur
        StringBuffer sensorDesc = new StringBuffer();

        for (Sensor sensor : sensors) {
            sensorDesc.append("NEW SENSOR DETECTED : \r\n");
            sensorDesc.append("\tName: " + sensor.getName() + "\r\n");
            sensorDesc.append("\tType: " + getType(sensor.getType()) + "\r\n");
            sensorDesc.append("Version: " + sensor.getVersion() + "\r\n");
            sensorDesc.append("Resolution (in the sensor unit): " + sensor.getResolution() + "\r\n");
            sensorDesc.append("Power in mA used by this sensor while in use" + sensor.getPower() + "\r\n");
            sensorDesc.append("Vendor: " + sensor.getVendor() + "\r\n");
            sensorDesc.append("Maximum range of the sensor in the sensor's unit." + sensor.getMaximumRange() + "\r\n");
            sensorDesc.append("Minimum delay allowed between two events in microsecond" + " or zero if this sensor only returns a value when the data it's measuring changes" + sensor.getMinDelay() + "\r\n");
            sensorDesc.append("\n \n");
        }

        displayMessage(sensorDesc.toString());


        // Faire quelque chose de cette liste
        //Toast.makeText(this, sensorDesc.toString(), Toast.LENGTH_LONG).show();

        // Pour trouver un capteur spécifique&#160;:
        //Sensor gyroscopeDefault = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        // Pour trouver tous les capteurs d'un type fixé :
        //List<Sensor> gyroscopes = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE);
    }

    private String getType(int type) {
        String strType;
        switch (type) {
            case Sensor.TYPE_ACCELEROMETER: strType = "TYPE_ACCELEROMETER";break;
            case Sensor.TYPE_GRAVITY:strType = "TYPE_GRAVITY";break;
            case Sensor.TYPE_GYROSCOPE:    strType = "TYPE_GYROSCOPE";    break;
            case Sensor.TYPE_LIGHT:strType = "TYPE_LIGHT";break;
            case Sensor.TYPE_LINEAR_ACCELERATION:strType = "TYPE_LINEAR_ACCELERATION";
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:strType = "TYPE_MAGNETIC_FIELD";break;
            case Sensor.TYPE_ORIENTATION:strType = "TYPE_ORIENTATION";break;
            case Sensor.TYPE_PRESSURE:strType = "TYPE_PRESSURE";break;
            case Sensor.TYPE_PROXIMITY:    strType = "TYPE_PROXIMITY";    break;
            case Sensor.TYPE_ROTATION_VECTOR:    strType = "TYPE_ROTATION_VECTOR";break;
            case Sensor.TYPE_TEMPERATURE:strType = "TYPE_TEMPERATURE";break;
            default: strType = "TYPE_UNKNOW";break;
        }
        return strType;
    }

    public void displayMessage(String message){
        TextView displayText = (TextView) findViewById(R.id.sensor_list);
        displayText.setText(message);
    }

}








