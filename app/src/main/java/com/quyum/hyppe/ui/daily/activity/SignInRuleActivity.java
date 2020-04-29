package com.quyum.hyppe.ui.daily.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.ProgressWebView;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2019/07/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SignInRuleActivity extends BaseActivity {
    @BindView(R.id.web_view)
    ProgressWebView webView;
    @BindView(R.id.nodata_ll)
    RelativeLayout noData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Daily check-in rule");
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
        loadDetail();
    }

    void loadDetail() {
        webView.loadUrl("Participant qualification Participants of this event (hereinafter referred to as: you) shall have all the following qualifications: (1) a natural person who has full capacity for civil conduct or has reached the age of 10 and has obtained the consent of his/her legal guardian to participate in the activity; (2) each natural person can use their legitimate and valid QQ account and WeChat account to participate in the activity. Activity rules 1. Participation mode (1) users can obtain certain rewards by completing a series of tasks published by the task center every day, including logging in, watching, Posting, thumb up, commenting, sharing videos, and Posting 30s videos in moments. (2) each user can use multiple accounts (WeChat or QQ accounts, WeChat /QQ accounts do not communicate with each other) to log in the micro video platform and participate in activities, but through one account, the corresponding amount of rewards received will be put into QQ wallet or WeChat to pay change account. (3) to ensure the best participation experience of users, please upgrade the wechat APP to the latest version. 2. Get the reward rules (1) the cash red envelope received by the user will be automatically deposited into the wechat red envelope account, and the user shall withdraw the red envelope within the last day of the following month. If the user fails to withdraw the red envelope after the deadline, the user shall be deemed to have voluntarily given up the right to withdraw the red envelope, and the red envelope shall be null and void. (2) if the winner is unable to enjoy the prize according to the rules of the activity due to his or her own reasons, the winner shall be deemed to give up the prize voluntarily, and tencent will not compensate the winner in any form. (3) before 3:00 am the next day, the user can finish the task of that day and receive the task reward. Users who fail to complete the task after the deadline shall be deemed to have given up voluntarily. Rewards for unfinished and unclaimed tasks do not accumulate.");
    }
}
