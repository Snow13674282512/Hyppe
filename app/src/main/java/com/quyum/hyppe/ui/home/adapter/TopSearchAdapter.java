package com.quyum.hyppe.ui.home.adapter;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.TopSearchBean;

public class TopSearchAdapter extends BaseQuickAdapter<TopSearchBean.DataBean, BaseViewHolder> {

    public TopSearchAdapter() {
        super(R.layout.item_top_search);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TopSearchBean.DataBean item) {
        helper.setText(R.id.content_tv, item.content);
        View view = helper.getView(R.id.content_tv);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int with = dm.widthPixels;
        int viewWith = with * 1 / 2;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = viewWith;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(layoutParams);
    }
}
