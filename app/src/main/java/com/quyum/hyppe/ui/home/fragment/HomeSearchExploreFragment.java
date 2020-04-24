package com.quyum.hyppe.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.event.HomeSearchEvent;
import com.quyum.hyppe.ui.home.activity.HomeSearchActivity;
import com.quyum.hyppe.ui.home.activity.TikTokActivity;
import com.quyum.hyppe.ui.home.adapter.HomeSearchExploreAdapter;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.utils.DataUtil;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

public class HomeSearchExploreFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    HomeSearchExploreAdapter adapter = new HomeSearchExploreAdapter();
    TextView noData;
    int page = 0;

    public static HomeSearchExploreFragment newInstance() {
        Bundle args = new Bundle();
        HomeSearchExploreFragment fragment = new HomeSearchExploreFragment();
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
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
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
        HomeSearchActivity homeSearchActivity = (HomeSearchActivity) getActivity();
        String searchStr = homeSearchActivity.searchStr;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(getActivity());

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshFinish();
                        if (page == 0) {
                            adapter.setNewData(tiktokBeans);
                        } else {
                            adapter.addData(tiktokBeans);
                            if (tiktokBeans.size() < 10) {
                                adapter.loadMoreEnd();
                            } else {
                                adapter.loadMoreComplete();
                            }
                        }
                        loadingView.setVisibility(View.GONE);
                        adapter.setEnableLoadMore(true);
                    }
                });
            }
        }).start();
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
