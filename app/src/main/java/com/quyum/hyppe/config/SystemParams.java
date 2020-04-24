package com.quyum.hyppe.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 保存系统信息
 *
 * @author song
 * @date 2015年11月4日
 */
public class SystemParams {

    public static final String PLATFORM_PHONE = "platform_phone";//平台电话-保存key
    public static final String CURRENT_CITY_CODE = "adcode";//当前城市code码--通过获取当前城市定位--调用接口获取code值，，抢红包的时候使用这个值
    private static SystemParams instance;
    private static SharedPreferences sharedPrederences = null;

    private SystemParams() {
    }

    public static void init(Context context) {
        sharedPrederences = context.getSharedPreferences("hyppe", Context.MODE_PRIVATE);
    }

    public static SystemParams getInstance() {

        if (instance == null) {
            synchronized (SystemParams.class) {
                if (instance == null) {
                    instance = new SystemParams();
                }
            }
        }
        return instance;
    }

    public String getLon() {
        return sharedPrederences.getString("lon", null);
    }

    public void setLon(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("lon", value);
        editor.commit();
    }

    public String getLatThree() {
        return sharedPrederences.getString("latThree", null);
    }

    public void setLatThree(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("latThree", value);
        editor.commit();
    }

    public List<String> getSearch() {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(sharedPrederences.getString("searchHistory", ""))) {
            list = Arrays.asList(sharedPrederences.getString("searchHistory", "").split(","));
        }
        List searchList = new ArrayList(list);
        return searchList;
    }

    public void setSearch(String value) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(sharedPrederences.getString("searchHistory", ""))) {
            list = Arrays.asList(sharedPrederences.getString("searchHistory", "").split(","));
        }
        List searchList = new ArrayList(list);
        if (searchList.size() == 10) {
            Boolean isHave = false;
            for (int i = searchList.size() - 1; i >= 0; i--) {
                if (searchList.get(i).equals(value)) {
                    searchList.remove(i);
                    isHave = true;
                }
            }
            if (isHave) {
                searchList.add(0, value);
            } else {
                searchList.remove(searchList.size() - 1);
                searchList.add(0, value);
            }
        } else if (searchList.size() == 0) {
            searchList.add(value);
        } else {
            for (int i = searchList.size() - 1; i >= 0; i--) {
                if (searchList.get(i).equals(value)) {
                    searchList.remove(i);
                }
            }
            searchList.add(0, value);
        }
        StringBuffer str2 = new StringBuffer();
        for (Iterator<String> iterator = searchList.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            str2.append(string);
            if (iterator.hasNext()) {
                str2.append(",");
            }
        }
        Editor editor = sharedPrederences.edit();
        editor.putString("searchHistory", String.valueOf(str2));
        editor.commit();
    }

    public void removeSearch(String value) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(sharedPrederences.getString("searchHistory", ""))) {
            list = Arrays.asList(sharedPrederences.getString("searchHistory", "").split(","));
        }
        List searchList = new ArrayList(list);
        for (int i = searchList.size() - 1; i >= 0; i--) {
            if (searchList.get(i).equals(value)) {
                searchList.remove(i);
            }
        }
        StringBuffer str2 = new StringBuffer();
        for (Iterator<String> iterator = searchList.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            str2.append(string);
            if (iterator.hasNext()) {
                str2.append(",");
            }
        }
        Editor editor = sharedPrederences.edit();
        editor.putString("searchHistory", String.valueOf(str2));
        editor.commit();
    }

    public Boolean getDowAgreement() {
        return sharedPrederences.getBoolean("dowAgreement", false);
    }

    public void setDowAgreement(Boolean value) {
        Editor editor = sharedPrederences.edit();
        editor.putBoolean("dowAgreement", value);
        editor.commit();
    }

    public Boolean getGiftAgreement() {
        return sharedPrederences.getBoolean("giftAgreement", false);
    }

    public void setGiftAgreement(Boolean value) {
        Editor editor = sharedPrederences.edit();
        editor.putBoolean("giftAgreement", value);
        editor.commit();
    }

    public String getLonThree() {
        return sharedPrederences.getString("lonThree", null);
    }

    public void setLonThree(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("lonThree", value);
        editor.commit();
    }

    public String getLat() {
        return sharedPrederences.getString("lat", null);
    }

    public void setLat(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("lat", value);
        editor.commit();
    }

    public String getCityThree() {
        return sharedPrederences.getString("cityThree", null);
    }

    public void setCityThree(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("cityThree", value);
        editor.commit();
    }

    public String getCity() {
        return sharedPrederences.getString("city", null);
    }

    public void setCity(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("city", value);
        editor.commit();
    }

    public String getArea() {
        return sharedPrederences.getString("area", null);
    }

    public void setArea(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("area", value);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPrederences.getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        return sharedPrederences.getInt(key, defValue);
    }

    public float getFloat(String key) {
        return sharedPrederences.getFloat(key, 0);
    }

    public float getFloat(String key, float defValue) {
        return sharedPrederences.getFloat(key, defValue);
    }

    public long getLong(String key) {
        return sharedPrederences.getLong(key, 0);
    }

    public long getLong(String key, long defValue) {
        return sharedPrederences.getLong(key, defValue);
    }

    public String getMobile() {
        return sharedPrederences.getString("mobile", null);
    }

    public void setMobile(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("mobile", value);
        editor.commit();
    }

    public String getPassword() {
        return sharedPrederences.getString("password", null);
    }

    public void setPassword(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("password", value);
        editor.commit();
    }

    public String getMemberId() {
        return sharedPrederences.getString("MemberId", null);
    }

    public void setMemberId(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("MemberId", value);
        editor.commit();
    }

    public String getToken() {
        return sharedPrederences.getString("Token", null);
    }

    public void setToken(String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString("Token", value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPrederences.getString(key, null);
    }

    public String getString(String key, String defValue) {
        return sharedPrederences.getString(key, defValue);
    }

    public boolean getBoolean(String key) {
        return sharedPrederences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPrederences.getBoolean(key, defValue);
    }

    public void setInt(String key, int value) {
        Editor editor = sharedPrederences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setFloat(String key, float value) {
        Editor editor = sharedPrederences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void setLong(String key, long value) {
        Editor editor = sharedPrederences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setString(String key, String value) {
        Editor editor = sharedPrederences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setBoolean(String key, boolean value) {
        Editor editor = sharedPrederences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setSetString(String key, Set<String> values) {
        Editor editor = sharedPrederences.edit();
        editor.putStringSet(key, values);
        editor.commit();
    }

    public void remove(String key) {
        Editor editor = sharedPrederences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        Editor editor = sharedPrederences.edit();
        editor.clear().commit();
    }
}
