package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;

public class HashActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_creator_tv)
    TextView contentCreatorTv;
    @BindView(R.id.orgin_location_tv)
    TextView orginLocationTv;
    @BindView(R.id.time_created_tv)
    TextView timeCreatedTv;
    @BindView(R.id.production_device_tv)
    TextView productionDeviceTv;
    @BindView(R.id.device_model_tv)
    TextView deviceModelTv;
    @BindView(R.id.screenwriter_tv)
    TextView screenwriterTv;
    @BindView(R.id.cast_tv)
    TextView castTv;
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hash;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Hash");
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
