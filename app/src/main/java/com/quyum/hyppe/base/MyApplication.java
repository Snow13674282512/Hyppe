package com.quyum.hyppe.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.google.gson.Gson;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.quyum.hyppe.R;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.NetProvider;
import com.quyum.hyppe.net.RequestHandler;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.net.XLog;
import com.quyum.hyppe.ui.home.bean.JsonBean;
import com.quyum.hyppe.utils.GetJsonDataUtil;
import com.quyum.hyppe.utils.StreamUtil;
import com.tencent.bugly.crashreport.CrashReport;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyApplication extends Application {
    public static RequestOptions options, optionsCircle, optionsIcon;
    public static UserBean.DataBean CurrentUser;
    public static boolean startFrist = false;//是否启动过
    private static Context context;
    private static Handler mHandler;//主线程Handler
    public static Boolean isAtPlayerOne = true;
    public static Boolean isAtPlayerTwo = false;
    public static Boolean isAtHome = true;
    public static Boolean isOpen = false;
    public static String searchStr = "";
    public static List<JsonBean> options1Items = new ArrayList<>();
    public static ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public static ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private Thread thread;
    public static Boolean isLoaded = false;

    public static Context getContext() {
        return context;
    }

    public static String getUserId() {
        if (MyApplication.CurrentUser != null) {
            return MyApplication.CurrentUser.ui_id;
        }
        return "";
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static String getCacheFile() {
        return context.getCacheDir().getAbsolutePath();
    }

    public static synchronized void save() {
        StreamUtil.saveObject(getCacheFile() + UserBean.DataBean.TAG, CurrentUser);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandlerJson = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        XLog.e("MyApplication", "-------------onCreate-start" + System.currentTimeMillis());
        context = this;

        QMUISwipeBackActivityManager.init(this);
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(BuildConfig.DEBUG)//调试的时候请打开日志，方便排错
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
//                .setEnableOrientation(true)
//                .setEnableAudioFocus(false)
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
//                .setAdaptCutout(false)
//                .setPlayOnMobileNetwork(true)
//                .setProgressManager(new ProgressManagerImpl())
                .build());
        //测试
        if (null == CurrentUser && startFrist) {//第一次启动不去加载
            // 从本地读取--防止闪退的时候清空了CurrentUser
            CurrentUser = (UserBean.DataBean) StreamUtil.restoreObject(getCacheFile() + UserBean.DataBean.TAG);
        }
        mHandler = new Handler();

        SystemParams.init(MyApplication.getContext());
        //Glide配置
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        //圆图Glide配置
        optionsCircle = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.default_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        //个人主页Glide配置
        optionsIcon = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img_wodezhuye_weidenglu_touxiang)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //网络请求配置
        XApi.registerProvider(new NetProvider() {

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return null;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
        /**
         * 异常上报  appid  是否打印上报日志
         */
        CrashReport.initCrashReport(getApplicationContext(), "727b897bce", false);
        startFrist = true;
        XLog.e("MyApplication", "-------------onCreate-end" + System.currentTimeMillis());
        mHandlerJson.sendEmptyMessage(MSG_LOAD_DATA);
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandlerJson.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandlerJson.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
