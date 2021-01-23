package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // private fields of the class
    private TextView tv;
    private SensorManager sm;
    private float rotation_matrix[] = new float[16];
    private float orientation_values[] = new float[4];
    private float bearings[] =new float[500];
    private float pitch[] = new float[500];
    private float roll[] = new float[500];
    private int count= 500;
    private int count2= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get access to the text view and the sensor manager
        tv = (TextView) findViewById(R.id.tv);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
// attach a sensor to the rotation vector

       // Configuration c = getResources().getConfiguration();



        sm.registerListener(new SensorEventListener() {
                                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                                }
                                public void onSensorChanged(SensorEvent event) {
// get the new rotation matrix and convert them into orientation
//values
                                    SensorManager.getRotationMatrixFromVector(rotation_matrix,
                                            event.values);
                                    SensorManager.getOrientation(rotation_matrix, orientation_values);
// as the orientation values are unitless we need to convert themback
// into degrees

                                   // for (int i = 0; i < count; i++) {
                                        orientation_values[0] = (float)
                                                Math.toDegrees(orientation_values[0]);
                                        orientation_values[1] = (float)
                                                Math.toDegrees(orientation_values[1]);
                                        orientation_values[2] = (float)
                                                Math.toDegrees(orientation_values[2]);
// print out the values to the text view
                                        bearings[count2]=orientation_values[0];
                                        pitch[count2]=orientation_values[1];
                                        roll[count2]=orientation_values[2];

                                   // Log.d("mynewTAG", "onSensorChanged: for" + count2 );

                                        if (count2 == 499) {
                                            count2 = 0;
                                        } else {
                                            count2++;
                                        }

                                   // }
                                    tv.setText(
                                            orientation_values[0]
                                                    + " , " + orientation_values[1]
                                                    + " , " + orientation_values[2]);
                                }

                                //call invalidate from the other class



                            }, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_UI);
    }

    public float getBearingsmax(){
         //float[] bearings = new float[500];
        float bmax=getmax(bearings);
        return bmax;
    }

    public float getPitchmax(){
        //float[] pitch = new float[500];
        float pmax=getmax(pitch);
        return pmax;
    }

    public float getRollmax(){
        //float[] bearings = new float[500];
        float rmax=getmax(roll);
        return rmax;
    }

    public float getBearingsmin(){
        //float[] bearings = new float[500];
        float bmin=getmin(bearings);
        return bmin;
    }

    public float getPitchmin(){
        //float[] pitch = new float[500];
        float pmin=getmin(pitch);
        return pmin;
    }

    public float getRollmin(){
        //float[] bearings = new float[500];
        float rmin=getmin(roll);
        return rmin;
    }


    public float getmax(float [] array) {
        float max = 0;

        for(int i=0; i<array.length; i++ ) {
            if(array[i]>max) {
                max = array[i];
            }
        }
        return max;
    }

    public float getmin(float [] array) {
        float min = 0;

        for(int i=0; i<array.length; i++ ) {
            if(array[i]<min) {
                min = array[i];
            }
        }
        return min;
    }


}