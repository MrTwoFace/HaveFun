package com.example.j_chenxi.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.j_chenxi.fragmentdemo.firstfragment.PagerFragmentFirst;
import com.example.j_chenxi.fragmentdemo.thirdfragment.PagerFragmentThird;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class ViewpagerAdapter extends FragmentStatePagerAdapter {

    public final static String newInstance_String_tag = "newInstance_String_tag";

    private List<String> mStringList = new ArrayList<>();
    public ViewpagerAdapter(FragmentManager fm, List<String> stringList) {
        super(fm);
        this.mStringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;
        switch (position){
            case 0:{
                fragment = PagerFragmentFirst.newInstance(mStringList.get(position));
                break;
            }
            case 1:{
                fragment = PagerFragmentSecond.newInstance(mStringList.get(position));
                break;
            }
            case 2:{
                fragment = PagerFragmentThird.newInstance(mStringList.get(position));
                break;
            }
            case 3:{
                fragment = PagerFragmentForth.newInstance(mStringList.get(position));
                break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mStringList == null ? 0:mStringList.size();
    }
}
