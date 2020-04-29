package com.quyum.hyppe.ui.wallet.activity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.event.NewCardEvent;
import com.quyum.hyppe.ui.setting.bean.PaymentBean;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.utils.EditInputFilter;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.WithdrawalPopWindow;
import com.quyum.hyppe.weight.payDialog.OnPasswordFinishedListener;
import com.quyum.hyppe.weight.payDialog.WeChatPayWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditWithdrawalActivity extends BaseActivity implements OnPasswordFinishedListener {
    @BindView(R.id.card_tv)
    TextView cardTv;
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    String balance = "513.132531";
    WithdrawalPopWindow withdrawalPopWindow;
    private WeChatPayWindow weChatPayWindow;
    private MyDialog myDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_withdrawal;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Withdrawal");
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
        InputFilter[] filters = {new EditInputFilter()};
        inputEt.setFilters(filters);

        initWithdrawalPop();
        setPopData();
        initMyDialog();
    }

    void initMyDialog() {
        myDialog = DialogBuilder.passwordErrorDialog(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                initWindow();
                weChatPayWindow.show(rootLayout);
                weChatPayWindow.setPrice(inputEt.getText().toString());
            }
        });
    }

    void initWithdrawalPop() {
        withdrawalPopWindow = new WithdrawalPopWindow(rootLayout, mContext);
        withdrawalPopWindow.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<PaymentBean.DataBean> list = adapter.getData();
                cardTv.setText(list.get(position).cardNumber);
                withdrawalPopWindow.close();
            }
        });
    }

    void setPopData() {
        List<PaymentBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PaymentBean.DataBean bankItem = new PaymentBean.DataBean();
            bankItem.cardNumber = "438976945769347" + i;
            list.add(bankItem);
        }
        withdrawalPopWindow.getAdapter().setNewData(list);
    }

    void initWindow() {
        weChatPayWindow = new WeChatPayWindow(this);
        weChatPayWindow.setOnPasswordFinishedListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void newCard(NewCardEvent newCardEvent) {
        withdrawalPopWindow.getAdapter().addData(newCardEvent.dataBean);
    }

    @OnClick({R.id.card_tv, R.id.all_withdrawal_tv, R.id.confirm_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.card_tv:
                if (withdrawalPopWindow.getAdapter().getData().size() > 0) {
                    withdrawalPopWindow.show();
                } else {
                    setPopData();
                    ToastUtils.showToast("暂未获取到支付信息，请稍后再试");
                }
                break;
            case R.id.all_withdrawal_tv:
                inputEt.setText(CommonUtil.setScale(balance, 2));
                inputEt.setSelection(inputEt.length());//将光标移至文字末尾
                break;
            case R.id.confirm_bt:
                if (TextUtils.isEmpty(inputEt.getText().toString().trim())) {
                    ToastUtils.showToast("请输入提现数量");
                    return;
                }
                initWindow();
                weChatPayWindow.show(rootLayout);
                weChatPayWindow.setPrice(inputEt.getText().toString());
                break;
        }
    }

    @Override
    public void onFinish(String password) {
        weChatPayWindow.dismiss();
        if (Double.parseDouble(inputEt.getText().toString()) > 1) {
            ToastUtils.showToast("提现成功");
            finish();
        } else {
            myDialog.show();
        }
    }
}
