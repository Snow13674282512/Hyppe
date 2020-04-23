package com.quyum.hyppe.ui.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.base.UserBean;

import static com.quyum.hyppe.base.MyApplication.getContext;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomeSearchUserAdapter extends BaseQuickAdapter<UserBean.DataBean, BaseViewHolder> {

    public HomeSearchUserAdapter() {
        super(R.layout.item_home_search_user);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final UserBean.DataBean item) {
        helper.setText(R.id.name_tv, item.ui_nickname);
        helper.setText(R.id.number_tv, item.ui_id);
        if (item.isSelect) {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_follow_big);
            helper.setText(R.id.follow_bt, "Cancel");
        } else {
            helper.setBackgroundRes(R.id.follow_bt, R.drawable.bg_un_follow_big);
            helper.setText(R.id.follow_bt, "Follow");
        }
        Glide.with(getContext())
                .load(item.ui_headurl)
                .apply(MyApplication.options)
                .into((ImageView) helper.getView(R.id.icon_iv));
        helper.getView(R.id.follow_bt).setOnClickListener(new View.OnClickListener() {
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
    }
}
