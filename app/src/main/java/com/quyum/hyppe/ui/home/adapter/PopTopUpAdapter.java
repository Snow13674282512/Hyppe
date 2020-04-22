package com.quyum.hyppe.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.TopUpBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PopTopUpAdapter extends BaseQuickAdapter<TopUpBean.DataBean, BaseViewHolder> {

    public PopTopUpAdapter() {
        super(R.layout.item_pop_balance);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TopUpBean.DataBean item) {
        helper.setText(R.id.num_tv, item.num);
        helper.setText(R.id.price_tv, "Price $" + item.price);
    }
}
