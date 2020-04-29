package com.quyum.hyppe.ui.daily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.daily.adapter.SignInAddFriendAdapter;
import com.quyum.hyppe.ui.daily.adapter.SignInAddListAdapter;
import com.quyum.hyppe.ui.daily.bean.SignInAddFriendBean;
import com.quyum.hyppe.ui.daily.bean.SignInListBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {
    @BindView(R.id.top_iv)
    ImageView topIv;
    @BindView(R.id.add_rv)
    RecyclerView addRv;
    @BindView(R.id.invite_bt)
    QMUIAlphaButton inviteBt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.folding_ll)
    LinearLayout foldingLl;
    @BindView(R.id.folding_tv)
    TextView foldingTv;
    SignInAddFriendAdapter addFriendAdapter = new SignInAddFriendAdapter();
    SignInAddListAdapter addListAdapter = new SignInAddListAdapter();
    List<SignInListBean.DataBean> allList = new ArrayList<>();
    List<SignInListBean.DataBean> foldList = new ArrayList<>();
    Boolean isFolding = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Daily Sign in");
        bar.setRightImageResource(R.drawable.icon_qiandao_guize);
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SignInRuleActivity.class));
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
        addFriendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SignInAddFriendBean.DataBean> list = adapter.getData();
                if (TextUtils.isEmpty(list.get(position).price)) {
                    ToastUtils.showToast("邀请好友");
                }
            }
        });
    }

    @Override
    protected void initView() {
        addRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        addRv.setAdapter(addFriendAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(addListAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setAddData();
        setListData();
    }

    void setAddData() {
        List<SignInAddFriendBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SignInAddFriendBean.DataBean item = new SignInAddFriendBean.DataBean();
            if (i == 0) {
                item.price = "https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3291638106,1894579552&fm=26&gp=0.jpg";
            }
            list.add(item);
        }
        addFriendAdapter.setNewData(list);
    }

    void setListData() {
        for (int i = 0; i < 10; i++) {
            SignInListBean.DataBean item = new SignInListBean.DataBean();
            item.price = "https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3291638106,1894579552&fm=26&gp=0.jpg";
            if (i / 2 == 0) {
                item.status = true;
            }
            item.title = "Invite Friends" + i;
            item.num = "Up to 8,88" + i + " HP";
            allList.add(item);
        }
        if (allList.size() <= 4) {
            foldingLl.setVisibility(View.GONE);
        } else {
            foldingLl.setVisibility(View.VISIBLE);
            for (int i = 0; i < 4; i++) {
                foldList.add(allList.get(i));
            }
        }
        addListAdapter.setNewData(foldList);
    }

    @OnClick({R.id.invite_bt, R.id.folding_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.invite_bt:
                ToastUtils.showToast("邀请好友");
                break;
            case R.id.folding_ll:
                if (isFolding) {
                    addListAdapter.setNewData(allList);
                    isFolding = false;
                    foldingTv.setText("Pack Up");
                } else {
                    addListAdapter.setNewData(foldList);
                    isFolding = true;
                    foldingTv.setText("All");
                }
                break;
        }
    }
}
