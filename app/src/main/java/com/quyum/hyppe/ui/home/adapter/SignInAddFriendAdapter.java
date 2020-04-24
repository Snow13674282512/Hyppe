package com.quyum.hyppe.ui.home.adapter;

import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.SignInAddFriendBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SignInAddFriendAdapter extends BaseQuickAdapter<SignInAddFriendBean.DataBean, BaseViewHolder> {

    public SignInAddFriendAdapter() {
        super(R.layout.item_sign_in_add_friend);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SignInAddFriendBean.DataBean item) {
        if (!TextUtils.isEmpty(item.price)) {
            Glide.with(mContext)
                    .load(item.price)
                    .apply(MyApplication.options)
                    .into((CircleImageView) helper.getView(R.id.icon_iv));
        } else {
            helper.setBackgroundRes(R.id.icon_iv, R.drawable.icon_qiandao_yaoqing);
        }
    }
}
