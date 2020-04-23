package com.quyum.hyppe.ui.home.activity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.HomeSearchEvent;
import com.quyum.hyppe.ui.home.adapter.HistorySearchAdapter;
import com.quyum.hyppe.ui.home.adapter.TopSearchAdapter;
import com.quyum.hyppe.ui.home.bean.TopSearchBean;
import com.quyum.hyppe.ui.home.fragment.HomeSearchExploreFragment;
import com.quyum.hyppe.ui.home.fragment.HomeSearchUserFragment;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity {
    @BindView(R.id.recommended_rv)
    RecyclerView recommendedRv;
    @BindView(R.id.top_rv)
    RecyclerView topRv;
    @BindView(R.id.top_search_ll)
    LinearLayout topSearchLl;
    TopSearchAdapter recommendedAdapter = new TopSearchAdapter();
    TopSearchAdapter topAdapter = new TopSearchAdapter();
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.search_tv)
    TextView searchTv;
    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    @BindView(R.id.history_search_ll)
    LinearLayout historySearchLl;
    HistorySearchAdapter historySearchAdapter = new HistorySearchAdapter();
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.view_pager_ll)
    LinearLayout viewPagerLl;
    @BindView(R.id.home_search_view)
    View searchView;
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"Explore", "User"};
    TextView noDataRecommended, noDataTop, noDataHistory;
    public String searchStr = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
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
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        if (TextUtils.isEmpty(searchEt.getText().toString())) {
                            ToastUtils.showToast("请输入输入内容");
                            return true;
                        } else {
                            SystemParams.getInstance().setSearch(searchEt.getText().toString().trim());
                            ArrayList<String> list = SystemParams.getInstance().getSearch();
                            Collections.reverse(list);
                            historySearchAdapter.setNewData(list);
                            topSearchLl.setVisibility(View.GONE);
                            historySearchLl.setVisibility(View.GONE);
                            viewPagerLl.setVisibility(View.VISIBLE);
                            searchStr = searchEt.getText().toString().trim();
                            EventBus.getDefault().post(new HomeSearchEvent());
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(searchEt.getText().toString())) {
                    topSearchLl.setVisibility(View.VISIBLE);
                    viewPagerLl.setVisibility(View.GONE);
                    historySearchLl.setVisibility(View.GONE);
                    searchTv.setText("Cancel");
                } else {
                    searchTv.setText("Search");
                    if (viewPagerLl.getVisibility() == View.GONE) {
                        historySearchLl.setVisibility(View.VISIBLE);
                        topSearchLl.setVisibility(View.GONE);
                    }
                }
            }
        });
        historySearchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> list = adapter.getData();
                SystemParams.getInstance().removeSearch(list.get(position));
                adapter.remove(position);
                if (adapter.getData().size() == 0) {
                    historySearchLl.setVisibility(View.GONE);
                    topSearchLl.setVisibility(View.VISIBLE);
                }
            }
        });

        QMUIKeyboardHelper.setVisibilityEventListener(mContext, new QMUIKeyboardHelper.KeyboardVisibilityEventListener() {
            @Override
            public boolean onVisibilityChanged(boolean isOpen, int heightDiff) {
                if (isOpen) {
                    if (viewPagerLl.getVisibility() == View.GONE) {
                        historySearchLl.setVisibility(View.VISIBLE);
                        topSearchLl.setVisibility(View.GONE);
                    }
                    ArrayList<String> list = SystemParams.getInstance().getSearch();
                    Collections.reverse(list);
                    historySearchAdapter.setNewData(list);
                }
//                else {
//                    historySearchLl.setVisibility(View.GONE);
//                    if (viewPagerLl.getVisibility() != View.VISIBLE) {
//                        topSearchLl.setVisibility(View.VISIBLE);
//                    }
//                }
                return false;
            }
        });
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight1 = -1;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
            }
            ViewGroup.LayoutParams params = searchView.getLayoutParams();
            params.height = statusBarHeight1;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            searchView.setLayoutParams(params);
        }
        fragmentList = new ArrayList<>();
        fragmentList.add(HomeSearchExploreFragment.newInstance());
        fragmentList.add(HomeSearchUserFragment.newInstance());
        viewpager.setOffscreenPageLimit(2);
        viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewpager, tabTitle);

        recommendedRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        recommendedRv.setAdapter(recommendedAdapter);
        View eRecommendedView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noDataRecommended = eRecommendedView.findViewById(R.id.noDataTv);
        recommendedAdapter.setEmptyView(eRecommendedView);

        topRv.setLayoutManager(new GridLayoutManager(mContext, 2));
        topRv.setAdapter(topAdapter);
        View eTopView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noDataTop = eTopView.findViewById(R.id.noDataTv);
        topAdapter.setEmptyView(eTopView);

        historyRv.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        historyRv.setAdapter(historySearchAdapter);
        View eHistoryView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noDataHistory = eHistoryView.findViewById(R.id.noDataTv);
        historySearchAdapter.setEmptyView(eHistoryView);

        setData();
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

    void setData() {
        List<TopSearchBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TopSearchBean.DataBean item = new TopSearchBean.DataBean();
            item.content = "sdf" + i;
            list.add(item);
        }
        recommendedAdapter.setNewData(list);
        topAdapter.setNewData(list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @OnClick(R.id.search_tv)
    public void onViewClicked() {
        topSearchLl.setVisibility(View.GONE);
        historySearchLl.setVisibility(View.GONE);
        viewPagerLl.setVisibility(View.VISIBLE);
        SystemParams.getInstance().setSearch(searchEt.getText().toString().trim());
        ArrayList<String> list = SystemParams.getInstance().getSearch();
        Collections.reverse(list);
        historySearchAdapter.setNewData(list);
        searchStr = searchEt.getText().toString().trim();
        EventBus.getDefault().post(new HomeSearchEvent());
    }

}
