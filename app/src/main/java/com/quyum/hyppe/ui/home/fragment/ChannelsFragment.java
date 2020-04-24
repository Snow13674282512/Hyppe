package com.quyum.hyppe.ui.home.fragment;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.HomeLeftEvent;
import com.quyum.hyppe.event.HomeSearchEvent;
import com.quyum.hyppe.ui.home.adapter.HistorySearchAdapter;
import com.quyum.hyppe.ui.home.adapter.TopSearchAdapter;
import com.quyum.hyppe.ui.home.bean.TopSearchBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChannelsFragment extends BaseFragment {
    @BindView(R.id.channels_search_view)
    View searchView;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.recommended_rv)
    RecyclerView recommendedRv;
    @BindView(R.id.top_rv)
    RecyclerView topRv;
    @BindView(R.id.top_search_ll)
    LinearLayout topSearchLl;
    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    @BindView(R.id.history_search_ll)
    LinearLayout historySearchLl;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.view_pager_ll)
    LinearLayout viewPagerLl;
    TopSearchAdapter recommendedAdapter = new TopSearchAdapter();
    TopSearchAdapter topAdapter = new TopSearchAdapter();
    HistorySearchAdapter historySearchAdapter = new HistorySearchAdapter();
    private List<BaseFragment> fragmentList;
    private String[] tabTitle = {"Hot", "Authenticated", "Likes", "Hashtag", "Popular", "local"};
    TextView noDataRecommended, noDataTop, noDataHistory;

    public static ChannelsFragment newInstance() {
        Bundle args = new Bundle();
        ChannelsFragment fragment = new ChannelsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_channels;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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
        MyApplication.searchStr = "";
        fragmentList = new ArrayList<>();
        fragmentList.add(ChannelsHotFragment.newInstance("0"));
        fragmentList.add(ChannelsHotFragment.newInstance("1"));
        fragmentList.add(ChannelsHotFragment.newInstance("2"));
        fragmentList.add(ChannelsHashTagFragment.newInstance());
        fragmentList.add(ChannelsHotFragment.newInstance("3"));
        fragmentList.add(ChannelsHotFragment.newInstance("4"));
        viewpager.setOffscreenPageLimit(6);
        viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), fragmentList));
        tabLayout.setViewPager(viewpager, tabTitle);

        recommendedRv.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recommendedRv.setAdapter(recommendedAdapter);
        View eRecommendedView = LayoutInflater.from(mActivity).inflate(R.layout.layout_no_data, null);
        noDataRecommended = eRecommendedView.findViewById(R.id.noDataTv);
        recommendedAdapter.setEmptyView(eRecommendedView);

        topRv.setLayoutManager(new GridLayoutManager(mActivity, 2));
        topRv.setAdapter(topAdapter);
        View eTopView = LayoutInflater.from(mActivity).inflate(R.layout.layout_no_data, null);
        noDataTop = eTopView.findViewById(R.id.noDataTv);
        topAdapter.setEmptyView(eTopView);

        historyRv.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));
        historyRv.setAdapter(historySearchAdapter);
        View eHistoryView = LayoutInflater.from(mActivity).inflate(R.layout.layout_no_data, null);
        noDataHistory = eHistoryView.findViewById(R.id.noDataTv);
        historySearchAdapter.setEmptyView(eHistoryView);

        setData();
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
                            historySearchAdapter.setNewData(SystemParams.getInstance().getSearch());
                            topSearchLl.setVisibility(View.GONE);
                            historySearchLl.setVisibility(View.GONE);
                            viewPagerLl.setVisibility(View.VISIBLE);
                            MyApplication.searchStr = searchEt.getText().toString().trim();
                            EventBus.getDefault().post(new HomeSearchEvent());
                            QMUIKeyboardHelper.hideKeyboard(searchView);
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
                } else {
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

        QMUIKeyboardHelper.setVisibilityEventListener(mActivity, new QMUIKeyboardHelper.KeyboardVisibilityEventListener() {
            @Override
            public boolean onVisibilityChanged(boolean isOpen, int heightDiff) {
                if (isOpen) {
                    if (viewPagerLl.getVisibility() == View.GONE) {
                        historySearchLl.setVisibility(View.VISIBLE);
                        topSearchLl.setVisibility(View.GONE);
                    }
                    historySearchAdapter.setNewData(SystemParams.getInstance().getSearch());
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

        topAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = ((TopSearchBean.DataBean) adapter.getData().get(position)).content;
                SystemParams.getInstance().setSearch(s);
                historySearchAdapter.setNewData(SystemParams.getInstance().getSearch());
                topSearchLl.setVisibility(View.GONE);
                historySearchLl.setVisibility(View.GONE);
                viewPagerLl.setVisibility(View.VISIBLE);
                MyApplication.searchStr = s;
                EventBus.getDefault().post(new HomeSearchEvent());
                searchEt.setText(s);
                searchEt.setSelection(s.length());//将光标移至文字末尾
                QMUIKeyboardHelper.hideKeyboard(searchView);
            }
        });

        recommendedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = ((TopSearchBean.DataBean) adapter.getData().get(position)).content;
                SystemParams.getInstance().setSearch(s);
                historySearchAdapter.setNewData(SystemParams.getInstance().getSearch());
                topSearchLl.setVisibility(View.GONE);
                historySearchLl.setVisibility(View.GONE);
                viewPagerLl.setVisibility(View.VISIBLE);
                MyApplication.searchStr = s;
                EventBus.getDefault().post(new HomeSearchEvent());
                searchEt.setText(s);
                searchEt.setSelection(s.length());//将光标移至文字末尾
                QMUIKeyboardHelper.hideKeyboard(searchView);
            }
        });

        historySearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = (String) adapter.getData().get(position);
                SystemParams.getInstance().setSearch(s);
                historySearchAdapter.setNewData(SystemParams.getInstance().getSearch());
                topSearchLl.setVisibility(View.GONE);
                historySearchLl.setVisibility(View.GONE);
                viewPagerLl.setVisibility(View.VISIBLE);
                MyApplication.searchStr = s;
                EventBus.getDefault().post(new HomeSearchEvent());
                searchEt.setText(s);
                searchEt.setSelection(s.length());//将光标移至文字末尾
                QMUIKeyboardHelper.hideKeyboard(searchView);

            }
        });
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
    protected void onLazyLoad() {

    }

    @OnClick({R.id.left_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_iv:
                EventBus.getDefault().post(new HomeLeftEvent());
                break;
        }
    }
}
