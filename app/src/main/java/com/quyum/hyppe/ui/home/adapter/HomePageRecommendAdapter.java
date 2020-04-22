package com.quyum.hyppe.ui.home.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.HomePageRecommendBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomePageRecommendAdapter extends BaseQuickAdapter<HomePageRecommendBean.DataBean, BaseViewHolder> {

    public HomePageRecommendAdapter() {
        super(R.layout.item_home_page_recommend);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomePageRecommendBean.DataBean item) {
        helper.setImageResource(R.id.icon_iv, item.drawable);
        helper.setText(R.id.name_tv, item.title);
        helper.setText(R.id.content_tv, item.content);
        if (item.isFollow) {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_follow_big);
        } else {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_un_follow_big);
        }
        helper.getView(R.id.follow_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(helper.getAdapterPosition());
            }
        });
        helper.getView(R.id.delete_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isFollow) {
                    item.isFollow = false;
                } else {
                    item.isFollow = true;
                }
                notifyItemChanged(helper.getAdapterPosition());
            }
        });
    }

}
