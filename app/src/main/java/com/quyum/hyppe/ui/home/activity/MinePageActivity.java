package com.quyum.hyppe.ui.home.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.ui.home.fragment.HomePagFragment;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MinePageActivity extends BaseActivity {
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
    @BindView(R.id.icon_iv)
    CircleImageView iconIv;
    @BindView(R.id.hyppers_tv)
    TextView hyppersTv;
    @BindView(R.id.hypping_tv)
    TextView hyppingTv;
    @BindView(R.id.like_tv)
    TextView likeTv;
    @BindView(R.id.edit_iv)
    ImageView editIv;
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
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"Posts221", "Gifts34", "Likes2.0w"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_page;
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

    @OnClick({R.id.back_iv, R.id.icon_iv, R.id.hyppers_ll, R.id.hupping_ll, R.id.edit_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.icon_iv:
                startActivity(new Intent(mContext, MineIconActivity.class));
                break;
            case R.id.hyppers_ll:
                startActivity(new Intent(mContext, FansActivity.class));
                break;
            case R.id.hupping_ll:
                startActivity(new Intent(mContext, FollowActivity.class));
                break;
            case R.id.edit_iv:
                startActivity(new Intent(mContext, EditMineActivity.class));
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
    }
}
