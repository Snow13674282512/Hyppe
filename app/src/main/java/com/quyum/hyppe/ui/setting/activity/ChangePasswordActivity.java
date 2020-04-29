package com.quyum.hyppe.ui.setting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.api.APPApi;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseModel;
import com.quyum.hyppe.net.ApiSubscriber;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.eamil_et)
    EditText eamilEt;
    @BindView(R.id.code_et)
    EditText codeEt;
    @BindView(R.id.get_code_tv)
    TextView getCodeTv;

    private CountDownTimer timer = new CountDownTimer(120000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if (getCodeTv != null) {
                getCodeTv.setText("Code Send(" + (millisUntilFinished / 1000) + ")");
            }
        }

        @Override
        public void onFinish() {
            if (getCodeTv != null) {
                getCodeTv.setText("Send Verification Code");
                getCodeTv.setEnabled(true);
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
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

    @OnClick({R.id.get_code_tv, R.id.ok_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_tv:
                if (TextUtils.isEmpty(eamilEt.getText().toString())) {
                    ToastUtils.showToast("请输入邮箱地址");
                    return;
                }
                getCode();
                break;
            case R.id.ok_bt:
                if (TextUtils.isEmpty(eamilEt.getText().toString())) {
                    ToastUtils.showToast("请输入邮箱地址");
                    return;
                }
                if (TextUtils.isEmpty(codeEt.getText().toString())) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                startActivity(new Intent(mContext, ChangePasswordNextActivity.class));
                finish();
                break;
        }
    }

    void getCode() {
        APPApi.getHttpService().getCode(eamilEt.getText().toString())
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        showDError(error, null);
                    }

                    @Override
                    public void onNext(BaseModel data) {
                        timer.start();
                        getCodeTv.setEnabled(false);
                        ToastUtils.showToast(data.msg);
                    }
                });
    }
}
