package com.hunan_zxy.timelib;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.hunan_zxy.timelib.adapter.AbstractWheelTextAdapter;
import com.hunan_zxy.timelib.adapter.DayTextAdapter;
import com.hunan_zxy.timelib.adapter.HourTextAdapter;
import com.hunan_zxy.timelib.adapter.MinuteTextAdapter;
import com.hunan_zxy.timelib.adapter.MonthDayTextAdapter;
import com.hunan_zxy.timelib.adapter.MonthTextAdapter;
import com.hunan_zxy.timelib.adapter.YearTextAdapter;
import com.hunan_zxy.timelib.entity.DayEntity;
import com.hunan_zxy.timelib.entity.MonthEntity;
import com.hunan_zxy.timelib.entity.YearBean;
import com.hunan_zxy.timelib.listener.OnWheelChangedListener;
import com.hunan_zxy.timelib.listener.OnWheelScrollListener;
import com.hunan_zxy.timelib.listener.TimeSelectCallBack;
import com.hunan_zxy.timelib.utils.BhUtils;
import com.hunan_zxy.timelib.widget.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * FileName: TimeSelectDialog
 * Author: jibinghao
 * Date: 2019/5/13 10:21 AM
 * Email:heybinghao@gmail.com
 * Description:
 */

public class TimeSelectDialog extends Dialog implements View.OnClickListener {


    private Activity context;
    private ImageView mIvBg;
    private TextView mTvCancel;
    private TextView mTvTitle;
    private TextView mTvConfirm;
    private WheelView mWvYear;
    private WheelView mWvMonth;
    private WheelView mWvonthDay;
    private WheelView mWvDay;
    private WheelView mWvHour;
    private WheelView mWvMinute;

    private int mYearPosition;
    private int mMonthPosition;
    private int mMonthDayPosition;
    private int mDayPosition;
    private int mHourPosition;
    private int mMinutePosition;
    private int mMinuteDivisor;//??????


    TimeSelectCallBack timeSelectCallBack;
    String title;

    private int maxTextSize = 16;
    private int minTextSize = 14;
    private ArrayList<YearBean> yearList = new ArrayList<>();
    private ArrayList<MonthEntity> monthList = new ArrayList<>();
    private ArrayList<DayEntity> monthDayList = new ArrayList<>();
    private ArrayList<String> dayList = new ArrayList<>();
    private ArrayList<String> hourList = new ArrayList<>();
    private ArrayList<String> minuteList = new ArrayList<>();

    private YearTextAdapter yearTextAdapter;
    private MonthTextAdapter monthTextAdapter;
    private MonthDayTextAdapter monthDayTextAdapter;
    private DayTextAdapter dayTextAdapter;
    private HourTextAdapter hourTextAdapter;
    private MinuteTextAdapter minuteTextAdapter;
    boolean isBlur = true;//?????????????????? ???????????????
    String cancelTitle;//???????????????
    String confirmTitle;//???????????????
    boolean isCycle;//??????????????????
    boolean isFiveSecondInterval;//????????????5?????????
    int cancelTextColor;
    int confirmTextColor;
    String yearStr="";

    boolean[] type;//????????????????????????,???????????????????????????????????????


