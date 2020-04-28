package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;

public class DeviceModelActivity extends BaseActivity {
    @BindView(R.id.name_et)
    EditText nameEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_model;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Device Model");
        bar.setRightText("Done");
        bar.getRightText().setTextColor(Color.parseColor("#EE6028"));
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    ToastUtils.showToast("请输入设备型号");
                    return;
                }
                setResult(RESULT_OK, new Intent().putExtra("data", nameEt.getText().toString()));
                finish();
            }
        });
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
        nameEt.setText(getIntent().getStringExtra("data"));
    }

}
