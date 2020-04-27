package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.adapter.FeedHelpAdapter;
import com.quyum.hyppe.ui.home.bean.FeedBackHelpBean;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FeedHelpActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    FeedHelpAdapter adapter = new FeedHelpAdapter();
    TextView noData;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Feedback & Help");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, FAQHelpActivity.class));
            }
        });
    }

    @Override
    protected void initView() {
        loadingView.setVisibility(View.VISIBLE);
        swipeRefresh.setEnabled(false);
        adapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);
        setData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    void setData() {
        List<FeedBackHelpBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FeedBackHelpBean.DataBean item = new FeedBackHelpBean.DataBean();
            item.title = "张三是不是坏人" + i;
            list.add(item);
        }
        adapter.setNewData(list);
        loadingView.setVisibility(View.GONE);
        adapter.setEnableLoadMore(true);
    }
}
