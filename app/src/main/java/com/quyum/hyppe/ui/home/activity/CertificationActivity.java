package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class CertificationActivity extends BaseActivity {
    @BindView(R.id.image_iv)
    ImageView imageIv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.submit_bt)
    QMUIAlphaButton submitBt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Certification");
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
        switch (getIntent().getIntExtra("status", 0)) {
            case 0:
                imageIv.setImageResource(R.drawable.img_renzheng_yishangchuan);
                contentTv.setText("You have been certified");
                break;
            case 1:
                imageIv.setImageResource(R.drawable.icon_shz);
                contentTv.setText("In the review");
                break;
            case 2:
                imageIv.setImageResource(R.drawable.icon_shsb);
                contentTv.setText("Audit failure");
                submitBt.setVisibility(View.VISIBLE);
                break;
        }
    }


    @OnClick(R.id.submit_bt)
    public void onViewClicked() {
        startActivity(new Intent(mContext, RealNameActivity.class));
        finish();
    }
}
