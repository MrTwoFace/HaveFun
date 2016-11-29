package com.example.j_chenxi.fragmentdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class ChildFragmentFirst extends ChildBaseFragment {

    private LayoutInflater layoutInflater;
    private View mRootView;
    private PagerFragmentForth mPagerFragmentForth;
    public static final String FRAGMENT_TAG = "ChildFragmentFirst";
    public static final String ParentFragment_TAG = "ParentFragment_TAG";
    public static ChildFragmentFirst newInstance(String stringInfo,PagerFragmentForth pagerFragmentForth) {

        ChildFragmentFirst f = new ChildFragmentFirst();
        Bundle args = new Bundle();
        args.putString(ViewpagerAdapter.newInstance_String_tag, stringInfo);
        args.putSerializable(ChildFragmentFirst.ParentFragment_TAG, pagerFragmentForth);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chenxi", "----ChildFragmentFirst onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("chenxi", "----ChildFragmentFirst onCreateView");
        layoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_child_1, container, false);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mPagerFragmentForth =(PagerFragmentForth) bundle.getSerializable(ChildFragmentFirst.ParentFragment_TAG);
        String text  = bundle.getString(ViewpagerAdapter.newInstance_String_tag);
        ((TextView)(view.findViewById(R.id.fragment_text))).setText(text);
        Log.e("chenxi", "----ChildFragmentFirst onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("chenxi", "----ChildFragmentFirst onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("chenxi", "----ChildFragmentFirst onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("chenxi", "----ChildFragmentFirst onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chenxi", "----ChildFragmentFirst onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("chenxi", "----ChildFragmentFirst onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("chenxi", "----ChildFragmentFirst onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("chenxi", "----ChildFragmentFirst onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("chenxi", "----ChildFragmentFirst onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("chenxi", "----ChildFragmentFirst onAttach");
    }
}
