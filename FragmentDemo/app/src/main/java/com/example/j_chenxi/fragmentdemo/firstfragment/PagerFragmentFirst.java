package com.example.j_chenxi.fragmentdemo.firstfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.j_chenxi.fragmentdemo.BaseFragment;
import com.example.j_chenxi.fragmentdemo.R;
import com.example.j_chenxi.fragmentdemo.ViewpagerAdapter;

import static android.media.AudioManager.STREAM_VOICE_CALL;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class PagerFragmentFirst extends BaseFragment {

    private LayoutInflater layoutInflater;
    private View mRootView;
    protected Context mContext;
    public static final String FRAGMENT_TAG = "PagerFragmentFirst";

    public static PagerFragmentFirst newInstance(String stringInfo) {

        PagerFragmentFirst f = new PagerFragmentFirst();
        Bundle args = new Bundle();
        args.putString(ViewpagerAdapter.newInstance_String_tag, stringInfo);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chenxi", "PagerFragmentFirst onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("chenxi", "PagerFragmentFirst onCreateView");
        layoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_1, container, false);
        }
        return mRootView;
    }

    private TextView battery_txt;
    private SeekBar voice_value_seekbar;
    private SeekBar phone_value_seekbar;
    private SeekBar music_value_seekbar;
    AudioManager mAudioManager;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String text = bundle.getString(ViewpagerAdapter.newInstance_String_tag);
        if (!TextUtils.isEmpty(text)) {
            ((TextView) (view.findViewById(R.id.fragment_text))).setText(text);
        }

        phone_value_seekbar = (SeekBar) view.findViewById(R.id.phone_value_seekbar);
        music_value_seekbar = (SeekBar) view.findViewById(R.id.music_value_seekbar);
        voice_value_seekbar = (SeekBar) view.findViewById(R.id.voice_value_seekbar);
        battery_txt = (TextView) view.findViewById(R.id.battery_txt);

        initValue();
        initBattery();

        Log.e("chenxi", "PagerFragmentFirst onViewCreated");
    }

    private void initBattery() {
        //注册广播接受者java代码
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //创建广播接受者对象
        BatteryReceiver batteryReceiver = new BatteryReceiver();

         //注册receiver
        getActivity().registerReceiver(batteryReceiver, intentFilter);
    }

    private void initValue() {
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);


        float phoneValueCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) * 1f / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        float musicValueCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 1f / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float voiceValueCurrent = mAudioManager.getStreamVolume(STREAM_VOICE_CALL) * 1f / mAudioManager.getStreamMaxVolume(STREAM_VOICE_CALL);

        int phoneValuePresent = (int) (Float.valueOf(String.format("%.2f", phoneValueCurrent)) * 100);
        int musicValuePresent = (int) (Float.valueOf(String.format("%.2f", musicValueCurrent)) * 100);
        int voiceValuePresent = (int) (Float.valueOf(String.format("%.2f", voiceValueCurrent)) * 100);


        phone_value_seekbar.setProgress(phoneValuePresent);
        music_value_seekbar.setProgress(musicValuePresent);
        voice_value_seekbar.setProgress(voiceValuePresent);

        phone_value_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float present = progress * 1f / 100;
                    int current = (int) Math.ceil(present * mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, current, AudioManager.FLAG_PLAY_SOUND);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        music_value_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float present = progress * 1f / 100;
                    int current = (int) Math.ceil(present * mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, AudioManager.FLAG_PLAY_SOUND);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        voice_value_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    float present = progress * 1f / 100;
                    int current = (int) Math.ceil(present * mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, current, AudioManager.FLAG_PLAY_SOUND);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("chenxi", "PagerFragmentFirst onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("chenxi", "PagerFragmentFirst onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("chenxi", "PagerFragmentFirst onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chenxi", "PagerFragmentFirst onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("chenxi", "PagerFragmentFirst onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("chenxi", "PagerFragmentFirst onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("chenxi", "PagerFragmentFirst onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("chenxi", "PagerFragmentFirst onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("chenxi", "PagerFragmentFirst onAttach");
        this.mContext = context;
    }

    /**
     * 广播接受者
     */
    class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if(status == BatteryManager.BATTERY_STATUS_CHARGING){
                battery_txt.setTextColor(Color.parseColor("#33FF00"));
            }else{
                battery_txt.setTextColor(Color.parseColor("#000000"));
            }
            //判断它是否是为电量变化的Broadcast Action
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            //电量最大值
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                //把它转成百分比
                battery_txt.setText("电池电量为" + ((level * 100) / scale) + "%");
            }
        }

    }
}
