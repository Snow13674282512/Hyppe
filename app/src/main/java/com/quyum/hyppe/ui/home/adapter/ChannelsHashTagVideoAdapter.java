package com.quyum.hyppe.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.TiktokBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ChannelsHashTagVideoAdapter extends BaseQuickAdapter<TiktokBean, BaseViewHolder> {

    public ChannelsHashTagVideoAdapter() {
        super(R.layout.item_channels_video);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TiktokBean item) {
        Glide.with(mContext)
                .load(item.coverImgUrl)
                .apply(MyApplication.options)
                .into((ImageView) helper.getView(R.id.image_iv));

    }
}
