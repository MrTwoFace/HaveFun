package com.example.j_chenxi.fragmentdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class PagerFragmentForth extends BaseFragment{


    private LayoutInflater layoutInflater;
    private View mRootView;
    private ChildBaseFragment mCurrentFragment;
    private ChildFragmentFirst mChildFragmentFirst;
    private ChildFragmentSecond mChildFragmentSecond;
    public static PagerFragmentForth newInstance(String stringInfo) {

        PagerFragmentForth f = new PagerFragmentForth();
        Bundle args = new Bundle();
        args.putString(ViewpagerAdapter.newInstance_String_tag, stringInfo);
        f.setArguments(args);

        return f;
    }
    private void hideCurrentFragment(FragmentTransaction trans, ChildBaseFragment fragment) {
        if (mCurrentFragment != null && mCurrentFragment != fragment) {
            trans.hide(mCurrentFragment);
        }
    }

    private void setCurrentFragment(ChildBaseFragment fragment) {
        mCurrentFragment = fragment;
    }



    private void getNewFragment(){
     if(mCurrentFragment == null || mCurrentFragment instanceof ChildFragmentSecond ){
         changeFirstFragment();
     }else{
         changeSecondFragment();
     }
    }

    private void changeFirstFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mChildFragmentFirst = (ChildFragmentFirst) fragmentManager.findFragmentByTag(ChildFragmentFirst.FRAGMENT_TAG);

//        if(mChildFragmentSecond != null && mChildFragmentSecond.mSearchResultView != null){
//            mChildFragmentSecond.mSearchResultView.onDestoryView();
//        }

        hideCurrentFragment(transaction, mChildFragmentFirst);
        if (mChildFragmentFirst == null) {
            mChildFragmentFirst = ChildFragmentFirst.newInstance("childFirst",this);
            transaction.add(R.id.fragment_layout, mChildFragmentFirst, ChildFragmentFirst.FRAGMENT_TAG);
        } else {
            transaction.show(mChildFragmentFirst);
        }

        setCurrentFragment(mChildFragmentFirst);
        transaction.commitAllowingStateLoss();
    }

    private void changeSecondFragment() {

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mChildFragmentSecond = (ChildFragmentSecond) fragmentManager.findFragmentByTag(ChildFragmentSecond.FRAGMENT_TAG);
        hideCurrentFragment(transaction, mChildFragmentSecond);

        if (mChildFragmentSecond == null) {
            mChildFragmentSecond = ChildFragmentSecond.newInstance("childSecond");
            transaction.add(R.id.fragment_layout, mChildFragmentSecond, ChildFragmentSecond.FRAGMENT_TAG);
        } else if (mChildFragmentSecond.isVisible()) {
            return;
        } else {
            transaction.show(mChildFragmentSecond);
        }

        setCurrentFragment(mChildFragmentSecond);
        transaction.commitAllowingStateLoss();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chenxi", "PagerFragmentForth onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("chenxi", "PagerFragmentForth onCreateView");
        layoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_4, container, false);
        }
        return mRootView;
    }
    private Button btn ;
    private int alpha = 1;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String text  = bundle.getString(ViewpagerAdapter.newInstance_String_tag);
        ((TextView)(view.findViewById(R.id.fragment_text))).setText(text);
        btn = ((Button)(view.findViewById(R.id.button_change)));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getNewFragment();
                btn.setAlpha(alpha--);
            }
        });
//        getNewFragment();
        Log.e("chenxi", "PagerFragmentForth onViewCreated");
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("chenxi", "PagerFragmentForth onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("chenxi", "PagerFragmentForth onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("chenxi", "PagerFragmentForth onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chenxi", "PagerFragmentForth onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("chenxi", "PagerFragmentForth onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("chenxi", "PagerFragmentForth onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("chenxi", "PagerFragmentForth onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("chenxi", "PagerFragmentForth onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("chenxi", "PagerFragmentForth onAttach");
    }
}
