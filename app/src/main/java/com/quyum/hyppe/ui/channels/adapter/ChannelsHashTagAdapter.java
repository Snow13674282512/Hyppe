package com.quyum.hyppe.ui.channels.adapter;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.activity.HashTagActivity;
import com.quyum.hyppe.ui.home.activity.TikTokActivity;
import com.quyum.hyppe.ui.channels.bean.ChannelsHashTagBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ChannelsHashTagAdapter extends BaseQuickAdapter<ChannelsHashTagBean.DataBean, BaseViewHolder> {

    public ChannelsHashTagAdapter() {
        super(R.layout.item_channels_hashtag);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ChannelsHashTagBean.DataBean item) {
        helper.setText(R.id.title_tv, "#" + item.title);
        helper.getView(R.id.more_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, HashTagActivity.class));
            }
        });
        RecyclerView recyclerView = helper.getView(R.id.recycler_view);
        ChannelsHashTagVideoAdapter adapter = new ChannelsHashTagVideoAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.list);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mContext.startActivity(new Intent(mContext, TikTokActivity.class).putExtra("pos", position));
            }
        });

    }
}
