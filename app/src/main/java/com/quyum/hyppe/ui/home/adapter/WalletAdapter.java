package com.quyum.hyppe.ui.home.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.GroupBean;
import com.quyum.hyppe.ui.home.bean.WalletBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WalletAdapter extends BaseQuickAdapter<WalletBean.DataBean, BaseViewHolder> {

    public WalletAdapter() {
        super(R.layout.item_wallet);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final WalletBean.DataBean item) {
        helper.setText(R.id.title_tv, item.title);
        helper.setText(R.id.time_tv, item.time);
    }
}
