package com.hunan_zxy.timelib.listener;

import com.hunan_zxy.timelib.widget.WheelView;

/**
 * FileName: OnWheelChangedListener
 * Author: jibinghao
 * Date: 2019/5/13 9:44 AM
 * Email:heybinghao@gmail.com
 * Description:
 */

public interface OnWheelChangedListener {

    void onChanged(WheelView wheel, int oldValue, int newValue);

}
