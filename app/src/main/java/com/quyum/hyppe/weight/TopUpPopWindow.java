package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.PopTopUpAdapter;
import com.quyum.hyppe.ui.home.bean.TopUpBean;
import com.quyum.hyppe.utils.GridSpacingItemDecoration;
import com.quyum.hyppe.utils.ToastUtils;

import java.util.List;

public class TopUpPopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private PopTopUpAdapter adapter;
    private View linearLayout;

    public TopUpPopWindow(View linearLayout, Context context) {
        this.linearLayout = linearLayout;
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_top_up, null);
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
        TextView numTv = contentView.findViewById(R.id.num_tv);

        ImageView cancelIv = contentView.findViewById(R.id.cancel_iv);
        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 20, false));
        adapter = new PopTopUpAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<TopUpBean.DataBean> list = adapter.getData();
                numTv.setText(list.get(position).price);
                ToastUtils.showToast("充值成功");
                customPopWindow.dissmiss();
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
