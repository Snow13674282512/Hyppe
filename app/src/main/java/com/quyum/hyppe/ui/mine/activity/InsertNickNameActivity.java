package com.quyum.hyppe.ui.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class InsertNickNameActivity extends BaseActivity {
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.num_tv)
    TextView numTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_insert_nickname;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Insert Nickname");
        bar.setRightText("Done");
        bar.getRightText().setTextColor(Color.parseColor("#EE6028"));
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent().putExtra("data", nameEt.getText().toString().trim()));
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
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numTv.setText(nameEt.getText().length() + "/12");
            }
        });
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        nameEt.setText(getIntent().getStringExtra("data"));
    }

    @OnClick(R.id.delete_iv)
    public void onViewClicked() {
    }
}
