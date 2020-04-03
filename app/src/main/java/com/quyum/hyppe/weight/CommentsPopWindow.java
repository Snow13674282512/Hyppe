package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopCommentsAdapter;
import com.quyum.hyppe.utils.TextEditTextView;

public class CommentsPopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private View linearLayout;
    private TextEditTextView textEditTextView;

    public CommentsPopWindow(View linearLayout, Context context) {
        this.linearLayout = linearLayout;
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_comments_bounce, null);
        handleNation(contentView);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
                .setAnimationStyle(R.style.anim_gift_pop)
                .size(linearLayout.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
    }

    public void show() {
        customPopWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);
    }

    private void handleNation(View contentView) {
        textEditTextView = contentView.findViewById(R.id.content_et);
        ImageView atIv = contentView.findViewById(R.id.at_iv);
        ImageView emojiIv = contentView.findViewById(R.id.emoji_iv);
    }

    public String getContent() {
        return textEditTextView.getText().toString().trim();
    }


    public void close() {
        customPopWindow.dissmiss();
    }
}
