package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.adapter.SelectDeviceAdapter;
import com.quyum.hyppe.ui.home.bean.SelectDeviceBean;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectDeviceActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    SelectDeviceAdapter adapter = new SelectDeviceAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Select Device");
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
                List<SelectDeviceBean.DataBean> list = adapter.getData();
                for (SelectDeviceBean.DataBean item : list) {
                    item.isSelect = false;
                }
                list.get(position).isSelect = true;
                adapter.notifyItemChanged(position);
                setResult(RESULT_OK, new Intent().putExtra("data", list.get(position).name));
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        adapter.setEnableLoadMore(false);
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        adapter.setEmptyView(eView);
        setData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    void setData() {
        List<SelectDeviceBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SelectDeviceBean.DataBean item = new SelectDeviceBean.DataBean();
            item.name = "Phone" + i;
            list.add(item);
        }
        adapter.setNewData(list);
        loadingView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("data"))) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).name.equals(getIntent().getStringExtra("data"))) {
                    list.get(i).isSelect = true;
                    adapter.notifyItemChanged(i);
                }
            }
        }
    }
}
