package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.qmuiteam.qmui.alpha.QMUIAlphaButton;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.bean.OwnershipAuthenticationBean;
import com.quyum.hyppe.utils.DateUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OwnershipAuthenticationActivity extends BaseActivity {
    @BindView(R.id.title_et)
    EditText titleEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.select_device_tv)
    TextView selectDeviceTv;
    @BindView(R.id.device_model_tv)
    TextView deviceModelTv;
    @BindView(R.id.screenwriter_tv)
    TextView screenwriterTv;
    @BindView(R.id.cast_tv)
    TextView castTv;
    @BindView(R.id.declaration_tv)
    TextView declarationTv;
    @BindView(R.id.agree_bt)
    QMUIAlphaButton agreeBt;
    TimePickerView timePickerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ownership_authentication;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Ownership Authentication");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(titleEt.getText().toString())) {
                    agreeBt.setBackgroundResource(R.drawable.bg_button_save);
                } else {
                    if (!TextUtils.isEmpty(nameEt.getText().toString()) && !TextUtils.isEmpty(locationTv.getText().toString()) && !TextUtils.isEmpty(timeTv.getText().toString())) {
                        agreeBt.setBackgroundResource(R.drawable.bg_button_big);
                    }
                }
            }
        });
        timeTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(timeTv.getText().toString())) {
                    agreeBt.setBackgroundResource(R.drawable.bg_button_save);
                } else {
                    if (!TextUtils.isEmpty(nameEt.getText().toString()) && !TextUtils.isEmpty(locationTv.getText().toString()) && !TextUtils.isEmpty(titleEt.getText().toString())) {
                        agreeBt.setBackgroundResource(R.drawable.bg_button_big);
                    }
                }
            }
        });
        locationTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(locationTv.getText().toString())) {
                    agreeBt.setBackgroundResource(R.drawable.bg_button_save);
                } else {
                    if (!TextUtils.isEmpty(nameEt.getText().toString()) && !TextUtils.isEmpty(timeTv.getText().toString()) && !TextUtils.isEmpty(titleEt.getText().toString())) {
                        agreeBt.setBackgroundResource(R.drawable.bg_button_big);
                    }
                }
            }
        });
        nameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(nameEt.getText().toString())) {
                    agreeBt.setBackgroundResource(R.drawable.bg_button_save);
                } else {
                    if (!TextUtils.isEmpty(timeTv.getText().toString()) && !TextUtils.isEmpty(locationTv.getText().toString()) && !TextUtils.isEmpty(titleEt.getText().toString())) {
                        agreeBt.setBackgroundResource(R.drawable.bg_button_big);
                    }
                }
            }
        });
    }

    @Override
    protected void initView() {
        intiTimePicker();
    }

    void intiTimePicker() {
        List<Boolean> list = new ArrayList<>();
        list.add(true);
        list.add(true);
        list.add(true);
        list.add(false);
        list.add(false);
        list.add(false);
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Calendar currDate = Calendar.getInstance();

        Calendar calendars = Calendar.getInstance();
        String year = String.valueOf(calendars.get(Calendar.YEAR));
        String month = String.valueOf(calendars.get(Calendar.MONTH));
        String day = String.valueOf(calendars.get(Calendar.DATE));

        //正确设置方式 原因：注意事项有说明
        startDate.set(1900, 0, 1);
        endDate.set(calendars.get(Calendar.YEAR), calendars.get(Calendar.MONTH), calendars.get(Calendar.DATE));
        currDate.set(calendars.get(Calendar.YEAR), calendars.get(Calendar.MONTH), calendars.get(Calendar.DATE));

        timePickerView = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                timeTv.setText(DateUtils.getTime(date));
            }
        })
                .setType(new boolean[]{list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)})// 默认全部显示
                .setTitleText("Time of Content Created")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setDate(currDate)
                .setRangDate(startDate, endDate)//起始终止年月日设定
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
                .setLabel("", "", "", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        OwnershipAuthenticationBean bean = (OwnershipAuthenticationBean) getIntent().getSerializableExtra("data");
        titleEt.setText(bean.title);
        nameEt.setText(bean.name);
        locationTv.setText(bean.location);
        timeTv.setText(bean.time);
        if (!TextUtils.isEmpty(titleEt.getText().toString().trim()) && !TextUtils.isEmpty(nameEt.getText().toString().trim()) && !TextUtils.isEmpty(locationTv.getText().toString()) && !
                TextUtils.isEmpty(timeTv.getText().toString().trim())) {
            agreeBt.setBackgroundResource(R.drawable.bg_button_big);
        } else {
            agreeBt.setBackgroundResource(R.drawable.bg_button_save);
        }
        selectDeviceTv.setText(bean.device);
        deviceModelTv.setText(bean.deviceModel);
        screenwriterTv.setText(bean.screenwriter);
        castTv.setText(bean.cast);
    }

    @OnClick({R.id.location_ll, R.id.time_ll, R.id.select_device_ll, R.id.device_model_ll, R.id.screenwriter_ll, R.id.cast_ll, R.id.discard_bt, R.id.agree_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location_ll:
                startActivityForResult(new Intent(mContext, SelectLocationActivity.class), 101);
                break;
            case R.id.time_ll:
                timePickerView.show();
                break;
            case R.id.select_device_ll:
                startActivityForResult(new Intent(mContext, SelectDeviceActivity.class).putExtra("data", selectDeviceTv.getText().toString()), 102);
                break;
            case R.id.device_model_ll:
                startActivityForResult(new Intent(mContext, DeviceModelActivity.class).putExtra("data", deviceModelTv.getText().toString()), 103);
                break;
            case R.id.screenwriter_ll:
                startActivityForResult(new Intent(mContext, ScreenWriterActivity.class).putExtra("data", screenwriterTv.getText().toString()), 104);
                break;
            case R.id.cast_ll:
                startActivityForResult(new Intent(mContext, CastActivity.class).putExtra("data", castTv.getText().toString()), 105);
                break;
            case R.id.discard_bt:
                finish();
                break;
            case R.id.agree_bt:
                setResult(RESULT_OK, new Intent().putExtra("data", new OwnershipAuthenticationBean(titleEt.getText().toString().trim(), nameEt.getText().toString().trim(),
                        locationTv.getText().toString().trim(), timeTv.getText().toString().trim(), selectDeviceTv.getText().toString().trim(),
                        deviceModelTv.getText().toString().trim(), screenwriterTv.getText().toString().trim(), castTv.getText().toString().trim())));
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    locationTv.setText(data.getStringExtra("data"));
                    break;
                case 102:
                    selectDeviceTv.setText(data.getStringExtra("data"));
                    break;
                case 103:
                    deviceModelTv.setText(data.getStringExtra("data"));
                    break;
                case 104:
                    screenwriterTv.setText(data.getStringExtra("data"));
                    break;
                case 105:
                    castTv.setText(data.getStringExtra("data"));
                    break;
            }
        }
    }
}
