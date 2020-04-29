package com.quyum.hyppe.ui.gift.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.gift.bean.GiftHistoryBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GiftHistoryAdapter extends BaseQuickAdapter<GiftHistoryBean.DataBean, BaseViewHolder> {

    public GiftHistoryAdapter() {
        super(R.layout.item_gift_history);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GiftHistoryBean.DataBean item) {
        helper.setText(R.id.time_tv, item.time);
        helper.setText(R.id.name_tv, item.name);
        helper.setText(R.id.gift_name_tv, "a" + item.giftName);
        Glide.with(mContext)
                .load(item.pic)
                .apply(MyApplication.options)
                .into((ImageView) helper.getView(R.id.gift_iv));
    }
}
