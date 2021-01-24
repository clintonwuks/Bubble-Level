package com.example.assignment3;

import android.annotation.SuppressLint;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static android.content.Context.SENSOR_SERVICE;

public class portraitView extends View {
    private Paint white, black, green, textp, line;
    private Rect square;
    private int width, height;
    private String text,text1,text2;
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

    private float bearings[] =new float[500];
    private float pitch[] = new float[500];
    private float roll[] = new float[500];
    private int count;
    private int count2;

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
        //INITIALISE VARIABLES AND SENSOR MANAGER
        white = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        textp = new Paint();
        line = new Paint();
        textp.setColor(0xFFFFFFFF);
        textp.setStyle(Paint.Style.FILL);
         textp.setTextSize(35);
        line.setStrokeWidth(5f);
        line.setColor(0xFF7C7B7B);
        white.setColor(0xFF7C7B7B);
        textPaint.setColor(0xFFFFFFFF);
        black.setColor(0xFF000000);
        black.setStyle(Paint.Style.FILL_AND_STROKE);
        green.setColor(0xFF95DD42);

        mObj = new MainActivity();

        count= 500;
        count2= 0;

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

                                    bearings[count2]=orientation_values[0];
                                    pitch[count2]=orientation_values[1];
                                    roll[count2]=orientation_values[2];

                                    if (count2 == 499) {
                                        count2 = 0;
                                    } else {
                                        count2++;
                                    }

                                    invalidate();

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

        super.onDraw(canvas);
        //DRAW GREY BACKGROUND

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (i % 2 == 0) {
                    if (j % 2 == 0) {

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
//IF DEVICE IS FACING FRONT VIEW

        if (calcDeg(orientation_values[1]) < 60) {

            canvas.drawRect(width / (float) 2.85, (float) (375 - 10),
                    width / (float) 1.5, (float) (375 + 10), black);

            canvas.translate(0, 0);


            double pitch = width / (float) 2 - orientation_values[2];
            pitch = (float) Math.max(MIN_DEGREE + (width / (float) 2), pitch);
            pitch = (float) Math.min((width / (float) 2) + MAX_DEGREE, pitch);
            canvas.drawCircle((float) pitch, 365, (float) 15, white);


            text= ("X-axis : " + orientation_values[2]);
             text1="Max Value : "+getRollmax();
             text2= "Min Value : "+ getRollmin();

            canvas.drawText(text, height/35, width-85, textp);
            canvas.drawText(text1, height/35, width-50, textp);
            canvas.drawText(text2, height/35, width-20, textp);
            invalidate();


            //AN ELSE FOR WHEN THW VALUE IS ON A FLAT SURFACE

        } else
        {
            //LOGIC TO MOVE THE BUBBLE BASED ON THE ORIENTATION VALUE THAT RESPONDS TO SELECTED MOVEMENTS

            double xarc = (width / (float) 2) - (orientation_values[2]);
            double yarc = (width / (float) 2) +(orientation_values[1]);

            double northx ;
            double northy ;

            //LOGIC TO MOVE THE NORTH LINE BASED ON THE ORIENTATION VALUE THAT RESPONDS TO SELECTED MOVEMENTS
            if (orientation_values[0] < 0){

                 northx = (width / (float) 2) + (round((orientation_values[0]),6)*10);
                 northy = ((width / (float) 2) -500 )- (orientation_values[0]*10);
            }
            else {
                northx = (width / (float) 2) + (round((orientation_values[0]),6)*10);
              //  northy=240;
                northy = ((width / (float) 2) -300 )+ (orientation_values[0])*10;
            }


//DRAW THE OUTER AND INNER CIRCLES WITH THE MID POINT AND NORTH LINE
            canvas.drawCircle(width / (float) 2.0, width / (float) 2.0,  300, green);
            canvas.drawCircle(width / (float) 2.0, width / (float) 2.0,  75, black);
            canvas.drawLine(width / (float) 2.0, width / (float) 2.3, width / (float) 2,  width / (float) 1.8 , line);
            canvas.drawLine((width / (float) 2.3), (width / (float) 2.0), (width / (float) 1.75),  (width / (float) 2.0) , line);
            canvas.translate(0, 0);
            //LOGIC TO MOVE THE BUBBLE BASED ON THE ORIENTATION VALUE THAT RESPONDS TO SELECTED MOVEMENTS

            canvas.drawCircle((float)xarc, (float)yarc, 50, white);

            //LOGIC TO MOVE THE NORTH LINE BASED ON THE ORIENTATION VALUE FOR THE BEARING

            canvas.drawLine((float) northx, (float)northy,// arrow east
                    width / (float) 2.0,  width / (float) 2.0 , line);

            text= ("X-axis : " + orientation_values[2] + " \t\t\tY-axis :  " + orientation_values[1]);
            text1= ("X-axis Max Value : "+getRollmax() + "\t\t\tX-axis Min Value : "+ getRollmin());
            text2=("Y-axis Max Value: "+getPitchmax() + "\t\t\tY-axis Min Value : "+ getPitchmin());

            canvas.drawText(text, height/35, width-85, textp);
            canvas.drawText(text1, height/35, width-50, textp);
            canvas.drawText(text2, height/35, width-20, textp);


            invalidate();



        }
    }


    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }



        public static double round(double val, int place){
            if(place < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(val);
            bd = bd.setScale(place, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

    private double calcDeg(double round) {
        double res;
        res = 90 + round;
        return res;
    }


    public float getPitchmax(){
        float pmax=getmax(pitch);
        return pmax;
    }

    public float getRollmax(){
        float rmax=getmax(roll);
        return rmax;
    }


    public float getPitchmin(){
        float pmin=getmin(pitch);
        return pmin;
    }

    public float getRollmin(){
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
        float min = array[0];

        for(int i=0; i<array.length; i++ ) {
            if(array[i]<min) {
                min = array[i];
            }
        }
        return min;
    }




}
