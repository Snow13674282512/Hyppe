package com.quyum.hyppe.ui.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetActivity extends BaseActivity {
    @BindView(R.id.mobile_tv)
    EditText mobileTv;
    @BindView(R.id.code_tv)
    EditText codeTv;
    @BindView(R.id.get_code_bt)
    Button getCodeBt;
    @BindView(R.id.ps_tv)
    EditText psTv;
    @BindView(R.id.eye_iv)
    ImageView eyeIv;
    @BindView(R.id.c_ps_tv)
    EditText cPsTv;
    @BindView(R.id.c_eye_iv)
    ImageView cEyeIv;
    @BindView(R.id.checkbox)
    CheckBox checkbox;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_froget;
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

    }

    @OnClick({R.id.right_top_iv, R.id.get_code_bt, R.id.eye_iv, R.id.c_eye_iv, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_top_iv:
                break;
            case R.id.get_code_bt:
                break;
            case R.id.eye_iv:
                break;
            case R.id.c_eye_iv:
                break;
            case R.id.login_bt:
                break;
        }
    }
}
