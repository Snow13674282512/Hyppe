package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.OnClick;

public class PaymentSettingActivity extends BaseActivity {
    private Boolean isHavePs = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment_setting;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Payment Settings");
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
                startActivity(new Intent(mContext, AddBankActivity.class));
                break;
            case R.id.change_password_ll:
                if (isHavePs) {
                    startActivity(new Intent(mContext, ChangePaymentPasswordActivity.class));
                } else {
                    startActivity(new Intent(mContext, SetPaymentPasswordActivity.class));
                }
                break;
        }
    }
}
