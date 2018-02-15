package com.aswin.CustomArc;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ASWIN on 2/7/2018.
 */

public class ArcView extends View {
    private Paint mBackPaint,mActivePaint,mTextPaint;
    private int finalViewHeight,finalViewWidth;
    private RectF bounds;
    private float strokeWidth,mTextSize,mDialSize;
    private boolean animatable,progressInPercentage,isLastSection=false;
    private float section;
    private float progress;
    private String progressString="";
    private int dialActiveColor,dialInactiveColor,textColor,mTextWidth;
    private Rect r = new Rect(),textBounds=new Rect();
    private float nextSection;


    public void setProgress(int progress) {
        this.progress = 300*progress/100;
        this.progressString=String.valueOf(progress);
    }

    public void setAnimatable(boolean animatable) {
        this.animatable = animatable;
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        strokeWidth=convertDpToPixel(15);
        dialActiveColor=Color.WHITE;
        dialInactiveColor=Color.parseColor("#50ffffff");
        textColor=Color.WHITE;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ArcView,
                0, 0);
        try {
            mTextSize=a.getDimensionPixelSize(R.styleable.ArcView_textSize,0);
            mDialSize=a.getDimensionPixelSize(R.styleable.ArcView_arcWidth,10);
            progressString=a.getString(R.styleable.ArcView_text);
            dialInactiveColor=a.getColor(R.styleable.ArcView_arcInactiveColor,Color.parseColor("#50ffffff"));
            dialActiveColor=a.getColor(R.styleable.ArcView_arcActiveColor,Color.WHITE);
            textColor=a.getColor(R.styleable.ArcView_textColor,Color.WHITE);
            animatable=a.getBoolean(R.styleable.ArcView_animatable,true);
            progressInPercentage=a.getBoolean(R.styleable.ArcView_progressInPercentage,false);
            try {
                progress = Integer.parseInt(progressString);
                progress=300*progress/100;
            }catch (NumberFormatException e){

            }
            if(progressInPercentage){
                this.progressString=progressString+"%";
            }
        } finally {
            a.recycle();
        }


        mBackPaint=new Paint();
        mBackPaint.setStrokeWidth(mDialSize);
        mBackPaint.setColor(dialInactiveColor);
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.STROKE);

        mActivePaint=new Paint();
        mActivePaint.setStrokeWidth(mDialSize);
        mActivePaint.setColor(dialActiveColor);
        mActivePaint.setAntiAlias(true);
        mActivePaint.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);



        bounds=new RectF();
        section=progress/30;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        finalViewHeight= getMeasuredHeight();
        finalViewWidth= getMeasuredWidth();

        bounds.set(strokeWidth,strokeWidth,finalViewWidth-strokeWidth,finalViewHeight-strokeWidth);


        mTextPaint.getTextBounds(progressString,0,progressString.length(),textBounds);

        mTextWidth = textBounds.width();

    }

    @Override
    protected void onDraw(final Canvas canvas) {

        canvas.drawArc(bounds,120,300,false,mBackPaint);

        drawCenter(canvas,mTextPaint,progressString);

        if (animatable) {
            if(progress==300){
                if(!isLastSection) {
                    canvas.drawArc(bounds, 120, section, false, mActivePaint);
                }else {
                    canvas.drawArc(bounds, 120, 300, false, mActivePaint);
                }
                if(!isLastSection) {
                    section = section + (progress / 30);
                    if (section < progress) {
                        invalidate();
                        nextSection = section + (progress / 30);
                        if (nextSection >= progress) {
                            isLastSection = true;
                        }
                    }
                }
            }else {
                if(!isLastSection) {
                    canvas.drawArc(bounds, 120, section, false, mActivePaint);
                }else {
                    canvas.drawArc(bounds, 120, progress, false, mActivePaint);
                }
                if (!isLastSection) {
                    section = section + (progress / 30);
                    if (section < progress) {
                        invalidate();
                        nextSection = section + (progress / 30);
                        if (nextSection >= progress) {
                            isLastSection = true;
                        }
                    }
                }
            }

        } else {
            canvas.drawArc(bounds, 120, progress, false, mActivePaint);
        }


    }

    public static float convertDpToPixel(float dp){

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }


    private void drawCenter(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}
