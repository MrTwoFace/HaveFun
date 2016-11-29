package com.example.j_chenxi.fragmentdemo.application;

import android.app.Application;

/**
 * Created by j-chenxi on 2016/10/16.
 */

public class BaseApplication extends Application {
    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;



    }

}
