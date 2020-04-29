package com.quyum.hyppe.ui.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qmuiteam.qmui.alpha.QMUIAlphaLinearLayout;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class PaymentSettingActivity extends BaseActivity {
    @BindView(R.id.bottom_view)
    View bottomView;
    @BindView(R.id.forget_password_ll)
    QMUIAlphaLinearLayout forgetPsLl;
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
        if (isHavePs) {
            bottomView.setVisibility(View.VISIBLE);
            forgetPsLl.setVisibility(View.VISIBLE);
        } else {
            bottomView.setVisibility(View.GONE);
            forgetPsLl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.email_ll, R.id.change_password_ll, R.id.forget_password_ll})
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
            case R.id.forget_password_ll:
                startActivity(new Intent(mContext, ForgetPaymentPasswordActivity.class));
                break;
        }
    }
}