    public TimeSelectDialog(Activity activity, String title, TimeSelectCallBack timeSelectCallBack) {
        super(activity, R.style.WheelViewDialog);
        this.context = activity;
        this.timeSelectCallBack = timeSelectCallBack;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wheel_view_time_select);
        initData();
        initView();
        initAdapter();

    }

    private void initAdapter() {
        yearTextAdapter = new YearTextAdapter(context, yearList, mYearPosition, maxTextSize, minTextSize);
        mWvYear.setVisibleItems(3);
        mWvYear.setViewAdapter(yearTextAdapter);
        mWvYear.setCurrentItem(mYearPosition);
        mWvYear.setCyclic(isCycle);

        monthTextAdapter = new MonthTextAdapter(context, monthList, mMonthPosition, maxTextSize, minTextSize);
        mWvMonth.setVisibleItems(3);
        mWvMonth.setViewAdapter(monthTextAdapter);
        mWvMonth.setCurrentItem(mMonthPosition);
        mWvMonth.setCyclic(isCycle);

        monthDayTextAdapter = new MonthDayTextAdapter(context, monthDayList, mMonthDayPosition, maxTextSize, minTextSize);
        mWvonthDay.setVisibleItems(3);
        mWvonthDay.setViewAdapter(monthDayTextAdapter);
        mWvonthDay.setCurrentItem(mMonthDayPosition);
        mWvonthDay.setCyclic(isCycle);

        dayTextAdapter = new DayTextAdapter(context, dayList, mDayPosition, maxTextSize, minTextSize);
        mWvDay.setVisibleItems(2);
        mWvDay.setViewAdapter(dayTextAdapter);
        mWvDay.setCurrentItem(mDayPosition);


        hourTextAdapter = new HourTextAdapter(context, hourList, mHourPosition, maxTextSize, minTextSize);
        mWvHour.setVisibleItems(3);
        mWvHour.setViewAdapter(hourTextAdapter);
        mWvHour.setCurrentItem(mHourPosition);
        mWvHour.setCyclic(isCycle);

        minuteTextAdapter = new MinuteTextAdapter(context, minuteList, mMinutePosition, maxTextSize, minTextSize);
        mWvMinute.setVisibleItems(3);
        mWvMinute.setViewAdapter(minuteTextAdapter);
        mWvMinute.setCurrentItem(mMinutePosition);
        mWvMinute.setCyclic(isCycle);


        //???????????????
        mWvYear.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) yearTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, yearTextAdapter);
                yearStr=currentText.replace("???","");
            }
        });
        mWvYear.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) yearTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, yearTextAdapter);
                yearStr=currentText;
                yearStr=currentText.replace("???","");
            }
        });

        mWvMonth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) monthTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, monthTextAdapter);
            }
        });

        mWvMonth.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) monthTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, monthTextAdapter);
                int position = getNewMonthDay(currentText.replace("???",""));
                monthDayTextAdapter = new MonthDayTextAdapter(context, monthDayList, position, maxTextSize, minTextSize);
                mWvonthDay.setVisibleItems(3);
                mWvonthDay.setCurrentItem(position);
                mWvonthDay.setViewAdapter(monthDayTextAdapter);
            }
        });


        mWvonthDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) monthDayTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, monthDayTextAdapter);
            }
        });

        mWvonthDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) monthDayTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, monthDayTextAdapter);
            }
        });


        mWvDay.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) dayTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, dayTextAdapter);
            }
        });

        mWvDay.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) dayTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, dayTextAdapter);
            }
        });
        mWvHour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) hourTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, hourTextAdapter);
            }
        });

        mWvHour.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) hourTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, hourTextAdapter);
            }
        });
        mWvMinute.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) minuteTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, minuteTextAdapter);
            }
        });

        mWvMinute.addScrollingListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) minuteTextAdapter.getItemText(wheel.getCurrentItem());
                setTextViewSize(currentText, minuteTextAdapter);
            }
        });


    }

    private int getNewMonthDay(String month) {
        if(yearStr.isEmpty()) return 0;
        int chooseYear = Integer.parseInt(yearStr);
        monthDayList = BhUtils.getMonthDay(chooseYear,Integer.parseInt(month),0);
        int monthPosition;
        monthPosition = mWvonthDay.getCurrentItem();
        while(monthPosition >= monthDayList.size()){
            //?????????1
            monthPosition--;
        }
        if (monthPosition < 0) {
            monthPosition = 0;
        }
        return monthPosition;
    }

    /**
     * ??????????????????
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextViewSize(String curriteItemText, AbstractWheelTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textView = (TextView) arrayList.get(i);
            currentText = textView.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textView.setTextSize(maxTextSize);
                textView.setTextColor(context.getResources().getColor(R.color.black_353535));
            } else {
                textView.setTextSize(minTextSize);
                textView.setTextColor(context.getResources().getColor(R.color.gray_939393));
            }
        }
    }

    private void initView() {
        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mTvCancel = (TextView) findViewById(R.id.tv_cancel);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mWvYear = (WheelView) findViewById(R.id.wv_year);
        mWvMonth = (WheelView) findViewById(R.id.wv_month);
        mWvonthDay= (WheelView) findViewById(R.id.wv_monthDay);
        mWvDay = (WheelView) findViewById(R.id.wv_day);
        mWvHour = (WheelView) findViewById(R.id.wv_hour);
        mWvMinute = (WheelView) findViewById(R.id.wv_minute);
        mTvConfirm.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        if (isBlur) {
            mIvBg.setVisibility(View.VISIBLE);

            mIvBg.setImageBitmap(ImageUtils.fastBlur(ScreenUtils.screenShot(context, true), 0.1f, 5));
        } else {
            mIvBg.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(cancelTitle)) {
            mTvCancel.setText(cancelTitle);
        }

        if (!TextUtils.isEmpty(confirmTitle)) {
            mTvConfirm.setText(confirmTitle);
        }
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.INVISIBLE);
        } else {
            mTvTitle.setText(title);
        }
        if (cancelTextColor != 0) {
            mTvCancel.setTextColor(cancelTextColor);
        }

        if (confirmTextColor != 0) {
            mTvConfirm.setTextColor(confirmTextColor);
        }
        if (type != null) {
            if (type.length >= 1) {
                mWvYear.setVisibility(type[0] ? View.VISIBLE : View.GONE);
                mWvMonth.setVisibility(type[0] ? View.VISIBLE : View.GONE);
            }
            if (type.length >= 2) {
                mWvHour.setVisibility(type[1] ? View.VISIBLE : View.GONE);
            }
            if (type.length >= 3) {
                mWvMinute.setVisibility(type[2] ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void initData() {
        if (isFiveSecondInterval) {
            mMinuteDivisor = 5;
            for (int i = 0; i < 12; i++) {
                if (i == 0) {
                    minuteList.add("00");
                } else if (i == 1) {
                    minuteList.add("05");
                } else {
                    minuteList.add(String.valueOf(i * 5));
                }
            }
        } else {
            mMinuteDivisor = 1;
            for (int i = 0; i < 60; i++) {
                if (i < 10) {
                    minuteList.add("0" + i);
                } else {
                    minuteList.add(String.valueOf(i));
                }
            }
        }

        for (int i = 1; i <= 12; i++) {
            hourList.add(String.valueOf(i));
        }
        dayList.add("??????");
        dayList.add("??????");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        yearStr=year+"";
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mYearPosition = (year - 2000);
        mMonthPosition = month;
        mMonthDayPosition=day-1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mMinutePosition = minute / mMinuteDivisor;
        if (hour == 12) {
            mDayPosition = 1;
            mHourPosition = 11;
        } else if (hour == 0) {
            mDayPosition = 0;
            mHourPosition = 11;
        } else if (hour > 12) {
            mDayPosition = 1;
            mHourPosition = calendar.get(Calendar.HOUR) - 1;
        } else {
            mDayPosition = 0;
            mHourPosition = hour - 1;
        }
        yearList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            yearList.add(new YearBean(String.valueOf(2000 + i)+"???"));
        }
        monthList = BhUtils.getMonthList();
        monthDayList=BhUtils.getMonthDay(year,month+1,0);
    }


    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.tv_cancel) {
            dismiss();
        } else if (i == R.id.tv_confirm) {
            if (timeSelectCallBack != null) {
                HashMap<Integer,String> map = getStringTime();
                timeSelectCallBack.onConfirm(map);
                dismiss();
            }
        }
    }

    private long getLongTime(String time) {
        return TimeUtils.string2Millis(time);
    }

    private HashMap<Integer,String> getStringTime() {
        HashMap<Integer,String> map = null;
        String year = yearTextAdapter.getItemText(mWvYear.getCurrentItem()).toString();
        String month = monthList.get(mWvMonth.getCurrentItem()).getMonth();
        String day = monthDayList.get(mWvonthDay.getCurrentItem()).getDay();
        String pmOrAm = dayList.get(mWvDay.getCurrentItem());
        String hour = hourTextAdapter.getItemText(mWvHour.getCurrentItem()).toString();
        String minute = minuteTextAdapter.getItemText(mWvMinute.getCurrentItem()).toString();
        int formatHour = Integer.parseInt(hour);
        /*if (pmOrAm.equals("??????")) {
            if (formatHour == 12) {
                //??????12???
                hour = "12";
            } else {
                formatHour = formatHour + 12;
                hour = String.valueOf(formatHour);
            }
        } else {
            if (formatHour == 12) {
                hour = "00";
            } else {
                if (formatHour < 10) {
                    hour = "0" + formatHour;
                }
            }
        }*/
        try{
            map=buildDate(year,month,day,pmOrAm);
        }catch (Exception e){
            e.printStackTrace();
        }

        String result = year + "-" + month + "-" + day + " " + pmOrAm;
        return map;
    }

    private HashMap<Integer,String> buildDate(String year, String month, String day,String pmOrAm) throws Exception{
        HashMap<Integer,String> map=new HashMap<>();
        String buildDate="";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int tranYear=Integer.parseInt(year.replace("???",""));
        int tranMonth=Integer.parseInt(month.replace("???",""));
        int tranDay=Integer.parseInt(day.replace("???",""));

        Calendar cal = Calendar.getInstance();// ????????????????????????
        cal.clear();// ????????????
        cal.set(Calendar.YEAR, tranYear);
        cal.set(Calendar.MONTH, tranMonth - 1);// 1??????0??????
        cal.set(Calendar.DAY_OF_MONTH, tranDay);
        if (pmOrAm.equals("??????")){
            map.put(1,"PM");
        }else{
            map.put(1,"AM");
        }
        buildDate=sdf.format(cal.getTime());
        map.put(0,buildDate);
        return map;
    }

    public void setBlur(boolean blur) {
        isBlur = blur;
    }

    public void setCancelTitle(String cancelTitle) {
        this.cancelTitle = cancelTitle;
    }

    public void setConfirmTitle(String confirmTitle) {
        this.confirmTitle = confirmTitle;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    public void setFiveSecondInterval(boolean fiveSecondInterval) {
        isFiveSecondInterval = fiveSecondInterval;
    }

    public void setCancelTextColor(int cancelTextColor) {
        this.cancelTextColor = cancelTextColor;
    }

    public void setType(boolean[] type) {
        this.type = type;
    }

    public void setConfirmTextColor(int confirmTextColor) {
        this.confirmTextColor = confirmTextColor;
    }
}
