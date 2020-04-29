package com.quyum.hyppe.ui.advertising.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.advertising.bean.AdvertisingBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AdvertisingAdapter extends BaseQuickAdapter<AdvertisingBean.DataBean, BaseViewHolder> {

    public AdvertisingAdapter() {
        super(R.layout.item_advertising);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AdvertisingBean.DataBean item) {
        helper.setText(R.id.num_tv, "<" + item.distance);
        if (item.isSelect) {
            helper.setGone(R.id.public_iv, true);
        } else {
            helper.setGone(R.id.public_iv, false);
        }
    }
}
