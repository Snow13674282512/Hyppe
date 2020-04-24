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
public class HomeSearchExploreAdapter extends BaseQuickAdapter<TiktokBean, BaseViewHolder> {

    public HomeSearchExploreAdapter() {
        super(R.layout.item_home_search_explore);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TiktokBean item) {
        helper.setText(R.id.num_tv, String.valueOf(item.likeCount));
        if (item.authorSex == 0) {
            helper.setBackgroundRes(R.id.like_iv, R.drawable.icon_sousuojieguo_xihuande);
            helper.setVisible(R.id.distance_tv, true);
        } else {
            helper.setBackgroundRes(R.id.like_iv, R.drawable.icon_suosoujieguo_weixihuande);
            helper.setVisible(R.id.distance_tv, false);
        }
        Glide.with(getContext())
                .load(item.coverImgUrl)
                .apply(MyApplication.options)
                .into((ImageView) helper.getView(R.id.image_iv));
    }
}
