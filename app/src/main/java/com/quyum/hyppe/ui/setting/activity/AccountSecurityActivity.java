package com.quyum.hyppe.ui.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountSecurityActivity extends BaseActivity {
    @BindView(R.id.email_tv)
    TextView emailTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_security;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Account Security");
        return true;
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

    @OnClick({R.id.email_ll, R.id.change_password_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.email_ll:
                DialogBuilder.change(mContext, emailTv.getText().toString().trim());
                break;
            case R.id.change_password_ll:
                startActivity(new Intent(mContext, ChangePasswordActivity.class));
                break;
        }
    }
}
