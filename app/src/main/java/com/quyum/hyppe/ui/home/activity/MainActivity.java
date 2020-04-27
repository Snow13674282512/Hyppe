package com.quyum.hyppe.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.BaseMainActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.FinishEvent;
import com.quyum.hyppe.event.HomeLeftEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.fragment.ChannelsFragment;
import com.quyum.hyppe.ui.home.fragment.HomeFragment;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseMainActivity {
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
    Boolean isFirst = true;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("bundle", bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
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
                if (MyApplication.isAtHome) {
                    EventBus.getDefault().post(new PlayerStopEvent());
                }
                MyApplication.isOpen = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                if (MyApplication.isAtHome) {
                    EventBus.getDefault().post(new PlayerStartEvent());
                }
                MyApplication.isOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (MyApplication.isAtHome) {
            EventBus.getDefault().post(new PlayerStopEvent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.isAtHome) {
            if (!isFirst && !MyApplication.isOpen) {
                EventBus.getDefault().post(new PlayerStartEvent());
            } else {
                isFirst = false;
            }
        }
    }

    @Override
    protected void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragment.newInstance());
        fragmentList.add(ChannelsFragment.newInstance());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPager.setCurrentItem(0);
                    oneView.setVisibility(View.VISIBLE);
                    twoView.setVisibility(View.GONE);
                    MyApplication.isAtHome = true;
                    EventBus.getDefault().post(new PlayerStartEvent());
                } else {
                    viewPager.setCurrentItem(1);
                    oneView.setVisibility(View.GONE);
                    twoView.setVisibility(View.VISIBLE);
                    MyApplication.isAtHome = false;
                    EventBus.getDefault().post(new PlayerStopEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.one_ll, R.id.publish_iv, R.id.two_ll, R.id.mine_ll, R.id.red_history_ll, R.id.sign_in_ll, R.id.wallet_ll, R.id.friend_ll, R.id.drafts_ll, R.id.message_ll, R.id.ad_ll,
            R.id.group_ll, R.id.setting_ll, R.id.certification_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_ll:
                viewPager.setCurrentItem(0);
                oneView.setVisibility(View.VISIBLE);
                twoView.setVisibility(View.GONE);
                MyApplication.isAtHome = true;
                EventBus.getDefault().post(new PlayerStartEvent());
                break;
            case R.id.publish_iv:
                break;
            case R.id.two_ll:
                viewPager.setCurrentItem(1);
                oneView.setVisibility(View.GONE);
                twoView.setVisibility(View.VISIBLE);
                MyApplication.isAtHome = false;
                EventBus.getDefault().post(new PlayerStopEvent());
                break;
            case R.id.mine_ll:
                startActivity(new Intent(mContext, MinePageActivity.class));
                break;
            case R.id.red_history_ll:
                startActivity(new Intent(mContext, GiftHistoryActivity.class));
                break;
            case R.id.sign_in_ll:
                startActivity(new Intent(mContext, SignInActivity.class));
                break;
            case R.id.wallet_ll:
                startActivity(new Intent(mContext, WalletActivity.class));
                break;
            case R.id.friend_ll:
                break;
            case R.id.drafts_ll:
                startActivity(new Intent(mContext, DraftBoxActivity.class));
                break;
            case R.id.message_ll:
                break;
            case R.id.ad_ll:
                break;
            case R.id.group_ll:
                startActivity(new Intent(mContext, GroupActivity.class));
                break;
            case R.id.setting_ll:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
            case R.id.certification_ll:
                startActivity(new Intent(mContext, RealNameActivity.class));
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
