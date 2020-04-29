package com.quyum.hyppe.ui.setting.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.event.NewCardEvent;
import com.quyum.hyppe.ui.setting.bean.PaymentBean;
import com.quyum.hyppe.ui.setting.activity.SetPaymentPasswordActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankNextActivity extends BaseActivity {
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.id_et)
    EditText idEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    MyDialog myDialog, havePsDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank_next;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Add bank card");
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
        initMyDialog();
        initHavePsDialog();
    }

    void initMyDialog() {
        myDialog = DialogBuilder.selectDialog(mContext, "Set payment password", "Bank card binding successful", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(mContext, SetPaymentPasswordActivity.class).putExtra("data", getIntent().getStringExtra("data"))
                        .putExtra("isCallBack", getIntent().getBooleanExtra("isCallBack", false)));
                finish();
            }
        });
    }

    void initHavePsDialog() {
        havePsDialog = DialogBuilder.selectDialog(mContext, "go back", "Bank card binding successful", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                havePsDialog.dismiss();
            }
        });
        havePsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (getIntent().getBooleanExtra("isCallBack", false)) {
                    PaymentBean.DataBean dataBean = new PaymentBean.DataBean();
                    dataBean.cardNumber = getIntent().getStringExtra("data");
                    EventBus.getDefault().post(new NewCardEvent(dataBean));
                    finish();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.next_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(nameEt.getText().toString().trim())) {
            ToastUtils.showToast("Please input Name of Card holder");
            return;
        }
        if (TextUtils.isEmpty(idEt.getText().toString().trim())) {
            ToastUtils.showToast("Please fill in your id number");
            return;
        }
        if (TextUtils.isEmpty(phoneEt.getText().toString().trim())) {
            ToastUtils.showToast("Please enter the bank reserved phone number");
            return;
        }
        if (nameEt.getText().toString().equals("小明")) {
            myDialog.show();
        } else {
            havePsDialog.show();
        }
    }
}
