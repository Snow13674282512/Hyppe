package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.adapter.ReportAdapter;
import com.quyum.hyppe.ui.home.bean.ReportBean;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ReportActivity extends BaseActivity {
    @BindView(R.id.content_rv)
    RecyclerView contentRv;
    @BindView(R.id.account_rv)
    RecyclerView accountRv;
    @BindView(R.id.infringement_rv)
    RecyclerView infringementRv;
    @BindView(R.id.other_rv)
    RecyclerView otherRv;
    ReportAdapter contentAdapter = new ReportAdapter();
    ReportAdapter accountAdapter = new ReportAdapter();
    ReportAdapter infringementAdapter = new ReportAdapter();
    ReportAdapter otherAdapter = new ReportAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Report");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        contentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReportBean.DataBean> list = adapter.getData();
                startActivity(new Intent(mContext, ReportNextActivity.class).putExtra("data", list.get(position).content));
            }
        });
        accountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReportBean.DataBean> list = adapter.getData();
                startActivity(new Intent(mContext, ReportNextActivity.class).putExtra("data", list.get(position).content));
            }
        });
        infringementAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReportBean.DataBean> list = adapter.getData();
                startActivity(new Intent(mContext, ReportNextActivity.class).putExtra("data", list.get(position).content));
            }
        });
        otherAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ReportBean.DataBean> list = adapter.getData();
                startActivity(new Intent(mContext, ReportNextActivity.class).putExtra("data", list.get(position).content));
            }
        });
    }

    @Override
    protected void initView() {
        contentRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        contentRv.setAdapter(contentAdapter);

        accountRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        accountRv.setAdapter(accountAdapter);

        infringementRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        infringementRv.setAdapter(infringementAdapter);

        otherRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        otherRv.setAdapter(otherAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getData();
    }

    void getData() {
        List<ReportBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReportBean.DataBean item = new ReportBean.DataBean();
            item.content = "内容" + i;
            list.add(item);
        }
        contentAdapter.setNewData(list);
        accountAdapter.setNewData(list);
        infringementAdapter.setNewData(list);
        otherAdapter.setNewData(list);
    }
}
