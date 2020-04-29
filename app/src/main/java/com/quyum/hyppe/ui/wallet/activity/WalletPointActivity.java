package com.quyum.hyppe.ui.wallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletPointActivity extends BaseActivity {
    @BindView(R.id.balance_tv)
    TextView balanceTv;
    @BindView(R.id.info_tv)
    TextView infoTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_point;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Point");
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
        balanceTv.setText(CommonUtil.setScale("116485.15132165", 2));
    }

    @OnClick({R.id.top_up_bt, R.id.withdrawal_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_up_bt:
                startActivity(new Intent(mContext, PointTopUpActivity.class));
                break;
            case R.id.withdrawal_bt:
                startActivity(new Intent(mContext, PointConversionActivity.class));
                break;
        }
    }
}
