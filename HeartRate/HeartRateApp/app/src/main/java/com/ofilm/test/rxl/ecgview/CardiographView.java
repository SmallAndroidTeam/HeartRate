package com.ofilm.test.rxl.ecgview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ofilm.test.rxl.R;



/**
 * Create By rongxinglan IN 2018/9/12
 */
public class CardiographView extends View{
    //画笔
    protected Paint mPaint;
    //自身的大小
    protected int mWidth,mHeight;
    private int grid_hori,grid_ver;//横、纵线条数

    private float gap_grid;//网格间距
    private int width,height;//本页面宽，高
    public int xori;//原点x坐标



    public CardiographView(Context context) {
        this(context,null);
    }

    public CardiographView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CardiographView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed){
            xori = 0;
            gap_grid = 50.0f;
            width = getWidth();
            height = getHeight();
            grid_hori = height/(int)gap_grid;
            grid_ver = width/(int)gap_grid;
            Log.e("json","本页面宽： " + width +"  高:" + height);
        }

        super.onLayout(changed, left, top, right, bottom);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initBackground(canvas);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * 画背景网格
     */
    private void initBackground(Canvas canvas){
        //横线
        for (int i = 1 ; i < grid_hori + 2 ; i ++){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(getResources().getColor(R.color.line)); //<color name="data_pr">#0a7b14</color>
            paint.setStrokeWidth(1.0f);
            Path path = new Path();
            path.moveTo(xori, gap_grid * (i-1) + (height-grid_hori*gap_grid)/2);
            path.lineTo(width,gap_grid * (i-1) + (height-grid_hori*gap_grid)/2);
            canvas.drawPath(path,paint);
        }
        //竖线
        for (int i = 1 ; i < grid_ver + 2 ; i ++){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(getResources().getColor(R.color.line));
            paint.setStrokeWidth(1.0f);
            Path path = new Path();
            path.moveTo(gap_grid * (i-1) + (width-grid_ver*gap_grid)/2, 0);
            path.lineTo(gap_grid * (i-1) + (width-grid_ver*gap_grid)/2,height);
            canvas.drawPath(path,paint);
        }
    }

}
