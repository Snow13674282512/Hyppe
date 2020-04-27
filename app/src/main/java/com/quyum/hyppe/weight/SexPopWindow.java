package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopGiftAdapter;

public class SexPopWindow {
    private CustomPopWindow customPopWindow;
    private View linearLayout;
    private View.OnClickListener femaleClickListener, maleClickListener;

    public SexPopWindow(View linearLayout, Context context, View.OnClickListener femaleClickListener, View.OnClickListener maleClickListener) {
        this.linearLayout = linearLayout;
        this.femaleClickListener = femaleClickListener;
        this.maleClickListener = maleClickListener;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_sex, null);
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

        contentView.findViewById(R.id.female_tv).setOnClickListener(femaleClickListener);
        contentView.findViewById(R.id.male_tv).setOnClickListener(maleClickListener);
        contentView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
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
