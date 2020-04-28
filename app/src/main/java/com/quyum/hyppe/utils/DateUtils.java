package com.quyum.hyppe.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author : Xue
 *     time   : 2019/04/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DateUtils {
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
