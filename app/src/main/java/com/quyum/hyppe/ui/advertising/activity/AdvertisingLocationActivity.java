package com.quyum.hyppe.ui.advertising.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.quyum.hyppe.base.MyApplication.options1Items;

public class AdvertisingLocationActivity extends BaseActivity {
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.detailed_location_tv)
    TextView detailedLocationTv;
    OptionsPickerView pvOptions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_advertising_next;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Advertising");
        bar.setRightText("Done");
        bar.getRightText().setTextColor(Color.parseColor("#EE6028"));
        bar.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(locationTv.getText().toString())) {
                    ToastUtils.showToast("请选择地区");
                    return;
                }
                if (TextUtils.isEmpty(detailedLocationTv.getText().toString())) {
                    ToastUtils.showToast("请选择具体位置");
                }
                setResult(RESULT_OK, new Intent().putExtra("area", locationTv.getText().toString()).
                        putExtra("detailed", detailedLocationTv.getText().toString()));
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

    }

    @Override
    protected void initView() {
        initPickerView();
    }

    void initPickerView() {
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = MyApplication.options2Items.size() > 0
                        && MyApplication.options2Items.get(options1).size() > 0 ?
                        MyApplication.options2Items.get(options1).get(options2) : "";

                String opt3tx = MyApplication.options2Items.size() > 0
                        && MyApplication.options3Items.get(options1).size() > 0
                        && MyApplication.options3Items.get(options1).get(options2).size() > 0 ?
                        MyApplication.options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx;
                locationTv.setText(tx);
            }
        })

                .setTitleText("Select area")
                .setDividerColor(Color.parseColor("#eeeeee"))
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#333333"))
                .setSubmitColor(Color.parseColor("#EE6028"))
                .setTitleColor(Color.parseColor("#333333"))
                .setTextColorOut(Color.parseColor("#80333333"))
                .setTitleBgColor(Color.parseColor("#ffffff"))
                .setCancelText("cancel")//取消按钮文字
                .setSubmitText("complete")//确认按钮文字
                .setTitleSize(16)
                .setSubCalSize(14)
                .setContentTextSize(16)
                .build();
        pvOptions.setPicker(options1Items, MyApplication.options2Items);//三级选择器
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        locationTv.setText(getIntent().getStringExtra("area"));
        detailedLocationTv.setText(getIntent().getStringExtra("detailed"));
    }

    @OnClick({R.id.location_ll, R.id.detailed_location_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_ll:
                if (MyApplication.isLoaded) {
                    pvOptions.show();
                } else {
                    ToastUtils.showToast("数据加载失败，请稍后重试");
                }
                break;
            case R.id.detailed_location_ll:
                if (TextUtils.isEmpty(locationTv.getText().toString())) {
                    ToastUtils.showToast("请选择地区");
                    return;
                }
                startActivityForResult(new Intent(mContext, SelectLocationAdvertisingActivity.class), 101);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    detailedLocationTv.setText(data.getStringExtra("data"));
                    break;
            }
        }
    }
}
