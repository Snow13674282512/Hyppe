package com.quyum.hyppe.weight;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopEmojisAdapter;
import com.quyum.hyppe.ui.home.bean.EmojisBean;

public class EmojisPopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private PopEmojisAdapter adapter;
    private View linearLayout;

    public EmojisPopWindow(View linearLayout, Context context) {
        this.linearLayout = linearLayout;
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_emojis, null);
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
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        TextView cancelTv = contentView.findViewById(R.id.cancel_tv);

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PopEmojisAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                customPopWindow.dissmiss();
                BigEmojisWindow bigEmojisWindow = new BigEmojisWindow(linearLayout, context, ((EmojisBean.DataBean) adapter.getData().get(position)).drawable);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        bigEmojisWindow.close();
                    }
                }, 1500);
            }
        });
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void setOnCloseListener(PopupWindow.OnDismissListener onDismissListener) {
        customPopWindow.setmOnDismissListener(onDismissListener);
    }

    public void close() {
        customPopWindow.dissmiss();
    }
}
