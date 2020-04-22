package com.quyum.hyppe.ui.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.ui.home.activity.MainActivity;
import com.quyum.hyppe.ui.login.activity.ForgetActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment {
    @BindView(R.id.mobile_tv)
    EditText mobileEt;
    @BindView(R.id.ps_tv)
    EditText psEt;
    @BindView(R.id.eye_iv)
    ImageView eyeIv;
    Boolean isLook = false;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_login;
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

    @OnClick({R.id.eye_iv, R.id.forget_tv, R.id.login_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eye_iv:
                if (isLook) {
                    psEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    psEt.setSelection(psEt.getText().length());
                    isLook = false;
                } else {
                    psEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    psEt.setSelection(psEt.getText().length());
                    isLook = true;
                }
                break;
            case R.id.forget_tv:
                startActivity(new Intent(mActivity, ForgetActivity.class));
                break;
            case R.id.login_bt:
//                if (TextUtils.isEmpty(mobileEt.getText().toString())) {
//                    ToastUtils.showToast("请输入手机号");
//                    return;
//                }
//                if (TextUtils.isEmpty(psEt.getText().toString())) {
//                    ToastUtils.showToast("请输入密码");
//                    return;
//                }
                startActivity(new Intent(mActivity, MainActivity.class));
                mActivity.finish();
                break;
        }
    }
}
