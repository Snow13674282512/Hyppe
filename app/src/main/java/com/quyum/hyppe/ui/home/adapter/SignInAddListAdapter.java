package com.quyum.hyppe.ui.home.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.SignInListBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SignInAddListAdapter extends BaseQuickAdapter<SignInListBean.DataBean, BaseViewHolder> {

    public SignInAddListAdapter() {
        super(R.layout.item_sign_in_list);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SignInListBean.DataBean item) {
//        Glide.with(mContext)
//                .load(item.price)
//                .apply(MyApplication.options)
//                .into((CircleImageView) helper.getView(R.id.icon_iv));
        helper.setText(R.id.title_tv, item.title);
        helper.setText(R.id.num_tv, item.num);
        if (item.status) {
            helper.setBackgroundRes(R.id.status_bt, R.drawable.bg_follow);
            helper.setText(R.id.status_bt, "Completed");
        } else {
            helper.setBackgroundRes(R.id.status_bt, R.drawable.bg_un_follow);
            helper.setText(R.id.status_bt, "Receive");
        }
    }
}
