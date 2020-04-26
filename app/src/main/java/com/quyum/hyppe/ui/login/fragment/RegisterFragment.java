package com.quyum.hyppe.ui.login.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.api.APPApi;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.BaseModel;
import com.quyum.hyppe.net.ApiSubscriber;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.mobile_tv)
    EditText mobileEt;
    @BindView(R.id.code_tv)
    EditText codeEt;
    @BindView(R.id.get_code_bt)
    Button getCodeBt;
    @BindView(R.id.ps_tv)
    EditText psEt;
    @BindView(R.id.eye_iv)
    ImageView eyeIv;
    @BindView(R.id.c_ps_tv)
    EditText cPsEt;
    @BindView(R.id.c_eye_iv)
    ImageView cEyeIv;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    Boolean isCLook = false, isLook = false;

    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private CountDownTimer timer = new CountDownTimer(120000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            if (getCodeBt != null) {
                getCodeBt.setText("Code Send(" + (millisUntilFinished / 1000) + ")");
            }
        }

        @Override
        public void onFinish() {
            if (getCodeBt != null) {
                getCodeBt.setText("Send Verification Code");
                getCodeBt.setEnabled(true);
            }
        }
    };

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_register;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

    @OnClick({R.id.get_code_bt, R.id.eye_iv, R.id.c_eye_iv, R.id.login_bt, R.id.checkbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code_bt:
                if (TextUtils.isEmpty(mobileEt.getText().toString())) {
                    ToastUtils.showToast("请输入手机号码");
                    return;
                }
                getCode();
                break;
            case R.id.eye_iv:
                if (isLook) {
                    psEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    psEt.setSelection(psEt.getText().length());
                    isLook = false;
                    eyeIv.setImageResource(R.drawable.icon_denglu_mimakejianb);
                } else {
                    psEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    psEt.setSelection(psEt.getText().length());
                    isLook = true;
                    eyeIv.setImageResource(R.drawable.icon_denglu_mimakejian);
                }
                break;
            case R.id.c_eye_iv:
                if (isCLook) {
                    cPsEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    cPsEt.setSelection(cPsEt.getText().length());
                    isCLook = false;
                    cEyeIv.setImageResource(R.drawable.icon_denglu_mimakejianb);
                } else {
                    cPsEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    cPsEt.setSelection(cPsEt.getText().length());
                    isCLook = true;
                    cEyeIv.setImageResource(R.drawable.icon_denglu_mimakejian);
                }
                break;
            case R.id.login_bt:
                if (TextUtils.isEmpty(mobileEt.getText().toString())) {
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(codeEt.getText().toString())) {
                    ToastUtils.showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(psEt.getText().toString())) {
                    ToastUtils.showToast("请输入密码");
                    return;
                }
                if (psEt.getText().toString().equals(cPsEt.getText().toString())) {
                    ToastUtils.showToast("两次密码不一致");
                    return;
                }
                break;
            case R.id.checkbox:
                break;
        }
    }

    void getCode() {
        APPApi.getHttpService().getCode(mobileEt.getText().toString())
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .compose(this.bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        showDError(error, null);
                    }

                    @Override
                    public void onNext(BaseModel data) {
                        timer.start();
                        getCodeBt.setEnabled(false);
                        ToastUtils.showToast(data.msg);
                    }
                });
    }
}
