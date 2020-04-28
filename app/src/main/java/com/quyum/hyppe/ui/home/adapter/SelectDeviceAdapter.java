package com.quyum.hyppe.ui.home.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.SelectDeviceBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SelectDeviceAdapter extends BaseQuickAdapter<SelectDeviceBean.DataBean, BaseViewHolder> {

    public SelectDeviceAdapter() {
        super(R.layout.item_select_device);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SelectDeviceBean.DataBean item) {
        helper.setText(R.id.name_tv, item.name);
        if (item.isSelect) {
            helper.setGone(R.id.public_iv, true);
        } else {
            helper.setGone(R.id.public_iv, false);
        }
    }
}
