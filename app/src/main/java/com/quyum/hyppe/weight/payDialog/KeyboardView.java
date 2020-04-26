package com.quyum.hyppe.weight.payDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.adapter.KeyboardAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang on 2017/6/23.
 */

public class KeyboardView extends RelativeLayout {

    private RecyclerView recyclerView;
    private List<String> datas;
    private KeyboardAdapter adapter;
    private Animation animationIn;
    private Animation animationOut;


    public KeyboardView(Context context) {
        this(context, null);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.layout_key_board, this);
        recyclerView = findViewById(R.id.recycler_view);

        initData();
        initView();
        initAnimation();
    }

    // 填充数据
    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                datas.add(String.valueOf(i + 1));
            } else if (i == 9) {
                datas.add("");
            } else if (i == 10) {
                datas.add("0");
            } else {
                datas.add("");
            }
        }
    }

    // 设置适配器
    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new KeyboardAdapter(getContext(), datas);
        recyclerView.setAdapter(adapter);
    }

    // 初始化动画效果
    private void initAnimation() {
        animationIn = AnimationUtils.loadAnimation(getContext(), R.anim.keyboard_in);
        animationOut = AnimationUtils.loadAnimation(getContext(), R.anim.keyboard_out);
    }

    // 弹出软键盘
    public void show() {
        startAnimation(animationIn);
        setVisibility(VISIBLE);
    }

    // 关闭软键盘
    public void dismiss() {
        if (isVisible()) {
            startAnimation(animationOut);
            setVisibility(GONE);
        }
    }

    // 判断软键盘的状态
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    public void setOnKeyBoardClickListener(KeyboardAdapter.OnKeyboardClickListener listener) {
        adapter.setOnKeyboardClickListener(listener);
    }

    public List<String> getDatas() {
        return datas;
    }

}
