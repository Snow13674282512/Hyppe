package com.quyum.hyppe.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.EmojisBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PopEmojisAdapter extends BaseQuickAdapter<EmojisBean.DataBean, BaseViewHolder> {

    public PopEmojisAdapter() {
        super(R.layout.item_pop_emojis);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final EmojisBean.DataBean item) {
        helper.setImageResource(R.id.img_iv, item.drawable);
    }
}
