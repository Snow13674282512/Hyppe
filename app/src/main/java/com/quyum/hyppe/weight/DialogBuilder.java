package com.quyum.hyppe.weight;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quyum.hyppe.R;

/**
 * Created by Administrator on 2017/8/25.
 */

public class DialogBuilder {
    /**
     * 选择 dialog
     *
     * @param context
     */
    public static MyDialog selectDialog(final Context context, String okStr, String content, View.OnClickListener onClickListener) {

        View view = View.inflate(context, R.layout.dialog_select, null);
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        TextView okTv = view.findViewById(R.id.ok_tv);
        View bottomCenterView = view.findViewById(R.id.bottom_center_line);
        if (okStr.equals("Acknowledge") || okStr.equals("Back to the home")) {
            cancelTv.setVisibility(View.GONE);
            bottomCenterView.setVisibility(View.GONE);
        }
        okTv.setText(okStr);
        contentTv.setText(content);

        final MyDialog builder = new MyDialog(context, view, R.style.DialogTheme, Gravity.CENTER);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        okTv.setOnClickListener(onClickListener);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        return builder;
    }

    /**
     * 提示 dialog
     *
     * @param context
     */
    public static MyDialog hintDialog(final Context context) {

        View view = View.inflate(context, R.layout.dialog_successful, null);
        LinearLayout rootLayout = view.findViewById(R.id.root_layout);

        final MyDialog builder = new MyDialog(context, view, R.style.DialogTheme, Gravity.CENTER);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.cancel();
            }
        }, 1500);//2秒后执行Runnable中的run方法
        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        return builder;
    }

}
