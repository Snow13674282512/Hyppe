package com.quyum.hyppe.ui.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.TiktokBean;

import static com.quyum.hyppe.base.MyApplication.getContext;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HomePageAdapter extends BaseQuickAdapter<TiktokBean, BaseViewHolder> {

    public HomePageAdapter() {
        super(R.layout.item_home_page);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TiktokBean item) {
        ImageView imageView = helper.getView(R.id.pic_iv);
        helper.setText(R.id.num_tv, item.formatPlayCountStr);
        Glide.with(getContext())
                .load(item.coverImgUrl)
                .apply(MyApplication.options)
                .into(imageView);
    }
}
