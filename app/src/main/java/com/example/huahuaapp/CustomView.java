package com.example.huahuaapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    List<Path> listStrokes = new ArrayList<>();
    Path pathStroke;
    Bitmap memBMP;
    Paint memPaint;
    Canvas memCanvas;
    boolean mBooleanOnTouch = false;

    float oldx;
    float oldy;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pathStroke = new Path();
                pathStroke.moveTo(x,y);
                oldx = x;
                oldy = y;
                mBooleanOnTouch = true;
                listStrokes.add(pathStroke);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(mBooleanOnTouch){
                    pathStroke.quadTo(oldx,oldy,x,y);
                    drawStrokes();
                    mBooleanOnTouch = false;
                }
                break;
        }
        return true;
    }

    public void drawStrokes() {
        if(memCanvas == null){
            memBMP = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
            memCanvas = new Canvas();
            memCanvas.setBitmap(memBMP);
            memPaint = new Paint();
            memPaint.setAntiAlias(true);
            memPaint.setColor(Color.RED);
            memPaint.setStyle(Paint.Style.STROKE);
            memPaint.setStrokeWidth(5);
        }

        for(Path path: listStrokes){
            memCanvas.drawPath(path,memPaint);
        }

        invalidate();
    }

    @Override
    protected  void onDraw(Canvas canvas){
        Paint paint = new Paint();
        if(memBMP != null)
            canvas.drawBitmap(memBMP,0,0,paint);

    }
}
