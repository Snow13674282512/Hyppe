package com.quyum.hyppe.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.GiftBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PopGiftAdapter extends BaseQuickAdapter<GiftBean.DataBean, BaseViewHolder> {

    public PopGiftAdapter() {
        super(R.layout.item_pop_gift);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GiftBean.DataBean item) {
        helper.setImageResource(R.id.img_iv, item.drawable);
        helper.setText(R.id.title_tv, item.title);
        if (item.isSelect) {
            helper.setBackgroundRes(R.id.root_rl, R.drawable.bg_item_gift_select);
        } else {
            helper.setBackgroundRes(R.id.root_rl, R.drawable.bg_item_gift_un_select);
        }
    }
}
