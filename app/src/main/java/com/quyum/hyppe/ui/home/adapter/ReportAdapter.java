package com.quyum.hyppe.ui.home.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.GiftBean;
import com.quyum.hyppe.ui.home.bean.ReportBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ReportAdapter extends BaseQuickAdapter<ReportBean.DataBean, BaseViewHolder> {

    public ReportAdapter() {
        super(R.layout.item_report);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ReportBean.DataBean item) {
        helper.setText(R.id.cotent_tv, item.content);
    }
}
