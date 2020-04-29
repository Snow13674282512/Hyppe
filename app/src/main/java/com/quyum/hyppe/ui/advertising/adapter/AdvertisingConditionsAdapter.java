package com.quyum.hyppe.ui.advertising.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.advertising.bean.AdvertisingConditionsBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class AdvertisingConditionsAdapter extends BaseQuickAdapter<AdvertisingConditionsBean.DataBean, BaseViewHolder> {

    public AdvertisingConditionsAdapter() {
        super(R.layout.item_advertising_conditions);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AdvertisingConditionsBean.DataBean item) {
        helper.setText(R.id.title_tv, item.name);
        if (item.isSelect) {
            helper.setBackgroundRes(R.id.title_tv, R.drawable.bg_advertising_conditions_select);
        } else {
            helper.setBackgroundRes(R.id.title_tv, R.drawable.bg_advertising_conditions_un_select);
        }
    }
}
