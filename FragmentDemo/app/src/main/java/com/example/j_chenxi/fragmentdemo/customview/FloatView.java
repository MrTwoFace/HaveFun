package com.example.j_chenxi.fragmentdemo.customview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.j_chenxi.fragmentdemo.MainActivity;
import com.example.j_chenxi.fragmentdemo.R;
import com.example.j_chenxi.fragmentdemo.firstfragment.PagerFragmentFirst;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by j-chenxi on 2016/9/30.
 */

public class FloatView extends RelativeLayout {

    private WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    private static WindowManager windowManager;
    private static ImageView imageView;
    private VolumeController mVolumeController = null;
    private boolean isShow = false;
    private float lastX; //上一次位置的X.Y坐标
    private float lastY;
    private float nowX;  //当前移动位置的X.Y坐标
    private float nowY;
    private float tranX; //悬浮窗移动位置的相对值
    private float tranY;


    private long startTime = 0;
    private long endTime = 0;

    private boolean isclick;


    public FloatView(Context context) {
        super(context);
        initImageView();
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show(){
        if(!isShow){
            windowManager.addView(imageView,lp);
            isShow = true;
        }
    }
    private void initImageView(){
        windowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);

        //注意，悬浮窗只有一个，而当打开应用的时候才会产生悬浮窗，所以要判断悬浮窗是否已经存在，
        if (imageView != null){
            return;
//            windowManager.removeView(imageView);
        }
        // 使用Application context 创建UI控件，避免Activity销毁导致上下文出现问题,因为现在的悬浮窗是系统级别的，不依赖与Activity存在
        imageView = new ImageView( getContext().getApplicationContext());
        imageView.setImageResource(R.drawable.icon_pin);
//        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        lp.type = WindowManager.LayoutParams.TYPE_TOAST;
        lp.gravity = Gravity.LEFT|Gravity.TOP;
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //显示位置与指定位置的相对位置差
        lp.x = 0;
        lp.y = 0;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.format = PixelFormat.TRANSPARENT;

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        // 获取按下时的X，Y坐标
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        isclick = false;//当按下的时候设置isclick为false，具体原因看后边的讲解

                        startTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 获取移动时的X，Y坐标
                        isclick = true;//当按钮被移动的时候设置isclick为true

                        nowX = event.getRawX();
                        nowY = event.getRawY();
                        // 计算XY坐标偏移量
                        tranX = nowX - lastX;
                        tranY = nowY - lastY;
                        // 移动悬浮窗
                        lp.x += tranX;
                        lp.y += tranY;
                        //更新悬浮窗位置
                        windowManager.updateViewLayout(imageView,lp);
                        //记录当前坐标作为下一次计算的上一次移动的位置坐标
                        lastX = nowX;
                        lastY = nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = System.currentTimeMillis();
                        //当从点击到弹起小于半秒的时候,则判断为点击,如果超过则不响应点击事件
                        if ((endTime - startTime) > 0.1 * 1000L) {
                            isclick = true;
                        } else {
                            isclick = false;
                        }
                        break;
                }
                return isclick;
            }
        });
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                WindowManager.LayoutParams lpFloatView = new WindowManager.LayoutParams();
                lpFloatView.type = WindowManager.LayoutParams.TYPE_TOAST;
                lpFloatView.gravity = Gravity.LEFT|Gravity.TOP;
                lpFloatView.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                //显示位置与指定位置的相对位置差
                lpFloatView.x = 0;
                lpFloatView.y = 0;
                //悬浮窗的宽高
                lpFloatView.width = WindowManager.LayoutParams.MATCH_PARENT;
                lpFloatView.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lpFloatView.format = PixelFormat.TRANSPARENT;

                if(mVolumeController == null){
                    mVolumeController = new VolumeController(getContext(), new VolumeController.OnVolumeControllerListener() {
                        @Override
                        public void onCloseClick() {
                            windowManager.removeView(mVolumeController);
                        }
                    });
                }

                windowManager.addView(mVolumeController,lpFloatView);
//                Intent intent = new Intent();
//                intent.setClass(getContext(), MainActivity.class);
//                getContext().startActivity(intent);
            }
        });
    }

}
