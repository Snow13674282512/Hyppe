package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankActivity extends BaseActivity {
    @BindView(R.id.card_number_et)
    EditText cardNumberEt;
    @BindView(R.id.delete_tv)
    ImageView deleteTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
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
        cardNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(cardNumberEt.getText().toString().trim())) {
                    deleteTv.setVisibility(View.GONE);
                } else {
                    deleteTv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @OnClick({R.id.delete_tv, R.id.next_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_tv:
                cardNumberEt.setText("");
                break;
            case R.id.next_bt:
                if (TextUtils.isEmpty(cardNumberEt.getText().toString().trim())) {
                    ToastUtils.showToast("请输入银行卡号");
                    return;
                }
                startActivity(new Intent(mContext, AddBankNextActivity.class).putExtra("data", cardNumberEt.getText().toString().trim()).
                        putExtra("isCallBack", getIntent().getBooleanExtra("isCallBack", false)));
                finish();
                break;
        }
    }
}
