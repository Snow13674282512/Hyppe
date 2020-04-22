package com.quyum.hyppe.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.BuildConfig;
import com.dueeeke.videoplayer.ijk.IjkPlayerFactory;
import com.dueeeke.videoplayer.player.VideoViewConfig;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.quyum.hyppe.R;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.NetProvider;
import com.quyum.hyppe.net.RequestHandler;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.net.XLog;
import com.quyum.hyppe.utils.StreamUtil;
import com.tencent.bugly.crashreport.CrashReport;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/2/4 0004.
 */

public class MyApplication extends Application {
    public static RequestOptions options, optionsCircle;
    public static UserBean.DataBean CurrentUser;
    public static boolean startFrist = false;//是否启动过
    private static Context context;
    private static Handler mHandler;//主线程Handler
    public static Boolean isAtPlayerOne = true;
    public static Boolean isAtPlayerTwo = false;
    public static Boolean isAtHome = true;

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
    }
}
