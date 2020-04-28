package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.content_tv)
    TextView contentTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("About Us");
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
        versionTv.setText("HYPPE V" + CommonUtil.getAppVersionName(mContext));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
