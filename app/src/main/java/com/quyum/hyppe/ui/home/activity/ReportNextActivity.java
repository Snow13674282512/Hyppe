package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportNextActivity extends BaseActivity {
    @BindView(R.id.illegal_tv)
    TextView illegalTv;
    @BindView(R.id.num_tv)
    TextView numTv;
    @BindView(R.id.content_et)
    EditText contentEt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.hint_tv)
    TextView hintTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_next;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
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

    }

    @OnClick(R.id.submit_bt)
    public void onViewClicked() {
    }
}
