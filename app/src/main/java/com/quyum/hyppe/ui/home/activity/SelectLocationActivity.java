package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.adapter.SelectLocationAdapter;
import com.quyum.hyppe.ui.home.bean.SelectLocationBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.utils.UiUtil;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class SelectLocationActivity extends BaseActivity {
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    SelectLocationAdapter adapter = new SelectLocationAdapter();
    TextView noData;
    List<SelectLocationBean.DataBean> list = new ArrayList<>();
    private HashSet<SelectLocationBean.DataBean> infos = new HashSet<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_location;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Add Location");
        return true;
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
                    setData();
                } else {
//                    adapter.setNewData(search(searchEt.getText().toString(), list));
                    infos.clear();
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        SelectLocationBean.DataBean bean = list.get(i);
                        String nikeName = bean.area;
                        if (UiUtil.contain(nikeName, searchEt.getText().toString())) {
                            bean.type = 2;//姓名
                            infos.add(bean);
                            continue;
                        }
                        if (bean.street.contains(searchEt.getText().toString())) {
                            bean.type = 3;//手机
                            infos.add(bean);
                            continue;

                        }
                        String firstSpell = UiUtil.getFirstSpell(nikeName);
                        if (UiUtil.contain(firstSpell, searchEt.getText().toString())) {
                            bean.type = 4;//缩写
                            infos.add(bean);
                            continue;

                        }
                        String pingYin = UiUtil.getPingYin(nikeName);
                        if (UiUtil.contain(pingYin, searchEt.getText().toString())) {
                            bean.type = 5;//全拼
                            infos.add(bean);
                            continue;
                        }
                    }
                    List<SelectLocationBean.DataBean> cList = new ArrayList<>();
                    cList.addAll(infos);
                    adapter.setSearchStr(searchEt.getText().toString().trim());
                    adapter.setNewData(cList);
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<SelectLocationBean.DataBean> list = adapter.getData();
                setResult(RESULT_OK, new Intent().putExtra("data", list.get(position).area + list.get(position).street));
                finish();
            }
        });
    }

    public List search(String name, List list) {
        List results = new ArrayList();
        Pattern pattern = Pattern.compile(name);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher(((SelectLocationBean.DataBean) list.get(i)).area);
            Matcher matcherStreet = pattern.matcher(((SelectLocationBean.DataBean) list.get(i)).street);
            if (matcher.find()) {
                results.add(list.get(i));
            } else if (matcherStreet.find()) {
                results.add(list.get(i));
            }
        }
        return results;
    }

    @Override
    protected void initView() {
        loadingView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        View eView = LayoutInflater.from(mContext).inflate(R.layout.layout_no_data, null);
        noData = eView.findViewById(R.id.noDataTv);
        adapter.setEmptyView(eView);
        setData();
    }

    void setData() {
        List<SelectLocationBean.DataBean> cList = new ArrayList<>();
        list.clear();
        for (int i = 0; i < 10; i++) {
            SelectLocationBean.DataBean item = new SelectLocationBean.DataBean();
            item.area = "ShenYang" + i;
            if (i == 0) {
                item.street = "";
            } else {
                item.street = "xiaodongluasdf154-" + i;
            }
            item.distance = "5" + i + "m";
            cList.add(item);
        }
        list.addAll(cList);
        adapter.setNewData(cList);
        loadingView.setVisibility(View.GONE);
        noData.setText("No result of location");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
