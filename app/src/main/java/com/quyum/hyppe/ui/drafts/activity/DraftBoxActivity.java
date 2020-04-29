package com.quyum.hyppe.ui.drafts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.release.activity.ReleaseActivity;
import com.quyum.hyppe.ui.drafts.adapter.DraftBoxAdapter;
import com.quyum.hyppe.ui.drafts.bean.DraftBoxBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DraftBoxActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.delete_bt)
    QMUIAlphaButton deleteBt;
    DraftBoxAdapter adapter = new DraftBoxAdapter();
    int page = 0;
    TextView noData;
    Boolean isOpen = false;
    String deleteId = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_draft_box;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Draft Box");
        bar.setRightText("Select");
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    for (DraftBoxBean.DataBean item : adapter.getData()) {
                        item.isOpen = false;
                        item.isSelect = false;
                    }
                    isOpen = false;
                    bar.setRightText("Select");
                    deleteBt.setVisibility(View.GONE);
                    adapter.setEnableLoadMore(true);
                    swipeRefresh.setEnabled(true);
                    adapter.notifyDataSetChanged();
                } else {
                    if (adapter.getData().size() > 0) {
                        for (DraftBoxBean.DataBean item : adapter.getData()) {
                            item.isOpen = true;
                        }
                        isOpen = true;
                        bar.setRightText("Cancel");
                        deleteBt.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        swipeRefresh.setEnabled(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ReleaseActivity.class));
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
        List<DraftBoxBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DraftBoxBean.DataBean item = new DraftBoxBean.DataBean();
            item.title = "The title is empty" + i;
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

    @OnClick(R.id.delete_bt)
    public void onViewClicked() {
        deleteId = "";
        List<DraftBoxBean.DataBean> list = new ArrayList<>();
        list.addAll(adapter.getData());
        List<String> temporaryList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).isSelect) {
                temporaryList.add(list.get(i).title);
                adapter.remove(i);
            }
        }
        StringBuffer str2 = new StringBuffer();
        for (Iterator<String> iterator = temporaryList.iterator(); iterator.hasNext(); ) {
            String string = iterator.next();
            str2.append(string);
            if (iterator.hasNext()) {
                str2.append(",");
            }
        }
        deleteId = str2.toString();
        ToastUtils.showToast("成功删除" + deleteId);
        if (adapter.getData().size() == 0) {
            deleteBt.setVisibility(View.GONE);
            mTitleBar.setRightText("Select");
            isOpen = false;
            adapter.setEnableLoadMore(true);
            swipeRefresh.setEnabled(true);
        }
    }
}
