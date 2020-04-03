package com.quyum.hyppe.weight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleObserver;

import com.quyum.hyppe.R;
import com.quyum.hyppe.utils.CommonUtil;
import com.quyum.hyppe.utils.IKeyBoard;
import com.quyum.hyppe.utils.WeakHandler;

public class KeyBoardDialogFragment extends DialogFragment implements WeakHandler.IHandler {
    private static final String TAG = "KeyBoardDialogFragment";
    private IKeyBoard mKeyBoardView;
    private boolean softKeyBoardIsVisible;
    private Activity mActivity;
    private WeakHandler mHandler;
    private Rect mRect = new Rect();


    public static KeyBoardDialogFragment newInstance(IKeyBoard keyBoard) {
        KeyBoardDialogFragment fragment = new KeyBoardDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setKeyBoardView(keyBoard);
        return fragment;
    }

    /**
     * 安全检查
     *
     * @param keyBoardView 业务逻辑的view
     */
    public void setKeyBoardView(IKeyBoard keyBoardView) {
        if (keyBoardView == null) {
            throw new RuntimeException("keyBoardView must not be null");
        } else if (keyBoardView.getRoot() == null) {
            throw new RuntimeException("keyBoardView must has root layout");
        }
        mKeyBoardView = keyBoardView;
    }

    /**
     * 设置主题 input_dialog_style_large的具体设置如下
     * <style name="input_dialog_style_large" parent="@android:style/Theme.Dialog">
     * <item name="android:windowBackground">@color/transparent</item> //winndow 背景为透明色
     * <item name="android:windowNoTitle">true</item> // 没有title
     * <item name="android:backgroundDimEnabled">false</item> // 没有默认的背景色
     * <item name="android:windowAnimationStyle">@style/keyboard_dialog_animation</item> //window动画，可以不设置
     * </style>
     * 业务逻辑view，注册DialogFragment声明周期
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.DialogTheme);
        if (mKeyBoardView == null || mKeyBoardView.getRoot() == null) {
            dismiss();
            return;
        }
        if (mKeyBoardView.getRoot() instanceof LifecycleObserver) {
            getLifecycle().addObserver((LifecycleObserver) mKeyBoardView.getRoot());
        }
        mHandler = new WeakHandler(this);
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mActivity = (Activity) activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mKeyBoardView.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWindowParams();
    }

    /**
     * 设置window属性
     */
    public void initWindowParams() {
        Window window = getDialog().getWindow();
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.dimAmount = 0;
        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setAttributes(lp);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(1, 100);
    }

    @Override
    public void onStop() {
        super.onStop();
        dismissAllowingStateLoss();
        mHandler.removeMessages(1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mKeyBoardView.onDismiss();
    }

    public void onGlobalLayout() {
        Window window = getDialog().getWindow();
        if (window != null) {
            mRect.setEmpty();
            window.getDecorView().getWindowVisibleDisplayFrame(mRect);
            int screenHeight = CommonUtil.getScreenHeight(mActivity);
            int heightDifference = screenHeight - (mRect.bottom - mRect.top);
            if (heightDifference > screenHeight / 3) {
                Log.d(TAG, "键盘弹出");
                softKeyBoardIsVisible = true;
            } else {
                if (softKeyBoardIsVisible) {
                    Log.d(TAG, "键盘隐藏");
                    mKeyBoardView.onDismiss();
                    softKeyBoardIsVisible = false;
                }
            }
        }
    }


    @Override
    public void handleMsg(Message msg) {
        if (msg.what == 1) {
            onGlobalLayout();
            mHandler.sendEmptyMessageDelayed(1, 100);
        }
    }
}
