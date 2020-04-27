package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;
import com.suke.widget.SwitchButton;

import butterknife.BindView;

public class NotificationSettingActivity extends BaseActivity {
    @BindView(R.id.comments_sb)
    SwitchButton commentsSb;
    @BindView(R.id.hyppe_sb)
    SwitchButton hyppeSb;
    @BindView(R.id.mentioned_sb)
    SwitchButton mentionedSb;
    @BindView(R.id.friend_request_sb)
    SwitchButton friendRequestSb;
    @BindView(R.id.direct_message_request_sb)
    SwitchButton directMessageRequestSb;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification_settting;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Notification Settings");
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
}
