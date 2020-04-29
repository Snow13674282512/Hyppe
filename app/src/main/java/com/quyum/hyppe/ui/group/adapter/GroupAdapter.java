package com.quyum.hyppe.ui.group.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.group.bean.GroupBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GroupAdapter extends BaseQuickAdapter<GroupBean.DataBean, BaseViewHolder> {

    public GroupAdapter() {
        super(R.layout.item_group);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GroupBean.DataBean item) {
        helper.setText(R.id.num_tv, "+" + item.num);
        helper.setText(R.id.name_tv, item.name);
        Glide.with(mContext)
                .load(item.price)
                .apply(MyApplication.options)
                .into((CircleImageView) helper.getView(R.id.icon_iv));
    }
}
