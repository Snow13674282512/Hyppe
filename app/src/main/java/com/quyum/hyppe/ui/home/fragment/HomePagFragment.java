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
import com.quyum.hyppe.ui.home.activity.TikTokActivity;
import com.quyum.hyppe.ui.home.adapter.HomePageAdapter;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.utils.DataUtil;
import com.quyum.hyppe.utils.GridSpacingItemDecoration;
import com.quyum.hyppe.weight.TitleBar;

import java.util.List;

import butterknife.BindView;

public class HomePagFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    HomePageAdapter adapter = new HomePageAdapter();
    TextView noData;
    int page = 0;

    public static HomePagFragment newInstance(String pos) {
        Bundle args = new Bundle();
        args.putString("pos", pos);
        HomePagFragment fragment = new HomePagFragment();
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
        setData();
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        loadingView.setVisibility(View.VISIBLE);
        swipeRefresh.setEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);
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
    }

    void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(getActivity());

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
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

    @Override
    protected void onLazyLoad() {

    }
}
