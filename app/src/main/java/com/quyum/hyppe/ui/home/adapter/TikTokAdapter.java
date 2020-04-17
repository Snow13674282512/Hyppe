package com.quyum.hyppe.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.utils.cache.PreloadManager;
import com.quyum.hyppe.weight.component.TikTokView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TikTokAdapter extends RecyclerView.Adapter<TikTokAdapter.ViewHolder> {
    private View.OnClickListener giftOnClickListener;
    private View.OnClickListener followOnClickListener;
    private View.OnClickListener hashOnClickListener;
    private View.OnClickListener likeOnClickListener;
    private View.OnClickListener unLikeOnClickListener;
    private View.OnClickListener emojiOnClickListener;
    private View.OnClickListener forwardOnClickListener;
    private View.OnClickListener commentsOnClickListener;
    /**
     * 数据源
     */
    private List<TiktokBean> mVideoBeans;

    public TikTokAdapter(List<TiktokBean> videoBeans, View.OnClickListener giftOnClickListener,
                         View.OnClickListener followOnClickListener, View.OnClickListener hashOnClickListener, View.OnClickListener likeOnClickListener,
                         View.OnClickListener unLikeOnClickListener, View.OnClickListener emojiOnClickListener, View.OnClickListener forwardOnClickListener,
                         View.OnClickListener commentsOnClickListener) {
        this.mVideoBeans = videoBeans;
        this.giftOnClickListener = giftOnClickListener;
        this.followOnClickListener = followOnClickListener;
        this.hashOnClickListener = hashOnClickListener;
        this.likeOnClickListener = likeOnClickListener;
        this.unLikeOnClickListener = unLikeOnClickListener;
        this.emojiOnClickListener = emojiOnClickListener;
        this.forwardOnClickListener = forwardOnClickListener;
        this.commentsOnClickListener = commentsOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tik_tok, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        TiktokBean item = mVideoBeans.get(position);
        //开始预加载
        PreloadManager.getInstance(context).addPreloadTask(item.videoDownloadUrl, position);
        Glide.with(context)
                .load(item.coverImgUrl)
                .apply(MyApplication.options)
                .into(holder.mThumb);
        holder.mTitle.setText(item.title);
        holder.mPosition = position;

        holder.giftIv.setTag(position);
        holder.giftIv.setOnClickListener(giftOnClickListener);

        holder.followBt.setTag(position);
        holder.followBt.setOnClickListener(followOnClickListener);

        holder.hashLl.setTag(position);
        holder.hashLl.setOnClickListener(hashOnClickListener);

        holder.likeLl.setTag(position);
        holder.likeLl.setOnClickListener(likeOnClickListener);

        holder.unLikeLl.setTag(position);
        holder.unLikeLl.setOnClickListener(unLikeOnClickListener);

        holder.expressionLl.setTag(position);
        holder.expressionLl.setOnClickListener(emojiOnClickListener);

        holder.forwardLl.setTag(position);
        holder.forwardLl.setOnClickListener(forwardOnClickListener);

        holder.commentsBt.setTag(position);
        holder.commentsBt.setOnClickListener(commentsOnClickListener);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        TiktokBean item = mVideoBeans.get(holder.mPosition);
        //取消预加载
        PreloadManager.getInstance(holder.itemView.getContext()).removePreloadTask(item.videoDownloadUrl);
    }

    @Override
    public int getItemCount() {
        return mVideoBeans != null ? mVideoBeans.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public int mPosition;
        public TextView mTitle, nameTv, hashTv, likeTv, unLikeTv, expressionTv, forwardTv;//标题
        public ImageView mThumb, giftIv;//封面图
        public TikTokView mTikTokView;
        public FrameLayout mPlayerContainer;
        public CircleImageView iconIv;
        public Button followBt, commentsBt;
        public LinearLayout hashLl, likeLl, unLikeLl, expressionLl, forwardLl;

        ViewHolder(View itemView) {
            super(itemView);
            mTikTokView = itemView.findViewById(R.id.tiktok_View);
            mTitle = mTikTokView.findViewById(R.id.title_tv);
            mThumb = mTikTokView.findViewById(R.id.iv_thumb);
            mPlayerContainer = itemView.findViewById(R.id.container);
            iconIv = mTikTokView.findViewById(R.id.icon_iv);
            nameTv = mTikTokView.findViewById(R.id.name_tv);
            followBt = mTikTokView.findViewById(R.id.follow_bt);
            giftIv = mTikTokView.findViewById(R.id.gift_iv);
            hashLl = mTikTokView.findViewById(R.id.hash_ll);
            hashTv = mTikTokView.findViewById(R.id.hash_tv);
            likeLl = mTikTokView.findViewById(R.id.like_ll);
            likeTv = mTikTokView.findViewById(R.id.like_tv);
            unLikeLl = mTikTokView.findViewById(R.id.un_like_ll);
            unLikeTv = mTikTokView.findViewById(R.id.un_like_tv);
            expressionLl = mTikTokView.findViewById(R.id.expression_ll);
            expressionTv = mTikTokView.findViewById(R.id.expression_tv);
            forwardLl = mTikTokView.findViewById(R.id.forward_ll);
            forwardTv = mTikTokView.findViewById(R.id.forward_tv);
            commentsBt = mTikTokView.findViewById(R.id.look_comments_bt);

            itemView.setTag(this);
        }
    }
}
