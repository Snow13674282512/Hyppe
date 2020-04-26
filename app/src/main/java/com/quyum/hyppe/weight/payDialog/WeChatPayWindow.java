package com.quyum.hyppe.weight.payDialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.KeyboardAdapter;

import java.util.List;

/**
 * 仿微信支付布局
 * Created by ayuhani on 2017/6/29.
 */
public class WeChatPayWindow extends PopupWindow implements KeyboardAdapter.OnKeyboardClickListener {

    public OnPasswordFinishedListener listener;
    private ImageView ivClose;  // 关闭按钮
    private TextView tvPrice;   // 价格
    private PasswordView passwordView;
    private KeyboardView keyboardView;
    private List<String> datas;
    private String[] numbers;
    private ImageView[] points;
    private int currentIndex;   // 当前即将要输入密码的格子的索引


    public WeChatPayWindow(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_wechat_pay, null);
        setContentView(contentView);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setClippingEnabled(false); // 让PopupWindow同样覆盖状态栏
        setBackgroundDrawable(new ColorDrawable(0xAA000000)); // 加上一层黑色透明背景
        initView(contentView);
    }

    private void initView(View contentView) {
        ivClose = contentView.findViewById(R.id.iv_close);
        tvPrice = contentView.findViewById(R.id.tv_price);
        passwordView = contentView.findViewById(R.id.password_view);
        keyboardView = contentView.findViewById(R.id.keyboard_view);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        keyboardView.setOnKeyBoardClickListener(this);
        datas = keyboardView.getDatas();
        numbers = passwordView.getNumbers();
        points = passwordView.getPoints();

        // 这里给每个FrameLayout添加点击事件，当键盘被收起时点击空白输入框，再次弹出键盘
        // 微信也是这样的，但我觉得并没有什么意义
        for (int i = 0; i < passwordView.getFrameLayouts().length; i++) {
            final int finalI = i;
            passwordView.getFrameLayouts()[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (points[finalI].getVisibility() != View.VISIBLE && !keyboardView.isVisible()) {
                        keyboardView.show();
                    }
                }
            });
        }
    }

    public WeChatPayWindow setPrice(CharSequence price) {
        tvPrice.setText(price);
        return this;
    }

    // 弹出PopupWindow
    public void show(View rootView) {
        showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onKeyClick(View view, RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 9: // 点击小数点没有作用，最好是把小数点隐藏掉，我这里偷懒了
                break;
            default:
                if (currentIndex >= 0 && currentIndex < numbers.length) {
                    numbers[currentIndex] = datas.get(position);
                    points[currentIndex].setVisibility(View.VISIBLE);
                    currentIndex++; // 当前位置的密码输入后，位置加一

                    if (currentIndex == numbers.length && listener != null) {
                        // 已经输入了六位数的密码了，回调方法
                        listener.onFinish(passwordView.getPassword());
                    }
                }
        }
    }

    @Override
    public void onDeleteClick(View view, RecyclerView.ViewHolder holder, int position) {
        // 点击删除按钮
        if (currentIndex > 0 && currentIndex < numbers.length) {
            currentIndex--;
            numbers[currentIndex] = "";
            points[currentIndex].setVisibility(View.GONE);
        }
    }

    public void setOnPasswordFinishedListener(OnPasswordFinishedListener listener) {
        this.listener = listener;
    }
}
