package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.qmuiteam.qmui.alpha.QMUIAlphaTextView;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.event.NewCardEvent;
import com.quyum.hyppe.ui.home.bean.OwnershipAuthenticationBean;
import com.quyum.hyppe.ui.home.bean.PaymentBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.PaymentPopWindow;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.payDialog.OnPasswordFinishedListener;
import com.quyum.hyppe.weight.payDialog.WeChatPayWindow;
import com.suke.widget.SwitchButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ReleaseActivity extends BaseActivity implements OnPasswordFinishedListener {
    @BindView(R.id.cover_iv)
    ImageView coverIv;
    @BindView(R.id.topic_tv)
    QMUIAlphaTextView topicTv;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.privacy_tv)
    TextView privacyTv;
    @BindView(R.id.save_camera_sb)
    SwitchButton saveCameraSb;
    @BindView(R.id.download_sb)
    SwitchButton downloadSb;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.root_layout)
    LinearLayout rootLayout;
    @BindView(R.id.save_bt)
    QMUIAlphaButton saveBt;
    String locationStr = "";
    String type = "0";
    OwnershipAuthenticationBean bean = new OwnershipAuthenticationBean("", "", "", "", "", "", "", "");
    PaymentPopWindow paymentPopWindow;
    private WeChatPayWindow weChatPayWindow;
    private MyDialog myDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Release");
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
                weChatPayWindow.setPrice(priceTv.getText().toString());
            }
        });
    }

    void initPaymentPop() {
        paymentPopWindow = new PaymentPopWindow(rootLayout, mContext);
        paymentPopWindow.getAdapter().setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position < 2) {
                    ToastUtils.showToast("发布成功");
                    finish();
                } else {
                    initWindow();
                    weChatPayWindow.show(rootLayout);
                    weChatPayWindow.setPrice(priceTv.getText().toString());
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

    @OnClick({R.id.select_img_rl, R.id.topic_tv, R.id.location_ll, R.id.privacy_ll, R.id.ownership_ll, R.id.save_bt, R.id.next_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_img_rl:
                break;
            case R.id.topic_tv:
                break;
            case R.id.location_ll:
                startActivityForResult(new Intent(mContext, SelectLocationActivity.class), 101);
                break;
            case R.id.privacy_ll:
                startActivityForResult(new Intent(mContext, PrivacySettingActivity.class).putExtra("data", type), 102);
                break;
            case R.id.ownership_ll:
                startActivityForResult(new Intent(mContext, OwnershipAuthenticationActivity.class).putExtra("data", bean), 103);
                break;
            case R.id.save_bt:
                break;
            case R.id.next_bt:
                if (TextUtils.isEmpty(bean.name)) {
                    ToastUtils.showToast("发布成功");
                    finish();
                } else {
                    if (paymentPopWindow.getAdapter().getData().size() > 0) {
                        paymentPopWindow.setMoney(priceTv.getText().toString());
                        paymentPopWindow.show();
                    } else {
                        setPopData();
                        ToastUtils.showToast("暂未获取到支付信息，请稍后再试");
                    }
                }
                break;
        }
    }

    @Override
    public void onFinish(String password) {
        weChatPayWindow.dismiss();
        if (Double.parseDouble(privacyTv.getText().toString()) > 1) {
            ToastUtils.showToast("发布成功");
            finish();
        } else {
            myDialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    locationStr = data.getStringExtra("data");
                    break;
                case 102:
                    type = data.getStringExtra("data");
                    switch (type) {
                        case "0":
                            privacyTv.setText("public");
                            break;
                        case "1":
                            privacyTv.setText("friends");
                            break;
                        case "2":
                            privacyTv.setText("only me");
                            break;
                    }
                    break;
                case 103:
                    bean = (OwnershipAuthenticationBean) data.getSerializableExtra("data");
                    if(TextUtils.isEmpty(bean.name)){
                        priceTv.setText("0.0");
                    }else {
                        priceTv.setText("125.1");
                    }
                    break;
            }
        }
    }
}
