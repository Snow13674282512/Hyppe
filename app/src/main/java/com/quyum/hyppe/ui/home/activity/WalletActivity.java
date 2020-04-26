package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.event.WalletEvent;
import com.quyum.hyppe.ui.home.fragment.WalletFragment;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.credit_tv)
    TextView creditTv;
    @BindView(R.id.point_tv)
    TextView pointTv;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"All", "Credit", "Point"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Wallet");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                EventBus.getDefault().post(new WalletEvent());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshFinish();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(WalletFragment.newInstance("0"));
        fragmentList.add(WalletFragment.newInstance("1"));
        fragmentList.add(WalletFragment.newInstance("2"));
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewPager, tabTitle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.credit_ll, R.id.point_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.credit_ll:
                startActivity(new Intent(mContext, WalletCreditActivity.class));
                break;
            case R.id.point_ll:
                startActivity(new Intent(mContext, WalletPointActivity.class));
                break;
        }
    }

    private void refreshFinish() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
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
            return tabTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
    }
}
