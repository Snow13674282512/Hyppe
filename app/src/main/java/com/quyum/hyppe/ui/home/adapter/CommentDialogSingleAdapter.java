package com.quyum.hyppe.ui.home.adapter;

import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.FirstLevelBean;
import com.quyum.hyppe.utils.TimeUtils;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.quyum.hyppe.base.MyApplication.getContext;

/**
 * @author ganhuanhui
 * 时间：2019/12/19 0019
 * 描述：
 */
public class CommentDialogSingleAdapter extends BaseQuickAdapter<FirstLevelBean, BaseViewHolder> {

    public CommentDialogSingleAdapter() {
        super(R.layout.item_comments);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FirstLevelBean item) {

        CircleImageView iv_header = helper.getView(R.id.icon_iv);
        TextView tv_content = helper.getView(R.id.content_tv);

        String contents = TextUtils.isEmpty(item.getContent()) ? " " : item.getContent();
        tv_content.setText(contents);

        String time = TimeUtils.getRecentTimeSpanByNow(item.getCreateTime());
        helper.setText(R.id.time_tv, time);
        helper.setText(R.id.name_tv, TextUtils.isEmpty(item.getUserName()) ? " " : item.getUserName());

        Glide.with(getContext()).load(item.getHeadImg()).into(iv_header);

    }
}
