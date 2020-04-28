package com.quyum.hyppe.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UiUtil {
    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static float getCount(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * px-->dp
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context ,int dp) {
        return (int) (dp * getCount(context));
    }

    /**
     * px-->dp
     * @param context
     * @param px
     * @return
     */
    public static int px2dp(Context context ,int px) {
        return (int) (px / getCount(context));
    }


    /**
     * 获取图片
     * @param context
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context , int id) {
        return context.getResources().getDrawable(id);
    }

    /**
     * 获取颜色集合
     * @param context
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(Context context , int id) {
        return context.getResources().getColorStateList(id);
    }

    /**
     * 获取颜色
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context ,int id) {
        return context.getResources().getColor(id);
    }

    /**
     * 获取dp
     * @param context
     * @param id
     * @return
     */
    public static float getdp(Context context , int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 将字符串中的中文转化为拼音,其他字符不变
     *
     * @param chinese
     * @return
     */
    public static String getPingYin(String chinese) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        char[] input = chinese.trim().toCharArray();
        String output = "";
        try {
            for (char curchar : input) {
                if (Character.toString(curchar).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                            curchar, format);
                    if (temp != null&&temp.length>0){
                        output += temp[0];
                    }else {
                        output += Character.toString(curchar);
                    }
                } else {
                    output += Character.toString(curchar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output.toLowerCase();
    }

    /**
     * 汉字转换为汉语拼音首字母，英文字符不变
     *
     * @param chinese 汉字
     * @return 拼音
     */
    public static String getFirstSpell(String chinese) {
        if (chinese.length() > 0) {
            StringBuffer pybf = new StringBuffer();
            char[] arr = chinese.toCharArray();
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (char curchar : arr) {
                if (curchar > 128) {
                    try {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, defaultFormat);
                        if (temp != null&&temp.length>0&&temp[0].length()>0) {
                            pybf.append(temp[0].charAt(0));
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    pybf.append(curchar);
                }
            }
            return pybf.toString().trim().toLowerCase();
        }
        return "";
    }

    /**
     * 重写contain方法
     * @param input
     * @param chinese
     * @return
     */
    public static boolean contain(String input, String chinese) {
        boolean result;
        try {
            Pattern p = Pattern.compile(chinese.toLowerCase(), Pattern.LITERAL);
            Matcher m = p.matcher(input.toLowerCase());
            result = m.find();
        } catch (Exception e) {
            return false;
        }
        return result;
    }
}
