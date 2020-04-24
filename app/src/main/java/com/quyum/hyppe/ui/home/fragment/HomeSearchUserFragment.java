package com.quyum.hyppe.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.UserBean;
import com.quyum.hyppe.event.HomeSearchEvent;
import com.quyum.hyppe.ui.home.activity.HomeSearchActivity;
import com.quyum.hyppe.ui.home.adapter.HomeSearchUserAdapter;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeSearchUserFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    HomeSearchUserAdapter adapter = new HomeSearchUserAdapter();
    TextView noData;
    int page = 0;

    public static HomeSearchUserFragment newInstance() {
        Bundle args = new Bundle();
        HomeSearchUserFragment fragment = new HomeSearchUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.layout_recycler;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);
        setData();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(HomeSearchEvent homeSearchEvent) {
        if (adapter != null) {
            setData();
        }
    }

    void setData() {
        HomeSearchActivity homeSearchActivity = (HomeSearchActivity) getActivity();
        String searchStr = homeSearchActivity.searchStr;
        if (page == 0) {
            adapter.setEnableLoadMore(false);
        }
        List<UserBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserBean.DataBean item = new UserBean.DataBean();
            item.ui_headurl = "";
            item.isSelect = false;
            item.ui_nickname = searchStr + i;
            item.ui_id = "11" + i;
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

    @Override
    protected void onLazyLoad() {

    }
}
