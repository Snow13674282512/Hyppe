package com.quyum.hyppe.ui.setting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.event.NewCardEvent;
import com.quyum.hyppe.ui.setting.bean.PaymentBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.VerifyCodeView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPaymentPasswordActivity extends BaseActivity {
    @BindView(R.id.ps_view)
    VerifyCodeView psView;
    @BindView(R.id.eye_iv)
    ImageView eyeIv;
    Boolean isLook = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_payment_password;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Set payment password");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        psView.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {

            }

            @Override
            public void invalidContent() {

            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.eye_iv, R.id.confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eye_iv:
                if (isLook) {
                    eyeIv.setImageResource(R.drawable.icon_mima_kejian);
                    isLook = false;
                    psView.setIsVisibility(true);
                } else {
                    eyeIv.setImageResource(R.drawable.icon_mima_bukejian);
                    isLook = true;
                    psView.setIsVisibility(false);
                }
                break;
            case R.id.confirm_bt:
                if (!psView.isComplete) {
                    return;
                } else {
                    if (getIntent().getBooleanExtra("isCallBack", false)) {
                        PaymentBean.DataBean dataBean = new PaymentBean.DataBean();
                        dataBean.cardNumber = getIntent().getStringExtra("data");
                        EventBus.getDefault().post(new NewCardEvent(dataBean));
                        finish();
                    } else {
                        ToastUtils.showToast("设置成功");
                        finish();
                    }
                }
                break;
        }
    }
}
