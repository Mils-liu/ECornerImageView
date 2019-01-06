package com.mils.ecornerimageview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.mils.ecornerimageview.R;
import com.mils.ecornerimageview.util.DisplayUtils;

/**
 * Created by Administrator on 2019/1/3.
 */

public class ECornerImageView extends android.support.v7.widget.AppCompatImageView {

    private final int CORNERWIDTH = DisplayUtils.dp2px(getContext(),10);
    private final int CORNERHEIGHT = DisplayUtils.dp2px(getContext(),10);

    private String TAG = "ECornerImageView";

    private Boolean leftTop = true;
    private Boolean lefBottom = true;
    private Boolean rightTop = true;
    private Boolean rightBottom =true;

    private Path path;

    private Paint cornerPaint;
    private Paint bitPaint;

    /*圆角的宽与高*/
    private int cornerWidth = CORNERWIDTH;
    private int cornerHeight = CORNERHEIGHT;

    private int cornerRadius = -1;

    public ECornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ECornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ECornerImageView(Context context) {
        super(context);
        init(context, null);
    }

    /*设置圆角宽度*/
    public void setcornerWidth(int cornerWidth) {
        this.cornerWidth = DisplayUtils.dp2px(getContext(),cornerWidth);
    }

    /*设置圆角高度*/
    public void setcornerHeight(int cornerHeight) {
        this.cornerHeight = DisplayUtils.dp2px(getContext(),cornerHeight);
    }

    /*设置圆角半径*/
    public void setRadius(int radius){
        this.cornerHeight = DisplayUtils.dp2px(getContext(),radius);
        this.cornerWidth = DisplayUtils.dp2px(getContext(),radius);
    }

    /*设置需要圆角的图片角落*/
    public void setCorner(Boolean leftTop,Boolean lefBottom,Boolean rightTop,Boolean rightBottom){
        this.leftTop = leftTop;
        this.lefBottom = lefBottom;
        this.rightTop = rightTop;
        this.rightBottom = rightBottom;
    }

    private void init(Context context, AttributeSet attrs) {

        /*获取自定义属性参数值*/
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ECornerImageView);
            cornerWidth = a.getDimensionPixelSize(R.styleable.ECornerImageView_cornerWidth, cornerWidth);
            cornerHeight = a.getDimensionPixelSize(R.styleable.ECornerImageView_cornerHeight, cornerHeight);
            Log.d(TAG,"cornerWidth:"+cornerWidth);
            Log.d(TAG,"cornerHeight:"+cornerHeight);
            cornerRadius = a.getDimensionPixelSize(R.styleable.ECornerImageView_cornerRadius, cornerRadius);
            Log.d(TAG,"cornerRadius:"+cornerRadius);
            if (cornerRadius!=-1){
                cornerHeight = cornerRadius;
                cornerWidth = cornerRadius;
            }
            Log.d(TAG,"cornerWidth2:"+cornerWidth);
            Log.d(TAG,"cornerHeight2:"+cornerHeight);
            //回收
            a.recycle();
        } else {
            cornerWidth = CORNERWIDTH;
            cornerHeight = CORNERHEIGHT;
        }

        path = new Path();

        cornerPaint = new Paint();
        cornerPaint.setColor(Color.WHITE);
        cornerPaint.setAntiAlias(true);
        cornerPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        bitPaint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        /*绘制图片
          界面显示图片时，需要的内存空间是按像素点的多少乘以每个像素点占用的空间大小来计算的。
        * Bitmap.Config ARGB_8888：每个像素占四位，即A=8，R=8，G=8，B=8，那么一个像素点占8+8+8+8=32位*/
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cornerCanvas = new Canvas(bitmap);
        super.draw(cornerCanvas);

        /*绘制圆角遮罩*/
        if (leftTop){
            drawLeftTop(cornerCanvas);
        }
        if (lefBottom){
            drawLeftBottom(cornerCanvas);
        }
        if (rightTop){
            drawRightTop(cornerCanvas);
        }
        if (rightBottom){
            drawRightBottom(cornerCanvas);
        }

        canvas.drawBitmap(bitmap, 0, 0, bitPaint);
        //bitPaint.setXfermode(null);

        /*在Android应用里，最耗费内存的就是图片资源。而且在Android系统中，读取位图Bitmap时，
          分给虚拟机中的图片的堆栈大小只有8M，如果超出了，就会出现OutOfMemory异常。*/
        if(bitmap != null && !bitmap.isRecycled()){

            // 回收bitmap并且置为null
            bitmap.recycle();
            bitmap = null;

        }
        /*通知垃圾回收器尽快进行回收*/
        System.gc();
    }

    /*绘制左上角圆角遮罩*/
    private void drawLeftTop(Canvas canvas) {
        /*绘制横竖两条直线*/
        path.moveTo(0, cornerHeight);
        path.lineTo(0, 0);
        path.lineTo(cornerWidth, 0);
        /*绘制椭圆的弧线*/
        path.arcTo(new RectF(0, 0, cornerWidth * 2, cornerHeight * 2), -90, -90);
        path.close();
        canvas.drawPath(path, cornerPaint);
    }

    /*绘制左下角圆角遮罩*/
    private void drawLeftBottom(Canvas canvas) {
        /*绘制横竖两条直线*/
        path.moveTo(0, getHeight() - cornerHeight);
        path.lineTo(0, getHeight());
        path.lineTo(cornerWidth, getHeight());
        /*绘制椭圆的弧线*/
        path.arcTo(new RectF(0, getHeight() - cornerHeight * 2, cornerWidth * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, cornerPaint);
    }

    /*绘制右上角圆角遮罩*/
    private void drawRightBottom(Canvas canvas) {
        /*绘制横竖两条直线*/
        path.moveTo(getWidth() - cornerWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - cornerHeight);
        /*绘制椭圆的弧线*/
        path.arcTo(new RectF(getWidth() - cornerWidth * 2, getHeight() - cornerHeight * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, cornerPaint);
    }

    /*绘制右下角圆角遮罩*/
    private void drawRightTop(Canvas canvas) {
        /*绘制横竖两条直线*/
        path.moveTo(getWidth(), cornerHeight);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - cornerWidth, 0);
        /*绘制椭圆的弧线*/
        path.arcTo(new RectF(getWidth() - cornerWidth * 2, 0, getWidth(), 0 + cornerHeight * 2), -90, 90);
        path.close();
        canvas.drawPath(path, cornerPaint);
    }

}
