package com.quyum.hyppe.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.utils.GlideEngine;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MineIconActivity extends BaseActivity {
    @BindView(R.id.icon_iv)
    ImageView iconIv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_icon;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
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

    @OnClick(R.id.update_bt)
    public void onViewClicked() {
        EasyPhotos.createAlbum(mContext, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
                .setCount(1)
                .setOriginalMenu(true, true, null)
                .start(101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                if (resultPhotos.size() > 0) {
                    Glide.with(mContext)
                            .load(resultPhotos.get(0).uri)
                            .apply(MyApplication.optionsIcon)
                            .into(iconIv);
                } else {
                    ToastUtils.showToast("出现未知错误，请重试");
                }
                return;
            }
        } else if (RESULT_CANCELED == resultCode) {
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
