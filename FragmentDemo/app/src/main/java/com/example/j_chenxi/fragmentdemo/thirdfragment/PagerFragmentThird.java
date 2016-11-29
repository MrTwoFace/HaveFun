package com.example.j_chenxi.fragmentdemo.thirdfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.j_chenxi.fragmentdemo.BaseFragment;
import com.example.j_chenxi.fragmentdemo.R;
import com.example.j_chenxi.fragmentdemo.ViewpagerAdapter;
import com.example.j_chenxi.fragmentdemo.utils.Distance;
import com.example.j_chenxi.fragmentdemo.utils.JobWorker;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.j_chenxi.fragmentdemo.R.id.battery_txt;

/**
 * Created by j-chenxi on 2016/9/28.
 */
public class PagerFragmentThird extends BaseFragment {

    private LayoutInflater layoutInflater;
    private View mRootView;

    private TextView mFragment_text;
    private Button btn_start_fire = null;
    private Button btn_stop_fire = null;

    private TextView txt_battery_value = null;
    private TextView txt_battery_tem = null;
    private int rate = 0;

    private LinearLayout view_layout = null;

    public static PagerFragmentThird newInstance(String stringInfo) {

        PagerFragmentThird f = new PagerFragmentThird();
        Bundle args = new Bundle();
        args.putString(ViewpagerAdapter.newInstance_String_tag, stringInfo);
        f.setArguments(args);

        return f;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("chenxi", "PagerFragmentThird onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("chenxi", "PagerFragmentThird onCreateView");
        layoutInflater = inflater;
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_3, container, false);
        }


        return mRootView;
    }

    private List<ENDownloadView> ENDownloadViews = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String text = bundle.getString(ViewpagerAdapter.newInstance_String_tag);
        mFragment_text = ((TextView) (view.findViewById(R.id.fragment_text)));

        btn_start_fire = (Button) view.findViewById(R.id.btn_start_fire);
        btn_stop_fire = (Button) view.findViewById(R.id.btn_stop_fire);
        txt_battery_value = (TextView) view.findViewById(R.id.txt_battery_value);
        txt_battery_tem = (TextView) view.findViewById(R.id.txt_battery_tem);

        view_layout = (LinearLayout) view.findViewById(R.id.view_layout);

        btn_start_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shootFire();
            }
        });
        btn_stop_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initBattery();
        initInfo();


    }

    private void initInfo() {

        JobWorker.getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final String info = getProcessCpuRate();
                    PagerFragmentThird.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFragment_text.setText(info);
                        }
                    });
                }
            }
        });
    }

    private String getProcessCpuRate() {
        StringBuilder tv = new StringBuilder();

        try {
            String Result;
            Process p;
            p = Runtime.getRuntime().exec("top -n 1");

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((Result = br.readLine()) != null) {
                if (Result.trim().length() < 1) {
                    continue;
                } else {
                    String[] CPUusr = Result.split("%");
                    tv.append("USER:" + CPUusr[0] + "\n");
                    String[] CPUusage = CPUusr[0].split("User");
                    String[] SYSusage = CPUusr[1].split("System");
                    tv.append("CPU:" + CPUusage[1].trim() + " length:" + CPUusage[1].trim().length() + "\n");
                    tv.append("SYS:" + SYSusage[1].trim() + " length:" + SYSusage[1].trim().length() + "\n");
                    tv.append("RATE:" + rate + "\n");
                    rate = Integer.parseInt(CPUusage[1].trim()) + Integer.parseInt(SYSusage[1].trim());
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tv.toString();
    }

    private void shootFire() {

        JobWorker.getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (rate < 95) {

//                        ImageView item1 = new ImageView(getActivity());
//
//                        item1.setImageResource(R.drawable.icon);//设置图片
//
//                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//
//                                LinearLayout. LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//
//                        lp.leftMargin=30;
//
//                        lp.topMargin=30;
//
//                        item1.setLayoutParams(lp);//设置布局参数
//
//                        view_layout.addView(item1);//RelativeLayout添加子View
                        final ENDownloadView view = new ENDownloadView(getActivity());
                        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Distance.dp2px(getActivity(), 80), Distance.dp2px(getActivity(), 80));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view_layout.addView(view, lp);
                                view.start();
                            }
                        });

                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void useCanvas() {
    }

    private void initBattery() {
        //注册广播接受者java代码
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //创建广播接受者对象
        PagerFragmentThird.BatteryReceiver batteryReceiver = new PagerFragmentThird.BatteryReceiver();

        //注册receiver
        getActivity().registerReceiver(batteryReceiver, intentFilter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("chenxi", "PagerFragmentThird onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("chenxi", "PagerFragmentThird onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("chenxi", "PagerFragmentThird onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("chenxi", "PagerFragmentThird onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("chenxi", "PagerFragmentThird onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("chenxi", "PagerFragmentThird onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("chenxi", "PagerFragmentThird onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("chenxi", "PagerFragmentThird onDetach");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("chenxi", "PagerFragmentThird onAttach");
    }

    /**
     * 广播接受者
     */
    class BatteryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //判断它是否是为电量变化的Broadcast Action
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                //电量最大值
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int tem = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
                //把它转成百分比
                txt_battery_value.setText("电池电量为" + ((level * 100) / scale) + "%");
                txt_battery_tem.setText("温度是：" + tem);
            }
        }

    }
}
