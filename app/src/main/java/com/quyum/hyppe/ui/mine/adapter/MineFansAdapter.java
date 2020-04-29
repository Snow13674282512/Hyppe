package com.quyum.hyppe.ui.mine.adapter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.FansBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MineFansAdapter extends BaseQuickAdapter<FansBean.DataBean, BaseViewHolder> {

    public MineFansAdapter() {
        super(R.layout.item_mine_fans);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FansBean.DataBean item) {
        helper.setText(R.id.name_tv, item.name);
        Glide.with(mContext)
                .load(item.price)
                .apply(MyApplication.options)
                .into((CircleImageView) helper.getView(R.id.icon_iv));
        if (item.isFollow) {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_follow_big);
            helper.setText(R.id.follow_bt, "Followed");
        } else {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_un_follow_big);
            helper.setText(R.id.follow_bt, "Follow");
        }
        helper.getView(R.id.follow_bt).setOnClickListener(new View.OnClickListener() {
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
