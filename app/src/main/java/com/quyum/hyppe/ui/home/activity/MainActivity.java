package com.quyum.hyppe.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.one_ll, R.id.publish_iv, R.id.two_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.one_ll:
                break;
            case R.id.publish_iv:
                break;
            case R.id.two_ll:
                break;
        }
    }
}
