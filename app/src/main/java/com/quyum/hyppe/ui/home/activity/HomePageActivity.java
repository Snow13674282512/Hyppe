package com.quyum.hyppe.ui.home.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.ui.home.adapter.HomePageRecommendAdapter;
import com.quyum.hyppe.ui.home.bean.HomePageRecommendBean;
import com.quyum.hyppe.ui.home.fragment.HomePagFragment;
import com.quyum.hyppe.ui.mine.activity.MineIconActivity;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomePageActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.top_view_page)
    View topViewPager;
    @BindView(R.id.top_ly)
    RelativeLayout topLy;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.facus_ll)
    LinearLayout facusLl;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    HomePageRecommendAdapter adapter = new HomePageRecommendAdapter();
    @BindView(R.id.follow_bt)
    Button followBt;
    @BindView(R.id.icon_iv)
    CircleImageView iconIv;
    @BindView(R.id.hyppers_tv)
    TextView hyppersTv;
    @BindView(R.id.hupping_tv)
    TextView huppingTv;
    @BindView(R.id.like_tv)
    TextView likeTv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.vip_iv)
    ImageView vipIv;
    @BindView(R.id.info_tv)
    TextView infoTv;
    @BindView(R.id.sex_iv)
    ImageView sexIv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.age_tv)
    TextView ageTv;
    @BindView(R.id.back_iv)
    ImageView backIv;
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"Posts221", "Gifts34", "Likes2.0w"};
    Boolean isVis = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_page;
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

    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

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
        fragmentList = new ArrayList<>();
        fragmentList.add(HomePagFragment.newInstance("0"));
        fragmentList.add(HomePagFragment.newInstance("1"));
        fragmentList.add(HomePagFragment.newInstance("2"));
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewpager, tabTitle);
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

    @OnClick({R.id.back_iv, R.id.icon_iv, R.id.hypper_ll, R.id.hypping_ll, R.id.focus_iv, R.id.recommended_iv, R.id.follow_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hypper_ll:
                startActivity(new Intent(mContext, FansActivity.class));
                break;
            case R.id.hypping_ll:
                startActivity(new Intent(mContext, FollowActivity.class));
                break;
            case R.id.focus_iv:
                facusLl.setVisibility(View.GONE);
                followBt.setVisibility(View.VISIBLE);
                break;
            case R.id.recommended_iv:
                if (isVis) {
                    recyclerView.setVisibility(View.GONE);
                    isVis = false;
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    isVis = true;
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.icon_iv:
                startActivity(new Intent(mContext, MineIconActivity.class).putExtra("isShowUpDate", false));
                break;
            case R.id.follow_bt:
                facusLl.setVisibility(View.VISIBLE);
                followBt.setVisibility(View.GONE);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<BaseFragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
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
        List<HomePageRecommendBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HomePageRecommendBean.DataBean item = new HomePageRecommendBean.DataBean();
            item.isFollow = false;
            item.drawable = R.drawable.default_icon;
            item.content = "You May Interested";
            item.title = "Joker" + i;
            list.add(item);
        }
        adapter.setNewData(list);
    }

}
