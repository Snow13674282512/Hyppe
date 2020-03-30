package com.quyum.hyppe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.login.activity.LoginAndRegisterActivity;
import com.quyum.hyppe.weight.TitleBar;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
        startActivity(new Intent(mContext, LoginAndRegisterActivity.class));
    }
}
