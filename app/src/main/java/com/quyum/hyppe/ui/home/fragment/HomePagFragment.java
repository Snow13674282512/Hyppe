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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.quyum.hyppe.R;
import com.quyum.hyppe.api.APPApi;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.net.ApiSubscriber;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.ui.home.activity.TikTokActivity;
import com.quyum.hyppe.ui.home.adapter.HomePageAdapter;
import com.quyum.hyppe.ui.home.bean.ReportBean;
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

    public static HomePagFragment newInstance(String pos) {
        Bundle args = new Bundle();
        args.putString("pos", pos);
        HomePagFragment fragment = new HomePagFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.recycler_layout;
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
    }

    void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(getActivity());

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(tiktokBeans);
                        loadingView.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onLazyLoad() {

    }
}
