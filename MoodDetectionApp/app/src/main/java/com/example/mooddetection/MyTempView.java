package com.example.mooddetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;


public class MyTempView extends View {

    private String temp;
    private Paint currentTmp;
    private Paint mPaint;
    private Paint paintCircle;
    private Paint paintLine;
    private Bitmap bitmaplv;
    private Bitmap bitmaplan;
    private Bitmap bitmapred;
    private Context context;
    private Paint mPaintOther;
    private Paint left;
    private double ratio;

    public MyTempView(Context context, AttributeSet attrs) {
        super(context, attrs);
        currentTmp = new Paint();
        currentTmp.setAntiAlias(true);
        currentTmp.setTextSize(35);
        currentTmp.setColor(Color.parseColor("#61BEE7"));
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#bce3fe"));;
        mPaint.setAntiAlias(true);
        paintCircle = new Paint();
        paintCircle.setColor(Color.parseColor("#61BEE7"));
        paintCircle.setAntiAlias(true);
        paintCircle.setTextSize(40);
        left=new Paint();
        left.setAntiAlias(true);
        bitmaplv = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_s);
        bitmaplan = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_lan_small);
        bitmapred = BitmapFactory.decodeResource(getResources(),
                R.mipmap.kedu_red_small);

    }


    public void setTemp(String temp,Context context,double ratio) {
        this.temp = temp;
        this.context = context;
        this.ratio=ratio;
        this.invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,700);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 屏幕宽度的一半
        int width = getWidth() / 2;

        Paint p=new Paint();
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质，打造出一个线性梯度沿著一条线。
        p.setShader(mShader);
        p.setAntiAlias(true);
        int y = getHeight()*3/4-5;
        //textPaint.setColor(Color.parseColor("#f9847b"));


        String dec="PerfectMOOD";
        //textPaint.setColor(Color.parseColor("#479aed"));

        Bitmap current=bitmaplv;
        //thermometer rectangle
        canvas.drawRect(0, 0, width*2 , getHeight(), mPaint);
        currentTmp.setColor(Color.parseColor("#3DB475"));
        paintCircle.setColor(Color.parseColor("#3DB475"));
        left.setColor(Color.parseColor("#d9f3ff"));
        if(ratio<=0.3)
        {
            current=bitmapred;
            paintCircle.setColor(Color.RED);
            currentTmp.setColor(Color.RED);
            dec="BADMOOD";
        }
        else if(ratio>0.3&&ratio<0.8)
        {
            current=bitmaplan;
            paintCircle.setColor(Color.BLUE);
            currentTmp.setColor(Color.BLUE);
            dec="GOODMOOD";
        }
        else
        {
            ratio=0.8;
        }
        canvas.drawCircle(width, getHeight()*7/8-20, getHeight()/8+20, p);
        canvas.drawRect(width -getHeight()/8+10, 0, width +getHeight()/8-10,(float)(y*(1-ratio)), left);
        canvas.drawRect(width -getHeight()/8+10, (float)(y*(1-ratio)), width +getHeight()/8-10,getHeight()*3/4-5, p);

        canvas.drawBitmap(current, width +getHeight()/8-10,(float)(y*(1-ratio))-current.getHeight()/2 , mPaint);
        canvas.drawText(temp , width +getHeight()/8-10+ current.getWidth() ,
                (float)(y*(1-ratio)), paintCircle);
        canvas.drawText(dec, width +getHeight()/8-10+ current.getWidth(),
                (float)(y*(1-ratio))+30, currentTmp);

    }
}

