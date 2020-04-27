package com.quyum.hyppe.weight;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.activity.ChangeEmailActivity;
import com.quyum.hyppe.utils.ToastUtils;

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
        if (okStr.equals("Acknowledge") || okStr.equals("Back to the home") || okStr.equals("Set payment password") || okStr.equals("go back")) {
            cancelTv.setVisibility(View.GONE);
            bottomCenterView.setVisibility(View.GONE);
        }
        if (okStr.equals("Top Up")) {
            cancelTv.setText("Cancel");
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
     * 选择 dialog
     *
     * @param context
     */
    public static MyDialog change(final Context context, String email) {

        View view = View.inflate(context, R.layout.dialog_select, null);
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        TextView okTv = view.findViewById(R.id.ok_tv);
        TextView titleTv = view.findViewById(R.id.title_tv);
        titleTv.setText("Change your Email?");
        okTv.setText("Change");
        cancelTv.setText("Cancel");
        contentTv.setText("Your current Email is" + email);

        final MyDialog builder = new MyDialog(context, view, R.style.DialogTheme, Gravity.CENTER);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);
        builder.show();
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                context.startActivity(new Intent(context, ChangeEmailActivity.class));
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        return builder;
    }

    /**
     * 支付密码错误 dialog
     *
     * @param context
     */
    public static MyDialog passwordErrorDialog(final Context context, View.OnClickListener onClickListener) {

        View view = View.inflate(context, R.layout.dialog_select, null);
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView cancelTv = view.findViewById(R.id.cancel_tv);
        TextView okTv = view.findViewById(R.id.ok_tv);
        cancelTv.setText("忘记密码");
        okTv.setText("重试");
        contentTv.setText("支付密码错误");

        final MyDialog builder = new MyDialog(context, view, R.style.DialogTheme, Gravity.CENTER);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(true);

        okTv.setOnClickListener(onClickListener);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
                ToastUtils.showToast("改不了！");
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

    /**
     * error dialog
     *
     * @param context
     */
    public static MyDialog errorDialog(final Context context) {

        View view = View.inflate(context, R.layout.dialog_error, null);
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
