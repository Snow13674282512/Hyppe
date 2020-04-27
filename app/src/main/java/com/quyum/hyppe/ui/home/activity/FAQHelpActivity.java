package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaRelativeLayout;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class FAQHelpActivity extends BaseActivity {
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.yes_rl)
    QMUIAlphaRelativeLayout yesRl;
    @BindView(R.id.no_rl)
    QMUIAlphaRelativeLayout noRl;
    Boolean isClick = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_detail;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("FAQ & Help");
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

    @OnClick({R.id.yes_rl, R.id.no_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.yes_rl:
                if (!isClick) {
                    yesRl.setBackgroundResource(R.drawable.bg_button_feed);
                }
                isClick = true;
                break;
            case R.id.no_rl:
                if (!isClick) {
                    noRl.setBackgroundResource(R.drawable.bg_button_feed);
                }
                isClick = true;
                break;
        }
    }
}
