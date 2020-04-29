package com.quyum.hyppe.ui.wallet.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.quyum.hyppe.weight.PaymentPopWindow;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.payDialog.OnPasswordFinishedListener;
import com.quyum.hyppe.weight.payDialog.WeChatPayWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CreditTopUpActivity extends BaseActivity implements OnPasswordFinishedListener {
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.after_tv)
    TextView afterTv;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    String rate = "0.1";
    PaymentPopWindow paymentPopWindow;
    private WeChatPayWindow weChatPayWindow;
    private MyDialog myDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_top_up;
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

        initPaymentPop();
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
                weChatPayWindow.setPrice(afterTv.getText().toString());
            }
        });
    }

    void initPaymentPop() {
        paymentPopWindow = new PaymentPopWindow(rootLayout, mContext);
        paymentPopWindow.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position < 2) {
                    ToastUtils.showToast("支付成功");
                    finish();
                } else {
                    initWindow();
                    weChatPayWindow.show(rootLayout);
                    weChatPayWindow.setPrice(afterTv.getText().toString());
                }
                paymentPopWindow.close();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void newCard(NewCardEvent newCardEvent) {
        paymentPopWindow.getAdapter().addData(newCardEvent.dataBean);
    }

    void setPopData() {
        List<PaymentBean.DataBean> list = new ArrayList<>();
        PaymentBean.DataBean item = new PaymentBean.DataBean();
        item.drawable = R.drawable.icon_zhifu_weixin;
        item.title = "WeChat Pay";
        list.add(item);
        PaymentBean.DataBean itemTwo = new PaymentBean.DataBean();
        itemTwo.drawable = R.drawable.icon_zhifu_zhifubao;
        itemTwo.title = "Alipay";
        list.add(itemTwo);
        for (int i = 0; i < 5; i++) {
            PaymentBean.DataBean bankItem = new PaymentBean.DataBean();
            bankItem.cardNumber = "438976945769347" + i;
            list.add(bankItem);
        }
        paymentPopWindow.getAdapter().setNewData(list);
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

    @OnClick(R.id.sure_bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(inputEt.getText().toString().trim())) {
            ToastUtils.showToast("请输入USD数量");
            return;
        }
        if (paymentPopWindow.getAdapter().getData().size() > 0) {
            paymentPopWindow.setMoney(afterTv.getText().toString());
            paymentPopWindow.show();
        } else {
            setPopData();
            ToastUtils.showToast("暂未获取到支付信息，请稍后再试");
        }
    }

    @Override
    public void onFinish(String password) {
        weChatPayWindow.dismiss();
        if (Double.parseDouble(afterTv.getText().toString()) > 1) {
            ToastUtils.showToast("支付成功");
            finish();
        } else {
            myDialog.show();
        }
    }
}
