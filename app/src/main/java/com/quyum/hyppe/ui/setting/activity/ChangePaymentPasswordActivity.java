package com.quyum.hyppe.ui.setting.activity;

import android.content.Intent;
import android.os.Bundle;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.setting.activity.SetPaymentPasswordActivity;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.VerifyCodeView;

import butterknife.BindView;

public class ChangePaymentPasswordActivity extends BaseActivity {
    @BindView(R.id.ps_view)
    VerifyCodeView psView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_payment_password;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Set payment password");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        psView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                if (psView.getEditContent().equals("123456")) {
                    startActivity(new Intent(mContext, SetPaymentPasswordActivity.class));
                    finish();
                } else {
                    DialogBuilder.errorDialog(mContext);
                    psView.ClearInput();
                }
            }

            @Override
            public void invalidContent() {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
