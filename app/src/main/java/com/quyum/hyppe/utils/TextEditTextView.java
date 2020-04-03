package com.quyum.hyppe.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import androidx.appcompat.widget.AppCompatEditText;

public class TextEditTextView extends AppCompatEditText {
    public TextEditTextView(Context context) {
        super(context);
    }

    public TextEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == 1 && onKeyBoardHideListener != null) {
            onKeyBoardHideListener.onKeyHide();
        }
        return super.onKeyPreIme(keyCode, event);
    }

    /**
     * 键盘监听接口
     */
    private OnKeyBoardHideListener onKeyBoardHideListener;

    public void setOnKeyBoardHideListener(OnKeyBoardHideListener onKeyBoardHideListener) {
        this.onKeyBoardHideListener = onKeyBoardHideListener;
    }

    public interface OnKeyBoardHideListener {
        void onKeyHide();
    }
}
