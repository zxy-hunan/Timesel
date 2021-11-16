package com.hunan_zxy.timeselector;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hunan_zxy.timelib.TimeSelector;
import com.hunan_zxy.timelib.listener.TimeSelectCallBack;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    /**
     * 时间选择器
     */
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void showDialog() {
        TimeSelector.getInstance()
                .setActivity(this)
                .setTitle("请选择年月日") //弹窗标题
                .setCancelTitle("取消") //取消按钮的文字
                .setConfirmTitle("确定") //确定按钮的文字
                .setCancelTextColor(Color.RED) //取消按钮的颜色
                .setConfirmTextColor(Color.BLUE) //确定按钮的颜色
                .setCycle(false) //是否支持数据循环
                .setBlur(false) //是否支持背景高斯模糊
                .setFiveSecondInterval(false) //秒的数据是否为5的倍数
                .setType(new boolean[]{true, false, false})
                .setTimeSelectCallBack(new TimeSelectCallBack() {
                    @Override
                    public void onChoose(String formatTime, long originTime) {
                        Log.d(TAG, formatTime);
                        Log.d(TAG, originTime + "");
                        Toast.makeText(MainActivity.this, formatTime, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onConfirm(HashMap<Integer, String> map) {
                        Toast.makeText(MainActivity.this, map.get(0)+"    "+map.get(1), Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                showDialog();
                break;
        }
    }
}
