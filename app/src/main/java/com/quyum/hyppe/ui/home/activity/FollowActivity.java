package com.quyum.hyppe.ui.home.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.bean.FansBean;
import com.quyum.hyppe.ui.mine.adapter.MineFansAdapter;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.weight.TitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FollowActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    SwipeRecyclerView recyclerView;
    MineFansAdapter adapter = new MineFansAdapter();
    int page = 0;
    TextView noData;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Hypping");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                setData();
            }
        }, recyclerView);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                setData();
            }
        });
    }

    @Override
    protected void initView() {
        loadingView.setVisibility(View.VISIBLE);
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
        if (page == 0) {
            adapter.setEnableLoadMore(false);
        }
        List<FansBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FansBean.DataBean item = new FansBean.DataBean();
            item.name = "李四" + i;
            item.price = "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1052131196,1689306403&fm=26&gp=0.jpg";
            list.add(item);
        }
        refreshFinish();
        if (page == 0) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);
            if (list.size() < 10) {
                adapter.loadMoreEnd();
            } else {
                adapter.loadMoreComplete();
            }
        }
        loadingView.setVisibility(View.GONE);
        adapter.setEnableLoadMore(true);
    }

    private void refreshFinish() {
        if (swipeRefresh != null && swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }
}
