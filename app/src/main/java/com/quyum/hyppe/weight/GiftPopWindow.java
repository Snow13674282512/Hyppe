package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopGiftAdapter;
import com.quyum.hyppe.ui.home.bean.GiftBean;
import com.quyum.hyppe.utils.ToastUtils;

import java.util.List;

public class GiftPopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private PopGiftAdapter adapter;
    private View linearLayout;
    private View.OnClickListener onClickListener;

    public GiftPopWindow(View linearLayout, Context context, View.OnClickListener onClickListener) {
        this.linearLayout = linearLayout;
        this.context = context;
        this.onClickListener = onClickListener;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_gift, null);
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
        TextView balanceTv = contentView.findViewById(R.id.balance_tv);
        TextView topUp = contentView.findViewById(R.id.top_up_tv);
        Button sendBt = contentView.findViewById(R.id.send_bt);

        topUp.setOnClickListener(onClickListener);
        sendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isHaveSelect = false;
                for (GiftBean.DataBean item : adapter.getData()) {
                    if (item.isSelect) {
                        isHaveSelect = true;
                    }
                }
                if (!isHaveSelect) {
                    ToastUtils.showToast("请选择礼物");
                    return;
                }
                ToastUtils.showToast("感谢大哥的666个火箭");
                customPopWindow.dissmiss();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new PopGiftAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GiftBean.DataBean> list = (List<GiftBean.DataBean>) adapter.getData();
                for (GiftBean.DataBean item : list) {
                    item.isSelect = false;
                }
                list.get(position).isSelect = true;
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void setOnCloseListener(PopupWindow.OnDismissListener onDismissListener) {
        customPopWindow.setmOnDismissListener(onDismissListener);
    }

    public BaseQuickAdapter getAdapter() {
        return adapter;
    }

    public void close() {
        customPopWindow.dissmiss();
    }
}
