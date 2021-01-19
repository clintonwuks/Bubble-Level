package com.example.assignment3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import static android.content.Context.SENSOR_SERVICE;

public class portraitView extends View {
    private Paint white, black;
    private Rect square;
    private int width, height;
    private String text;
    private TextView tv;
    private SensorManager sm;
    Paint textPaint;
    boolean altrue = true;
    MainActivity mObj;
    private float bearingArr[] = new float[500];
    private float pitchArr[] = new float[500];
    private float rollArr[] = new float[500];
    private float rotation_matrix[] = new float[16];
    private float orientation_values[] = new float[4];
    private String[] arrSTr = new String[3];

    static private final double GRAVITY = 9.81d;
    static private final double MIN_DEGREE = -10d;
    static private final double MAX_DEGREE = 10d;

    public portraitView(Context context) {
        super(context);
        init();
    }

    public portraitView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public portraitView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        //create paint object
        white = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);
        white.setColor(0xFF7C7B7B);
        textPaint.setColor(0xFFFFFFFF);
        black.setColor(0xFF000000);

        mObj = new MainActivity();
        bearingArr = mObj.getBearings();
        pitchArr = mObj.getPitch();
        rollArr = mObj.getRoll();



        tv = (TextView) ((Activity) getContext()).findViewById(R.id.tv);
        sm = (SensorManager) ((Activity) getContext()).getSystemService(SENSOR_SERVICE);
        sm.registerListener(new SensorEventListener() {
                                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                                }
                                public void onSensorChanged(SensorEvent event){
                                    SensorManager.getRotationMatrixFromVector(rotation_matrix,
                                            event.values);
                                    SensorManager.getOrientation(rotation_matrix, orientation_values);
                                    orientation_values[0] = (float)
                                            Math.toDegrees(orientation_values[0]);
                                    orientation_values[1] = (float)
                                            Math.toDegrees(orientation_values[1]);
                                    orientation_values[2] = (float)
                                            Math.toDegrees(orientation_values[2]);

//                                    text = tv.getText().toString();
                                    text= (orientation_values[0]
                                            + " , " + orientation_values[1]
                                            + " , " + orientation_values[2]);
                                    invalidate();

//                                    canvas.drawText(text, sqrHeight, sqrHeight, textPaint);


                                    Log.d("mytag2", "onSensorChanged: "+ orientation_values[0]
                                            + " , " + orientation_values[1]
                                            + " , " + orientation_values[2]);
                                }
                            }, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_UI);



    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Set board as per screen size
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int maxwidth = getMeasuredWidth();
        int maxheight = getMeasuredHeight();
        width = maxwidth;
        height = maxheight;
        setMeasuredDimension(width, height);

    }

    public void onDraw(Canvas canvas) {

        int sqrHeight = width / 8;
        int sqrWidth = height / 8;
        Log.d("sqrtings", "onDraw: "+ sqrHeight);


       // this.canvas = canvas;
        // call the superclass method




       // canvas.drawCircle(375, 375, 5,textPaint);
//       text = tv.getText().toString();
        super.onDraw(canvas);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
//                            square = new Rect(i * sqrWidth, j *
//                                    sqrHeight, (i + 1) * sqrWidth, (j +
//                                    1) * sqrHeight);

                        canvas.drawRect(i * sqrWidth, j * sqrHeight, (i + 1) * sqrWidth, (j + 1) * sqrHeight, white);

                    } else {
                        canvas.drawRect(i * sqrWidth, j * sqrHeight, (i + 1) * sqrWidth, (j + 1) * sqrHeight, white);

                    }
                } else {
                    if (j % 2 == 0) {

                        canvas.drawRect(i * sqrWidth, j *
                                sqrHeight, (i + 1) * sqrWidth, (j +
                                1) * sqrHeight, white);


                    } else {

                        canvas.drawRect(i * sqrWidth, j *
                                sqrHeight, (i + 1) * sqrWidth, (j +
                                1) * sqrHeight, white);

                    }
                }
            }
        }

        canvas.drawRect(100,(float)(375-10),650,(float)(375+10),black);

        canvas.drawCircle(375, 375, (float)2.5,textPaint);
        canvas.translate(0,0);
        arrSTr = text.split(",");
       // drawText(canvas, sqrHeight, sqrHeight);

      double  pitch = 700- Double.parseDouble(arrSTr[1]);
//        pitch = (float) Math.min(MAX_DEGREE+375, pitch);
//        pitch = (float) Math.max(375, pitch);
        Log.d("mytag3", "onDraw: "+pitch);
        //canvas.drawText(text, (float)((sqrHeight*Float.parseFloat(arrSTr[1]))/MIN_DEGREE), (float)((sqrHeight*Float.parseFloat(arrSTr[0]))/MAX_DEGREE), textPaint);
        canvas.drawCircle(60, (float) pitch, 5,textPaint);
        invalidate();

//        do {
//            //tv = (TextView) ((Activity) getContext()).findViewById(R.id.tv);
//            text = tv.getText().toString();
//            canvas.drawText(text, sqrHeight, sqrHeight, textPaint);
//            Log.d("mylog", "onDraw: "+text);
//            invalidate();
//        } while (altrue);



    }

    private void drawText(Canvas canvas, float i, float j) {
        canvas.drawText(text, i, j, textPaint);
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }




}
