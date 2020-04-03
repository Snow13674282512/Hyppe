package com.quyum.hyppe.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.CommentsBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PopCommentsAdapter extends BaseQuickAdapter<CommentsBean.DataBean, BaseViewHolder> {

    public PopCommentsAdapter() {
        super(R.layout.item_comments);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommentsBean.DataBean item) {
        helper.setImageResource(R.id.icon_iv, R.drawable.default_icon);
        helper.setText(R.id.content_tv, item.content);
        helper.setText(R.id.time_tv, item.time);
        helper.setText(R.id.name_tv, item.name);
    }
}
