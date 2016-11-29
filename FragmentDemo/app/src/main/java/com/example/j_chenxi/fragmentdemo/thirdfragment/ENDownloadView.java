package com.example.j_chenxi.fragmentdemo.thirdfragment;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.j_chenxi.fragmentdemo.R;

public class ENDownloadView extends View {


    private static final int STATE_DOWNLOADING = 1;

    private static final int DEFAULT_LINE_COLOR = Color.WHITE;

    private static final int DEFAULT_BG_LINE_COLOR = 0xff3a3f45;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private static final int DEFAULT_LINE_WIDTH = 9;

    private static final int DEFAULT_BG_LINE_WIDTH = 9;

    private static final int DEFAULT_TEXT_SIZE = 14;

    private static final int DEFAULT_STATE = STATE_DOWNLOADING;

    private static final int DEFAULT_RIPPLE_SPEED = 2;

    private static final int DEFAULT_DOWNLOAD_TIME = 2000;


    private int mCurrentState;

    private float mCurrentRippleX;

    private double mTotalSize;

    private int mTextSize;

    private int mDownloadTime;

    private Paint mPaint, mBgPaint, mTextPaint;

    private Path mPath;

    private RectF mRectF ,mClipRectF;

    private float mFraction;

    private float mWidth, mHeight;

    private float mCenterX, mCenterY;

    private float mBaseLength, mCircleRadius, mBaseRippleLength;


    public ENDownloadView(Context context) {
        super(context);
        init(null);
    }

    public ENDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.download);
        init(ta);
    }


    public void init(TypedArray ta){
        int lineColor = DEFAULT_LINE_COLOR;
        int bgLineColor = DEFAULT_BG_LINE_COLOR;
        int textColor = DEFAULT_TEXT_COLOR;
        int lineWidth = DEFAULT_LINE_WIDTH;
        int bgLineWidth = DEFAULT_BG_LINE_WIDTH;
        int textSize = DEFAULT_TEXT_SIZE;
        if(ta != null){
            lineColor = ta.getColor(R.styleable.download_download_line_color, DEFAULT_LINE_COLOR);
            bgLineColor = ta.getColor(R.styleable.download_download_bg_line_color, DEFAULT_BG_LINE_COLOR);
            textColor = ta.getColor(R.styleable.download_download_text_color, DEFAULT_TEXT_COLOR);
            lineWidth = ta.getInteger(R.styleable.download_download_line_width, DEFAULT_LINE_WIDTH);
            bgLineWidth = ta.getInteger(R.styleable.download_download_bg_line_width, DEFAULT_BG_LINE_WIDTH);
            textSize = ta.getInteger(R.styleable.download_download_text_size, DEFAULT_TEXT_SIZE);
            ta.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setColor(lineColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);


        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeWidth(bgLineWidth);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setDither(true);
        mBgPaint.setColor(bgLineColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPath = new Path();

        mTextSize = textSize;
        mCurrentState = DEFAULT_STATE;
        mDownloadTime = DEFAULT_DOWNLOAD_TIME;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
        mCircleRadius = mWidth * 5 / 12;
        mBaseLength = mCircleRadius / 3;
        mBaseRippleLength = 4.4f * mBaseLength / 12;
        mCurrentRippleX = mCenterX - mBaseRippleLength * 10;
        mRectF = new RectF(mCenterX - mCircleRadius, mCenterY - mCircleRadius, mCenterX + mCircleRadius, mCenterY + mCircleRadius);
        mClipRectF = new RectF(mCenterX - 6 * mBaseRippleLength, 0 , mCenterX + 6 * mBaseRippleLength , mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentState) {
            case STATE_DOWNLOADING:
                if (mFraction <= 0.2) {
                    mTextPaint.setTextSize(mTextSize / 0.2f * mFraction);
                }
                canvas.drawCircle(mCenterX, mCenterY ,mCircleRadius, mBgPaint);
                canvas.drawArc(mRectF, -90, 359.99f * mFraction, false ,mPaint);
                mPath.reset();
                mCurrentRippleX += DEFAULT_RIPPLE_SPEED;
                if (mCurrentRippleX > mCenterX - mBaseRippleLength * 6)
                    mCurrentRippleX = mCenterX - mBaseRippleLength * 10;
                mPath.moveTo(mCurrentRippleX , mCenterY);
                for (int i = 0; i< 4 ; i++) {
                    mPath.rQuadTo(mBaseRippleLength, -(1 - mFraction) * mBaseRippleLength, mBaseRippleLength * 2, 0);
                    mPath.rQuadTo(mBaseRippleLength, (1 - mFraction) * mBaseRippleLength, mBaseRippleLength * 2, 0);
                }
                canvas.save();
                canvas.clipRect(mClipRectF);
                canvas.drawPath(mPath, mPaint);
                canvas.restore();

                canvas.drawText(String.format("%.2f", mFraction) , mCenterX , mCenterY + 1.4f * mBaseLength , mTextPaint);
                break;
        }
    }

    public void start() {
        downloadAnim();
    }

    private void downloadAnim() {
        ValueAnimator downloadAnim = ValueAnimator.ofFloat(1.f, 100.f);
        downloadAnim.setDuration(600);
        downloadAnim.setRepeatCount(ValueAnimator.INFINITE);
        downloadAnim.setRepeatMode(ValueAnimator.RESTART);
        downloadAnim.setInterpolator(new LinearInterpolator());
        downloadAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFraction = valueAnimator.getAnimatedFraction();
                invalidate();
            }
        });
        downloadAnim.start();
    }

}
