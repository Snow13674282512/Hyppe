package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class PrivacySettingActivity extends BaseActivity {
    @BindView(R.id.public_iv)
    ImageView publicIv;
    @BindView(R.id.friends_iv)
    ImageView friendsIv;
    @BindView(R.id.only_me_iv)
    ImageView onlyMeIv;
    String type = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_privacy_setting;
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
        switch (getIntent().getStringExtra("data")) {
            case "0":
                type = "0";
                publicIv.setVisibility(View.VISIBLE);
                friendsIv.setVisibility(View.GONE);
                onlyMeIv.setVisibility(View.GONE);
                break;
            case "1":
                type = "1";
                publicIv.setVisibility(View.GONE);
                friendsIv.setVisibility(View.VISIBLE);
                onlyMeIv.setVisibility(View.GONE);
                break;
            case "2":
                type = "2";
                publicIv.setVisibility(View.GONE);
                friendsIv.setVisibility(View.GONE);
                onlyMeIv.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.public_ll, R.id.friends_ll, R.id.only_me_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.public_ll:
                type = "0";
                publicIv.setVisibility(View.VISIBLE);
                friendsIv.setVisibility(View.GONE);
                onlyMeIv.setVisibility(View.GONE);
                setResult(RESULT_OK, new Intent().putExtra("data", type));
                finish();
                break;
            case R.id.friends_ll:
                type = "1";
                publicIv.setVisibility(View.GONE);
                friendsIv.setVisibility(View.VISIBLE);
                onlyMeIv.setVisibility(View.GONE);
                setResult(RESULT_OK, new Intent().putExtra("data", type));
                finish();
                break;
            case R.id.only_me_ll:
                type = "2";
                publicIv.setVisibility(View.GONE);
                friendsIv.setVisibility(View.GONE);
                onlyMeIv.setVisibility(View.VISIBLE);
                setResult(RESULT_OK, new Intent().putExtra("data", type));
                finish();
                break;
        }
    }
}
