package com.quyum.hyppe.ui.setting.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordNextActivity extends BaseActivity {
    @BindView(R.id.ps_et)
    EditText psEt;
    @BindView(R.id.again_et)
    EditText againEt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password_next;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Change Password");
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


    @OnClick(R.id.ok_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(psEt.getText().toString())) {
            ToastUtils.showToast("请输入新密码");
            return;
        }
        if (!psEt.getText().toString().equals(againEt.getText().toString())) {
            ToastUtils.showToast("两次密码输入不一致");
            return;
        }
        ToastUtils.showToast("修改成功");
        finish();
    }
}
