package com.quyum.hyppe.ui.home.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.HomeLeftEvent;
import com.quyum.hyppe.event.PlayerChildSwitchStopEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.weight.SlidingScaleTabLayout.SlidingScaleTabLayout;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SlidingScaleTabLayout tabLayout;
    private String[] tabTitle = {"Follows", "Recommend"};

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewPager, tabTitle);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    MyApplication.isAtPlayerOne = true;
                    MyApplication.isAtPlayerTwo = false;
                    EventBus.getDefault().post(new PlayerStartEvent());
                    EventBus.getDefault().post(new PlayerChildSwitchStopEvent());
                } else {
                    MyApplication.isAtPlayerTwo = true;
                    MyApplication.isAtPlayerOne = false;
                    EventBus.getDefault().post(new PlayerStartEvent());
                    EventBus.getDefault().post(new PlayerChildSwitchStopEvent());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitle.length;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            View view = (View) object;
            return (int) view.getTag();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        @Override
        public Fragment getItem(int i) {
            return HomeChildFragment.newInstance(i);
        }
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @OnClick({R.id.left_iv, R.id.search_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_iv:
                EventBus.getDefault().post(new HomeLeftEvent());
                break;
            case R.id.search_iv:
                break;
        }
    }
}
