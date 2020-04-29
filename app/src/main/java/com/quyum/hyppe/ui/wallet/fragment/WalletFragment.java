package com.quyum.hyppe.ui.wallet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.event.WalletEvent;
import com.quyum.hyppe.ui.wallet.adapter.WalletAdapter;
import com.quyum.hyppe.ui.wallet.bean.WalletBean;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WalletFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;;
    WalletAdapter adapter = new WalletAdapter();
    TextView noData;
    int page = 0;

    public static WalletFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_wallet;
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getData(WalletEvent walletEvent) {
        if (adapter != null) {
            page = 0;
            setData();
        }
    }

    void setData() {
        if (page == 0) {
            adapter.setEnableLoadMore(false);
        }
        List<WalletBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WalletBean.DataBean item = new WalletBean.DataBean();
            item.time = "2020.01.0" + i;
            item.title = "Top Up 4" + i + "Credit";
            list.add(item);
        }
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

    @Override
    protected void onLazyLoad() {

    }
}
