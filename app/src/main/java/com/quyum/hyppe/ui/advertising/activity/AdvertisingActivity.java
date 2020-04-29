package com.quyum.hyppe.ui.advertising.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.advertising.adapter.AdvertisingAdapter;
import com.quyum.hyppe.ui.advertising.bean.AdvertisingBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AdvertisingActivity extends BaseActivity {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    AdvertisingAdapter adapter = new AdvertisingAdapter();
    String distance;
    String area = "", detailed = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertising;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Advertising");
        bar.setRightText("Next");
        bar.getRightText().setTextColor(Color.parseColor("#EE6028"));
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(locationTv.getText().toString().trim())) {
                    ToastUtils.showToast("请选择你的位置");
                    return;
                }
                startActivity(new Intent(mContext, AdvertisingNextActivity.class));
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
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<AdvertisingBean.DataBean> list = adapter.getData();
                for (AdvertisingBean.DataBean item : list) {
                    item.isSelect = false;
                }
                list.get(position).isSelect = true;
                distance = list.get(position).distance;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initView() {
        adapter.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        setData();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    void setData() {
        List<AdvertisingBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AdvertisingBean.DataBean item = new AdvertisingBean.DataBean();
            if (i == 0) {
                item.isSelect = true;
            }
            item.distance = "5" + i + "km";
            list.add(item);
        }
        distance = list.get(0).distance;
        adapter.setNewData(list);
    }

    @OnClick({R.id.location_ll, R.id.problem_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_ll:
                startActivityForResult(new Intent(mContext, AdvertisingLocationActivity.class).putExtra("location", area).putExtra("detailed", detailed), 101);
                break;
            case R.id.problem_rl:
                startActivityForResult(new Intent(mContext, AdvertisingLocationActivity.class).putExtra("location", area).putExtra("detailed", detailed), 101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    area = data.getStringExtra("area");
                    detailed = data.getStringExtra("detailed");
                    locationTv.setText(detailed);
                    break;
            }
        }
    }
}
