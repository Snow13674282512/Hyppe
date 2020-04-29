package com.quyum.hyppe.ui.certification.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.utils.GlideEngine;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class RealNameActivity extends BaseActivity {
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.id_et)
    EditText idEt;
    @BindView(R.id.front_iv)
    QMUIRadiusImageView frontIv;
    @BindView(R.id.side_iv)
    QMUIRadiusImageView sideIv;
    String frontUrl, sideUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Real Name Verification");
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.front_iv, R.id.side_iv, R.id.submit_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.front_iv:
                EasyPhotos.createAlbum(mContext, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .setCount(1)
                        .setOriginalMenu(true, true, null)
                        .start(101);
                break;
            case R.id.side_iv:
                EasyPhotos.createAlbum(mContext, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                        .setCount(1)
                        .setOriginalMenu(true, true, null)
                        .start(102);
                break;
            case R.id.submit_bt:
                if (TextUtils.isEmpty(nameEt.getText().toString().trim())) {
                    ToastUtils.showToast("请输入真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(idEt.getText().toString().trim())) {
                    ToastUtils.showToast("请输入身份证号码");
                    return;
                }
                if (TextUtils.isEmpty(frontUrl)) {
                    ToastUtils.showToast("请上传身份证正面照片");
                    return;
                }
                if (TextUtils.isEmpty(sideUrl)) {
                    ToastUtils.showToast("请上传身份证背面照片");
                }
                ToastUtils.showToast("上传成功");
                startActivity(new Intent(mContext, CertificationActivity.class).putExtra("status", 1));
                finish();
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
                    ArrayList<Photo> resultFrontPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                    if (resultFrontPhotos.size() > 0) {
                        frontUrl = resultFrontPhotos.get(0).path;
                        Glide.with(mContext)
                                .load(resultFrontPhotos.get(0).uri)
                                .apply(MyApplication.optionsIcon)
                                .into(frontIv);
                    } else {
                        ToastUtils.showToast("出现未知错误，请重试");
                    }
                    break;
                case 102:
                    //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                    ArrayList<Photo> resultSidePhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                    if (resultSidePhotos.size() > 0) {
                        sideUrl = resultSidePhotos.get(0).path;
                        Glide.with(mContext)
                                .load(resultSidePhotos.get(0).uri)
                                .apply(MyApplication.optionsIcon)
                                .into(sideIv);
                    } else {
                        ToastUtils.showToast("出现未知错误，请重试");
                    }
                    break;
            }
        } else if (RESULT_CANCELED == resultCode) {
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

}
