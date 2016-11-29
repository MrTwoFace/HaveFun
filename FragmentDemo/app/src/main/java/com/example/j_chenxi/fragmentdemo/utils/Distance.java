package com.example.j_chenxi.fragmentdemo.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by j-chenxi on 2016/11/28.
 */

public class Distance {
    public static int dp2px(Context context, float dpValue) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics()) + 0.5);
    }
}
