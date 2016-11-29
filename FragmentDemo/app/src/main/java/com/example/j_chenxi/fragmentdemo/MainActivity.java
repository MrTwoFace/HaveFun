package com.example.j_chenxi.fragmentdemo;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.j_chenxi.fragmentdemo.service.FloatService;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {

    private ViewPager mViewpager = null;
    private ViewpagerAdapter viewPagerAdapter;
    private List<String> stringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stringList.add("我是第一个Fragment");
        stringList.add("我是第二个Fragment");
        stringList.add("我是第三个Fragment");
        stringList.add("我是第四个Fragment");

        mViewpager = (ViewPager)findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(),stringList);
        mViewpager.setAdapter(viewPagerAdapter);
//        createFloatView();

        Intent intent=new Intent(MainActivity.this, FloatService.class);
        startService(intent);
    }
    private List<String> homeList; // 桌面应用程序包名列表
    private ActivityManager mActivityManager;

}
