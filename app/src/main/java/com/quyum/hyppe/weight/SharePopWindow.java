package com.quyum.hyppe.weight;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopEmojisAdapter;
import com.quyum.hyppe.ui.home.bean.EmojisBean;
import com.quyum.hyppe.utils.ToastUtils;

public class SharePopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private View linearLayout;

    public SharePopWindow(View linearLayout, Context context) {
        this.linearLayout = linearLayout;
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_share, null);
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
        LinearLayout twitterLl = contentView.findViewById(R.id.twitter_ll);
        LinearLayout facebookLl = contentView.findViewById(R.id.face_book_ll);
        LinearLayout youtubeLl = contentView.findViewById(R.id.youtobe_ll);
        LinearLayout reportLl = contentView.findViewById(R.id.report_ll);
        LinearLayout downloadLl = contentView.findViewById(R.id.download_ll);
        TextView cancelTv = contentView.findViewById(R.id.cancel_tv);

        twitterLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("敬请期待");
            }
        });
        facebookLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("敬请期待");
            }
        });
        youtubeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("敬请期待");
            }
        });
        reportLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        downloadLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
    }

    public void setOnCloseListener(PopupWindow.OnDismissListener onDismissListener) {
        customPopWindow.setmOnDismissListener(onDismissListener);
    }

    public void close() {
        customPopWindow.dissmiss();
    }
}
