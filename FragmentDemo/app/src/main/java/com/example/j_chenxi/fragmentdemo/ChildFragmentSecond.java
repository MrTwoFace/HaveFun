package com.example.j_chenxi.fragmentdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class ChildFragmentSecond extends ChildBaseFragment {

    private LayoutInflater layoutInflater;
    private View mRootView;
    public static final String FRAGMENT_TAG = "ChildFragmentSecond";
    public static ChildFragmentSecond newInstance(String stringInfo) {

        ChildFragmentSecond f = new ChildFragmentSecond();
        Bundle args = new Bundle();
        args.putString(ViewpagerAdapter.newInstance_String_tag, stringInfo);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chenxi", "----ChildFragmentSecond onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("chenxi", "----ChildFragmentSecond onCreateView");
        layoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_child_2, container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String text  = bundle.getString(ViewpagerAdapter.newInstance_String_tag);
        ((TextView)(view.findViewById(R.id.fragment_text))).setText(text);
        Log.e("chenxi", "----ChildFragmentSecond onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("chenxi", "----ChildFragmentSecond onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("chenxi", "----ChildFragmentSecond onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("chenxi", "----ChildFragmentSecond onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chenxi", "----ChildFragmentSecond onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("chenxi", "----ChildFragmentSecond onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("chenxi", "----ChildFragmentSecond onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("chenxi", "----ChildFragmentSecond onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("chenxi", "----ChildFragmentSecond onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("chenxi", "----ChildFragmentSecond onAttach");
    }
}
