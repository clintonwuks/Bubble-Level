package com.example.assignment3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class portraitView extends View {
    private Paint white;
    private Rect square;
    private int width, height;


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
        white.setColor(0xFF7C7B7B);
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
       // this.canvas = canvas;
        // call the superclass method
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
    }

    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

}
