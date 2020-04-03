package com.quyum.hyppe.ui.home.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.netease.neliveplayer.playerkit.sdk.PlayerManager;
import com.netease.neliveplayer.playerkit.sdk.model.SDKInfo;
import com.netease.neliveplayer.playerkit.sdk.model.SDKOptions;
import com.netease.neliveplayer.proxy.config.NEPlayerConfig;
import com.netease.neliveplayer.sdk.NELivePlayer;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.FinishEvent;
import com.quyum.hyppe.event.HomeLeftEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.fragment.HomeFragment;
import com.quyum.hyppe.ui.login.fragment.RegisterFragment;
import com.quyum.hyppe.utils.player.HttpPostUtils;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.quyum.hyppe.utils.ToastUtils.showToast;

public class MainActivity extends BaseActivity {
    public static String TAG = "MainActivity";
    private final int WRITE_PERMISSION_REQ_CODE = 100;

    @BindView(R.id.one_tv)
    TextView oneTv;
    @BindView(R.id.one_view)
    View oneView;
    @BindView(R.id.two_tv)
    TextView twoTv;
    @BindView(R.id.two_view)
    View twoView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.icon_iv)
    CircleImageView iconIv;
    @BindView(R.id.name_tv)
    TextView nameTv;
    @BindView(R.id.introduction_tv)
    TextView introductionTv;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;
    private List<BaseFragment> fragmentList;
    private boolean bPermission = false;
    private SDKOptions config;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("bundle", bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(MainActivity.this, (String[]) permissions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                float scale = 1 - slideOffset;
                mainRl.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MyApplication.isAtPlayer) {
            EventBus.getDefault().post(new PlayerStopEvent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.isAtPlayer) {
            EventBus.getDefault().post(new PlayerStartEvent());
        }
    }

    @Override
    protected void initView() {
        bPermission = checkPublishPermission();
        if (bPermission) {
            initPlayer();
        }
        if (!bPermission) {
            showToast("app所需要的权限未满足");
        }
        if (config != null && config.dynamicLoadingConfig != null &&
                config.dynamicLoadingConfig.isDynamicLoading && !NELivePlayer.isDynamicLoadReady()) {
            showToast("请等待加载so文件");
            return;
        }
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(RegisterFragment.newInstance());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
    }

    private void initPlayer() {
        config = new SDKOptions();
        //是否开启动态加载功能，默认关闭
        //        config.dynamicLoadingConfig = new NEDynamicLoadingConfig();
        //        config.dynamicLoadingConfig.isDynamicLoading = true;
        //        config.dynamicLoadingConfig.isArmeabiv7a = true;
        //        config.dynamicLoadingConfig.armeabiv7aUrl = "your_url";
        //        config.dynamicLoadingConfig.onDynamicLoadingListener = mOnDynamicLoadingListener;
        // SDK将内部的网络请求以回调的方式开给上层，如果需要上层自己进行网络请求请实现config.dataUploadListener，
        // 如果上层不需要自己进行网络请求而是让SDK进行网络请求，这里就不需要操作config.dataUploadListener
        config.dataUploadListener = mOnDataUploadListener;
        //是否支持H265解码回调
        config.supportDecodeListener = mOnSupportDecodeListener;
        //这里可以绑定客户的账号系统或device_id，方便出问题时双方联调
        //        config.thirdUserId = "your_id";
        config.privateConfig = new NEPlayerConfig();
        PlayerManager.init(this, config);
        SDKInfo sdkInfo = PlayerManager.getSDKInfo(this);
        Log.i(TAG, "NESDKInfo:version" + sdkInfo.version + ",deviceId:" + sdkInfo.deviceId);
    }

    private NELivePlayer.OnDataUploadListener mOnDataUploadListener = new NELivePlayer.OnDataUploadListener() {

        @Override
        public boolean onDataUpload(String url, String data) {
            Log.d(TAG, "onDataUpload url:" + url + ", data:" + data);
            sendData(url, data);
            return true;
        }

        @Override
        public boolean onDocumentUpload(String url, Map<String, String> params, Map<String, String> filepaths) {
            Log.d(TAG, "onDataUpload url:" + url + ", params:" + params + ",filepaths:" + filepaths);
            return (new HttpPostUtils(url, params, filepaths).connPost());
        }
    };

    private NELivePlayer.OnSupportDecodeListener mOnSupportDecodeListener = new NELivePlayer.OnSupportDecodeListener() {

        @Override
        public void onSupportDecode(boolean isSupport) {
            Log.d(TAG, "是否支持H265硬件解码 onSupportDecode isSupport:" + isSupport);
            //如果支持H265硬件解码，那么可以使用H265的视频源进行播放
        }
    };

    private boolean sendData(final String urlStr, final String content) {
        int response = 0;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(content.getBytes());
            response = conn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, " sendData finished,data:" + content);

            } else {
                Log.i(TAG, " sendData, response: " + response);

            }
        } catch (IOException e) {
            Log.e(TAG, "sendData, recv code is error: " + e.getMessage());

        } catch (Exception e) {
            Log.e(TAG, "sendData, recv code is error2: " + e.getMessage());

        }
        return (response == HttpURLConnection.HTTP_OK);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_PERMISSION_REQ_CODE:
                initPlayer();
                for (int ret : grantResults) {
                    if (ret != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                bPermission = true;
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.one_ll, R.id.publish_iv, R.id.two_ll, R.id.red_history_ll, R.id.sign_in_ll, R.id.wallet_ll, R.id.friend_ll, R.id.drafts_ll, R.id.message_ll, R.id.ad_ll, R.id.setting_ll, R.id.certification_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_ll:
                viewPager.setCurrentItem(0);
                oneView.setVisibility(View.VISIBLE);
                twoView.setVisibility(View.GONE);
                MyApplication.isAtPlayer = true;
                break;
            case R.id.publish_iv:
                break;
            case R.id.two_ll:
                viewPager.setCurrentItem(1);
                oneView.setVisibility(View.GONE);
                twoView.setVisibility(View.VISIBLE);
                MyApplication.isAtPlayer = false;
                break;
            case R.id.red_history_ll:
                break;
            case R.id.sign_in_ll:
                break;
            case R.id.wallet_ll:
                break;
            case R.id.friend_ll:
                break;
            case R.id.drafts_ll:
                break;
            case R.id.message_ll:
                break;
            case R.id.ad_ll:
                break;
            case R.id.setting_ll:
                break;
            case R.id.certification_ll:
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<BaseFragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public RelativeLayout getMainRl() {
        return mainRl;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishEvent finishEvent) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void open(HomeLeftEvent homeLeftEvent) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }


}
