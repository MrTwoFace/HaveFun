package com.example.j_chenxi.fragmentdemo.customview;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.j_chenxi.fragmentdemo.R;

import static android.media.AudioManager.STREAM_VOICE_CALL;

/**
 * Created by j-chenxi on 2016/10/16.
 */

public class VolumeController extends RelativeLayout{
    private View mRootView;

    private SeekBar voice_value_seekbar;
    private SeekBar phone_value_seekbar;
    private SeekBar music_value_seekbar;
    private ImageView mClose;
    AudioManager mAudioManager;
    private OnVolumeControllerListener mOnVolumeControllerListener = null;

    public VolumeController(Context context,OnVolumeControllerListener onVolumeControllerListener) {
        super(context);
        this.mOnVolumeControllerListener = onVolumeControllerListener;
        initView();
    }

    public VolumeController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VolumeController(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mRootView = inflater.inflate(R.layout.float_controller, this, true);

        mClose = (ImageView) mRootView.findViewById(R.id.close_btn);
        phone_value_seekbar = (SeekBar) mRootView.findViewById(R.id.phone_value_seekbar);
        music_value_seekbar = (SeekBar) mRootView.findViewById(R.id.music_value_seekbar);
        voice_value_seekbar = (SeekBar) mRootView.findViewById(R.id.voice_value_seekbar);


        initValue();


    }

    private void initValue() {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);


        float phoneValueCurrent = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM)*1f/mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        float musicValueCurrent = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC)*1f/mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float voiceValueCurrent = mAudioManager.getStreamVolume( STREAM_VOICE_CALL)*1f/mAudioManager.getStreamMaxVolume(STREAM_VOICE_CALL);

        int phoneValuePresent = (int)(Float.valueOf(String.format("%.2f",phoneValueCurrent))*100);
        int musicValuePresent = (int)(Float.valueOf(String.format("%.2f",musicValueCurrent))*100);
        int voiceValuePresent = (int)(Float.valueOf(String.format("%.2f",voiceValueCurrent))*100);


        phone_value_seekbar.setProgress(phoneValuePresent);
        music_value_seekbar.setProgress(musicValuePresent);
        voice_value_seekbar.setProgress(voiceValuePresent);

        phone_value_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    float present = progress*1f/100;
                    int current = (int) Math.ceil(present*mAudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,current,AudioManager.FLAG_PLAY_SOUND);
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
                if(fromUser){
                    float present = progress*1f/100;
                    int current = (int) Math.ceil(present*mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,current,AudioManager.FLAG_PLAY_SOUND);
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
                if(fromUser){
                    float present = progress*1f/100;
                    int current = (int) Math.ceil(present*mAudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
                    mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,current,AudioManager.FLAG_PLAY_SOUND);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnVolumeControllerListener != null){
                    mOnVolumeControllerListener.onCloseClick();
                }
            }
        });
    }

    public interface OnVolumeControllerListener{
        void onCloseClick();
    }


}
