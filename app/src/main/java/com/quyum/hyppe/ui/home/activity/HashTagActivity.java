package com.quyum.hyppe.ui.home.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.qmuiteam.qmui.widget.QMUIAppBarLayout;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.adapter.HomePageAdapter;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.utils.DataUtil;
import com.quyum.hyppe.utils.GridSpacingItemDecoration;
import com.quyum.hyppe.weight.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HashTagActivity extends BaseActivity {
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.top_view_page)
    View topViewPager;
    @BindView(R.id.top_ly)
    RelativeLayout topLy;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    HomePageAdapter adapter = new HomePageAdapter();
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.appbar)
    QMUIAppBarLayout appBarLayout;
    TextView noData;
    int page = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hash_tag;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
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
                startActivity(new Intent(mContext, TikTokActivity.class).putExtra("pos", position));

            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                setData();
            }
        }, recyclerView);


        appBarLayout.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            if (i >= 0) {
                swipeRefresh.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeRefresh.setEnabled(false); //否则关闭
            }
        });
    }

    @Override
    protected void initView() {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);

        topLy.setBackgroundColor(Color.parseColor("#151923"));
        topLy.getBackground().mutate().setAlpha(0);
        titleTv.setAlpha(0);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight1 = -1;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
            }
            ViewGroup.LayoutParams params = topViewPager.getLayoutParams();
            params.height = statusBarHeight1;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            topViewPager.setLayoutParams(params);
        }
        mCollapsingTopBarLayout.setScrimUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i("1", "scrim: " + animation.getAnimatedValue());
                topLy.getBackground().mutate().setAlpha((Integer) animation.getAnimatedValue());
            }
        });
        mCollapsingTopBarLayout.addOnOffsetUpdateListener(new QMUICollapsingTopBarLayout.OnOffsetUpdateListener() {
            @Override
            public void onOffsetChanged(QMUICollapsingTopBarLayout layout, int offset, float expandFraction) {
                Log.i("1", "offset = " + offset + "; expandFraction = " + expandFraction);
                titleTv.setAlpha(expandFraction);
            }
        });
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topLy.getBackground().setAlpha(255);
            }
        });
    }

    @OnClick({R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setData();
    }

    void setData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<TiktokBean> tiktokBeans = DataUtil.getTiktokDataFromAssets(mContext);

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

}
