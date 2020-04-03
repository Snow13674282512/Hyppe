package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quyum.hyppe.R;

public class BigEmojisWindow {
    private CustomPopWindow customPopWindow;
    private View linearLayout;
    private Integer drawable;

    public BigEmojisWindow(View linearLayout, Context context, int drawable) {
        this.linearLayout = linearLayout;
        this.drawable = drawable;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_big_emojis, null);
        handleNation(contentView);
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView)
                .setAnimationStyle(R.style.anim_emojis_pop)
                .size(linearLayout.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT)
                .create()
                .showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
    }

    private void handleNation(View contentView) {
        ImageView imageView = contentView.findViewById(R.id.image);
        imageView.setImageResource(drawable);
    }

    public void close() {
        customPopWindow.dissmiss();
    }
}
