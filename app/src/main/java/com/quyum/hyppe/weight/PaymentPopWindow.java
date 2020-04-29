package com.quyum.hyppe.weight;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.setting.activity.AddBankActivity;
import com.quyum.hyppe.weight.payDialog.PopPaymentAdapter;

public class PaymentPopWindow {
    private CustomPopWindow customPopWindow;
    private Context context;
    private PopPaymentAdapter adapter;
    private View linearLayout;
    private TextView moneyTv;
    private TextView unitTv;

    public PaymentPopWindow(View linearLayout, Context context) {
        this.linearLayout = linearLayout;
        this.context = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_payment, null);
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

    public void setUnit(String unit) {
        unitTv.setText(unit);
    }

    private void handleNation(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        moneyTv = contentView.findViewById(R.id.money_tv);
        unitTv = contentView.findViewById(R.id.unit_tv);
        moneyTv.setText(moneyTv.getText().toString().trim());

        contentView.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
        contentView.findViewById(R.id.add_bank_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AddBankActivity.class).putExtra("isCallBack", true));
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter = new PopPaymentAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void setMoney(String money) {
        moneyTv.setText(money);
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
