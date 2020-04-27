package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.FinishEvent;
import com.quyum.hyppe.ui.login.activity.LoginAndRegisterActivity;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Settings");
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

    @OnClick({R.id.account_ll, R.id.notification_ll, R.id.help_ll, R.id.agreement_ll, R.id.about_ll, R.id.how_to_ll, R.id.payment_ll, R.id.switch_account_bt, R.id.log_out_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_ll:
                startActivity(new Intent(mContext, AccountSecurityActivity.class));
                break;
            case R.id.notification_ll:
                startActivity(new Intent(mContext, NotificationSettingActivity.class));
                break;
            case R.id.help_ll:
                startActivity(new Intent(mContext, FeedHelpActivity.class));
                break;
            case R.id.agreement_ll:
                break;
            case R.id.about_ll:
                break;
            case R.id.how_to_ll:
                break;
            case R.id.payment_ll:
                break;
            case R.id.switch_account_bt:
                SystemParams.getInstance().setToken(null);
                MyApplication.CurrentUser = null;
                MyApplication.save();
                SystemParams.getInstance().setMobile(null);
                SystemParams.getInstance().setPassword(null);
                LoginAndRegisterActivity.start(mContext);
                EventBus.getDefault().post(new FinishEvent());
                finish();
                break;
            case R.id.log_out_bt:
                SystemParams.getInstance().setToken(null);
                MyApplication.CurrentUser = null;
                MyApplication.save();
                SystemParams.getInstance().setMobile(null);
                SystemParams.getInstance().setPassword(null);
                LoginAndRegisterActivity.start(mContext);
                EventBus.getDefault().post(new FinishEvent());
                finish();
                break;
        }
    }
}
