package com.quyum.hyppe.ui.mine.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.release.activity.BioActivity;
import com.quyum.hyppe.utils.GlideEngine;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.SexPopWindow;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.quyum.hyppe.base.MyApplication.options1Items;

public class EditMineActivity extends BaseActivity {
    @BindView(R.id.icon_iv)
    CircleImageView iconIv;
    @BindView(R.id.nick_name_tv)
    TextView nickNameTv;
    @BindView(R.id.sex_iv)
    ImageView sexIv;
    @BindView(R.id.sex_tv)
    TextView sexTv;
    @BindView(R.id.location_tv)
    TextView locationTv;
    @BindView(R.id.bio_tv)
    TextView bioTv;
    String url;
    SexPopWindow sexPopWindow;
    OptionsPickerView pvOptions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_mine;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Edit data");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        sexTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(sexTv.getText().toString())) {
                    sexTv.setHint("Gender");
                } else {
                    sexTv.setHint("");
                }
            }
        });
    }

    @Override
    protected void initView() {
        initSexPop();
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

    void initSexPop() {
        sexPopWindow = new SexPopWindow(mContentRootView, mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexIv.setImageResource(R.drawable.icon_wodeziliao_xingbie_n);
                sexTv.setText("Female");
                sexPopWindow.close();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sexIv.setImageResource(R.drawable.icon_tarenzhuye_xingbie);
                sexTv.setText("Male");
                sexPopWindow.close();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.icon_ll, R.id.nick_name_ll, R.id.sex_ll, R.id.location_ll, R.id.bio_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.icon_ll:
                EasyPhotos.createAlbum(mContext, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .setCount(1)
                        .setOriginalMenu(true, true, null)
                        .start(101);
                break;
            case R.id.nick_name_ll:
                startActivityForResult(new Intent(mContext, InsertNickNameActivity.class).putExtra("data", nickNameTv.getText().toString()), 102);
                break;
            case R.id.sex_ll:
                sexPopWindow.show();
                break;
            case R.id.location_ll:
                if (MyApplication.isLoaded) {
                    pvOptions.show();
                } else {
                    ToastUtils.showToast("数据加载失败，请稍后重试");
                }
                break;
            case R.id.bio_ll:
                startActivityForResult(new Intent(mContext, BioActivity.class).putExtra("data", bioTv.getText().toString()), 103);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 101:
                    //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                    ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                    if (resultPhotos.size() > 0) {
                        Glide.with(mContext)
                                .load(resultPhotos.get(0).uri)
                                .apply(MyApplication.optionsIcon)
                                .into(iconIv);
                        url = resultPhotos.get(0).path;
                    } else {
                        ToastUtils.showToast("出现未知错误，请重试");
                    }
                    break;
                case 102:
                    nickNameTv.setText(data.getStringExtra("data"));
                    break;
                case 103:
                    bioTv.setText(data.getStringExtra("data"));
                    break;

            }
        }
    }
}
