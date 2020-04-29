package com.quyum.hyppe.ui.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.utils.EditInputFilter;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.payDialog.OnPasswordFinishedListener;
import com.quyum.hyppe.weight.payDialog.WeChatPayWindow;

import butterknife.BindView;
import butterknife.OnClick;

public class PointTopUpActivity extends BaseActivity implements OnPasswordFinishedListener {
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.after_tv)
    TextView afterTv;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    String rate = "1.1";
    private WeChatPayWindow weChatPayWindow;
    private MyDialog myDialog, topUpDialog;
    String balance = "8455.132531";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_point_top_up;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Top Up");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(inputEt.getText().toString())) {
                    afterTv.setText("");
                } else {
                    afterTv.setText(CommonUtil.setScale(String.valueOf(CommonUtil.mul(Double.parseDouble(inputEt.getText().toString()), Double.parseDouble(rate))), 2));
                }
            }
        });
    }

    @Override
    protected void initView() {
        InputFilter[] filters = {new EditInputFilter()};
        inputEt.setFilters(filters);

        initMyDialog();
        initTopUpDialog();
    }

    void initMyDialog() {
        myDialog = DialogBuilder.passwordErrorDialog(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                initWindow();
                weChatPayWindow.show(rootLayout);
                weChatPayWindow.setPrice(afterTv.getText().toString());
            }
        });
    }

    void initTopUpDialog() {
        topUpDialog = DialogBuilder.selectDialog(mContext, "Top Up", "Please top up your Point.", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topUpDialog.dismiss();
                startActivity(new Intent(mContext, PointConversionActivity.class));
            }
        });
    }

    void initWindow() {
        weChatPayWindow = new WeChatPayWindow(this);
        weChatPayWindow.setOnPasswordFinishedListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onFinish(String password) {
        weChatPayWindow.dismiss();
        if (Double.parseDouble(afterTv.getText().toString()) > 1) {
            ToastUtils.showToast("转换成功");
            finish();
        } else {
            myDialog.show();
        }
    }

    @OnClick({R.id.all_withdrawal_tv, R.id.sure_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.all_withdrawal_tv:
                inputEt.setText(CommonUtil.setScale(balance, 2));
                inputEt.setSelection(inputEt.length());//将光标移至文字末尾
                break;
            case R.id.sure_bt:
                if (TextUtils.isEmpty(inputEt.getText().toString().trim())) {
                    ToastUtils.showToast("请输入Point数量");
                    return;
                }
                if (Double.parseDouble(inputEt.getText().toString().trim()) > 1) {
                    topUpDialog.show();
                } else {
                    initWindow();
                    weChatPayWindow.show(rootLayout);
                    weChatPayWindow.setPrice(afterTv.getText().toString());
                }
                break;
        }
    }
}
