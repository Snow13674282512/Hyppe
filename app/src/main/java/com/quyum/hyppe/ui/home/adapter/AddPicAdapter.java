package com.quyum.hyppe.ui.home.adapter;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;

public class AddPicAdapter extends BaseQuickAdapter<Photo, BaseViewHolder> {

    public AddPicAdapter() {
        super(R.layout.item_pic_grid);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final Photo item) {
        Glide.with(mContext)
                .load(item.uri)
                .apply(MyApplication.options)
                .into((QMUIRadiusImageView) helper.getView(R.id.image_iv));
        if (TextUtils.isEmpty(item.path)) {
            helper.setVisible(R.id.add_pic_rl, true);
            helper.setVisible(R.id.image_iv, false);
            helper.setVisible(R.id.delete_iv, false);
        } else {
            helper.setVisible(R.id.add_pic_rl, false);
            helper.setVisible(R.id.image_iv, true);
            helper.setVisible(R.id.delete_iv, true);
        }
        String s = getData().size() - 1 + "/4" + "\n" + "(Must be)";
        helper.setText(R.id.num_tv, s);
        helper.addOnClickListener(R.id.delete_iv);
    }
}
