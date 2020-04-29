package com.quyum.hyppe.ui.advertising.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.advertising.adapter.AdvertisingConditionsAdapter;
import com.quyum.hyppe.ui.advertising.bean.AdvertisingConditionsBean;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AdvertisingNextActivity extends BaseActivity {
    @BindView(R.id.cover_iv)
    ImageView coverIv;
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.public_iv)
    ImageView publicIv;
    @BindView(R.id.gender_tv)
    TextView genderTv;
    @BindView(R.id.gender_rv)
    RecyclerView genderRv;
    @BindView(R.id.age_tv)
    TextView ageTv;
    @BindView(R.id.age_rv)
    RecyclerView ageRv;
    AdvertisingConditionsAdapter genderAdapter = new AdvertisingConditionsAdapter();
    AdvertisingConditionsAdapter ageAdapter = new AdvertisingConditionsAdapter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertising_third;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Advertising");
        bar.setRightText("Next");
        bar.getRightText().setTextColor(Color.parseColor("#EE6028"));
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BudGetTimeActivity.class));
                finish();
            }
        });
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        genderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AdvertisingConditionsBean.DataBean> genderList = adapter.getData();
                List<AdvertisingConditionsBean.DataBean> ageList = ageAdapter.getData();
                for (AdvertisingConditionsBean.DataBean item : genderList) {
                    item.isSelect = false;
                }
                genderList.get(position).isSelect = true;
                adapter.notifyDataSetChanged();
                if (position != 0) {
                    publicIv.setVisibility(View.GONE);
                }
                if (genderList.get(0).isSelect && ageList.get(0).isSelect) {
                    publicIv.setVisibility(View.VISIBLE);
                }

            }
        });
        ageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AdvertisingConditionsBean.DataBean> ageList = adapter.getData();
                List<AdvertisingConditionsBean.DataBean> genderList = genderAdapter.getData();
                for (AdvertisingConditionsBean.DataBean item : ageList) {
                    item.isSelect = false;
                }
                ageList.get(position).isSelect = true;
                adapter.notifyDataSetChanged();
                if (position != 0) {
                    publicIv.setVisibility(View.GONE);
                }
                if (genderList.get(0).isSelect = true && ageList.get(0).isSelect) {
                    publicIv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initView() {
        genderRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        genderRv.setAdapter(genderAdapter);
        ageRv.setLayoutManager(new GridLayoutManager(mContext, 4));
        ageRv.setAdapter(ageAdapter);
        setGenderData();
        setAgeData();
    }

    void setGenderData() {
        List<AdvertisingConditionsBean.DataBean> list = new ArrayList<>();
        AdvertisingConditionsBean.DataBean oneBean = new AdvertisingConditionsBean.DataBean();
        oneBean.name = "Unlimited";
        oneBean.isSelect = true;
        AdvertisingConditionsBean.DataBean twoBean = new AdvertisingConditionsBean.DataBean();
        twoBean.name = "Man";
        AdvertisingConditionsBean.DataBean threeBean = new AdvertisingConditionsBean.DataBean();
        threeBean.name = "Female";
        list.add(oneBean);
        list.add(twoBean);
        list.add(threeBean);
        genderAdapter.setNewData(list);
    }

    void setAgeData() {
        List<AdvertisingConditionsBean.DataBean> list = new ArrayList<>();
        AdvertisingConditionsBean.DataBean oneBean = new AdvertisingConditionsBean.DataBean();
        oneBean.name = "Unlimited";
        oneBean.isSelect = true;
        AdvertisingConditionsBean.DataBean twoBean = new AdvertisingConditionsBean.DataBean();
        twoBean.name = "18～23";
        AdvertisingConditionsBean.DataBean threeBean = new AdvertisingConditionsBean.DataBean();
        threeBean.name = "24～40";
        AdvertisingConditionsBean.DataBean fourBean = new AdvertisingConditionsBean.DataBean();
        fourBean.name = "40+";
        list.add(oneBean);
        list.add(twoBean);
        list.add(threeBean);
        list.add(fourBean);
        ageAdapter.setNewData(list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.cover_iv, R.id.system_ll, R.id.problem_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cover_iv:
                break;
            case R.id.system_ll:
                system();
                break;
            case R.id.problem_rl:
                startActivity(new Intent(mContext, AdvertisingProblemActivity.class));
                break;
        }
    }

    void system() {
        publicIv.setVisibility(View.VISIBLE);
        List<AdvertisingConditionsBean.DataBean> list = genderAdapter.getData();
        for (AdvertisingConditionsBean.DataBean item : list) {
            item.isSelect = false;
        }
        list.get(0).isSelect = true;
        genderAdapter.notifyDataSetChanged();

        List<AdvertisingConditionsBean.DataBean> list1 = ageAdapter.getData();
        for (AdvertisingConditionsBean.DataBean item : list1) {
            item.isSelect = false;
        }
        list.get(0).isSelect = true;
        ageAdapter.notifyDataSetChanged();
    }
}
