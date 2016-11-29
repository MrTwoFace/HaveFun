package com.example.j_chenxi.fragmentdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.j_chenxi.fragmentdemo.customview.FloatView;

import java.util.ServiceConfigurationError;

/**
 * Created by j-chenxi on 2016/10/16.
 */

public class FloatService extends Service {

    private FloatView mFloatView = null;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatView = new FloatView(getApplicationContext());
        mFloatView.show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
