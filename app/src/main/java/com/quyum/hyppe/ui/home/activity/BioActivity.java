package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class BioActivity extends BaseActivity {
    @BindView(R.id.input_et)
    EditText inputEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bio;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Bio");
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
        inputEt.setText(getIntent().getStringExtra("data"));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.completed_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(inputEt.getText().toString())) {
            ToastUtils.showToast("请输入内容");
            return;
        }
        setResult(RESULT_OK, new Intent().putExtra("data", inputEt.getText().toString().trim()));
        finish();
    }
}
