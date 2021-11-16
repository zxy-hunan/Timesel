package com.hunan_zxy.timelib.listener;

import java.util.HashMap;

/**
 * FileName: TimeSelectCallBack
 * Author: jibinghao
 * Date: 2019/5/13 9:39 AM
 * Email:heybinghao@gmail.com
 * Description: 时间选择回调
 */

public interface TimeSelectCallBack {

    void onChoose(String formatTime, long originTime);

    void onConfirm(HashMap<Integer,String> map);

}
