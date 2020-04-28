package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ScreenWriterActivity extends BaseActivity {
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.content_et)
    EditText contentEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screenwriter;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Screenwriter");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numTv.setText(contentEt.getText().length() + "/200 Letters");
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        contentEt.setText(getIntent().getStringExtra("data"));
    }

    @OnClick(R.id.completed_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(contentEt.getText().toString().trim())) {
            ToastUtils.showToast("Please Fill in Your Brief Synopsis");
            return;
        }
        setResult(RESULT_OK, new Intent().putExtra("data", contentEt.getText().toString().trim()));
        finish();
    }
}
