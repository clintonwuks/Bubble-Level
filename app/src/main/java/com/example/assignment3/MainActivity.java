package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // private fields of the class
    private TextView tv;
    private SensorManager sm;
    private float rotation_matrix[] = new float[16];
    private float orientation_values[] = new float[4];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get access to the text view and the sensor manager
//        tv = (TextView) findViewById(R.id.tv);
//        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
//// attach a sensor to the rotation vector
//
//        Configuration c = getResources().getConfiguration();



//        sm.registerListener(new SensorEventListener() {
//                                public void onAccuracyChanged(Sensor sensor, int accuracy) {
//                                }
//                                public void onSensorChanged(SensorEvent event) {
//// get the new rotation matrix and convert them into orientation
////values
//                                    SensorManager.getRotationMatrixFromVector(rotation_matrix,
//                                            event.values);
//                                    SensorManager.getOrientation(rotation_matrix, orientation_values);
//// as the orientation values are unitless we need to convert themback
//// into degrees
//                                    orientation_values[0] = (float)
//                                            Math.toDegrees(orientation_values[0]);
//                                    orientation_values[1] = (float)
//                                            Math.toDegrees(orientation_values[1]);
//                                    orientation_values[2] = (float)
//                                            Math.toDegrees(orientation_values[2]);
//// print out the values to the text view
//                                    tv.setText(
//                                            orientation_values[0]
//                                                    + " , " + orientation_values[1] +" , "
//                                                    + " , " + orientation_values[2]);
//                                }
//                            }, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
//                SensorManager.SENSOR_DELAY_UI);
    }
}