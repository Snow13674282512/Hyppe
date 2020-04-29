package com.quyum.hyppe.ui.setting.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.setting.bean.FeedBackHelpBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FeedHelpAdapter extends BaseQuickAdapter<FeedBackHelpBean.DataBean, BaseViewHolder> {

    public FeedHelpAdapter() {
        super(R.layout.item_feedback_help);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FeedBackHelpBean.DataBean item) {
        helper.setText(R.id.title_tv, item.title);
        if (helper.getAdapterPosition() == getData().size() - 1) {
            helper.setVisible(R.id.bottom_view, false);
        } else {
            helper.setVisible(R.id.bottom_view, true);
        }
    }
}
