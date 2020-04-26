package com.quyum.hyppe.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.DraftBoxBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DraftBoxAdapter extends BaseQuickAdapter<DraftBoxBean.DataBean, BaseViewHolder> {

    public DraftBoxAdapter() {
        super(R.layout.item_draft_box);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final DraftBoxBean.DataBean item) {
        if (item.isSelect) {
            helper.setImageResource(R.id.checkbox, R.drawable.draft_select);
        } else {
            helper.setImageResource(R.id.checkbox, R.drawable.draft_un_select);
        }
        if (item.isOpen) {
            helper.setGone(R.id.checkbox, true);
        } else {
            helper.setGone(R.id.checkbox, false);
        }
        helper.getView(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isSelect) {
                    item.isSelect = false;
                } else {
                    item.isSelect = true;
                }
                notifyItemChanged(helper.getAdapterPosition());
            }
        });
        helper.setText(R.id.title_tv, item.title);
        Glide.with(mContext)
                .load(item.price)
                .apply(MyApplication.options)
                .into((ImageView) helper.getView(R.id.img_iv));
    }
}
