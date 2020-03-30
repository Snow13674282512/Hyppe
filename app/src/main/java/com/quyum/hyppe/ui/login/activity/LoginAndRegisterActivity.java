package com.quyum.hyppe.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.ui.login.fragment.LoginFragment;
import com.quyum.hyppe.ui.login.fragment.RegisterFragment;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginAndRegisterActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"Sign in", "Sign up"};

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginAndRegisterActivity.class);
        //防止多个登录页面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    }

    @Override
    protected void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(LoginFragment.newInstance());
        fragmentList.add(RegisterFragment.newInstance());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewPager, tabTitle);
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

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.right_top_iv)
    public void onViewClicked() {
    }
}
