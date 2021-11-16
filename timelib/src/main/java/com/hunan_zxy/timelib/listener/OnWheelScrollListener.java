package com.hunan_zxy.timelib.listener;

import com.hunan_zxy.timelib.widget.WheelView;

/**
 * FileName: OnWheelScrollListener
 * Author: jibinghao
 * Date: 2019/5/13 9:46 AM
 * Email:heybinghao@gmail.com
 * Description:
 */

public interface OnWheelScrollListener {

    void onScrollingStarted(WheelView wheel);


    void onScrollingFinished(WheelView wheel);
}
