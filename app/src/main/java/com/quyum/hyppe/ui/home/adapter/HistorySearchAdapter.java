package com.quyum.hyppe.ui.home.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.config.SystemParams;

public class HistorySearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistorySearchAdapter() {
        super(R.layout.item_search_history);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        helper.setText(R.id.content_tv, item);
        helper.addOnClickListener(R.id.delete_iv);
    }
}
