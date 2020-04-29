package com.quyum.hyppe.ui.channels.fragment;

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
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.HomeSearchEvent;
import com.quyum.hyppe.ui.home.activity.TikTokActivity;
import com.quyum.hyppe.ui.channels.adapter.ChannelsHashTagAdapter;
import com.quyum.hyppe.ui.channels.bean.ChannelsHashTagBean;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.utils.DataUtil;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ChannelsHashTagFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    ChannelsHashTagAdapter adapter = new ChannelsHashTagAdapter();
    TextView noData;
    int page = 0;

    public static ChannelsHashTagFragment newInstance() {
        Bundle args = new Bundle();
        ChannelsHashTagFragment fragment = new ChannelsHashTagFragment();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);
        setData();
    }

    @Override
    protected void addListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mActivity, TikTokActivity.class).putExtra("pos", position));

            }
        });
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
        String searchStr = MyApplication.searchStr;
        List<ChannelsHashTagBean.DataBean> list = new ArrayList<>();
        List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(getActivity());
        for (int i = 0; i < 10; i++) {
            ChannelsHashTagBean.DataBean item = new ChannelsHashTagBean.DataBean();
            item.list.addAll(tiktokBeans);
            item.title = searchStr + i;
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
