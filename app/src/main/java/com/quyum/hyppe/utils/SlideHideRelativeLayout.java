package com.quyum.hyppe.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class SlideHideRelativeLayout extends RelativeLayout {
    private float mPosY;    //手指按下时Y轴的值
    private float mCurPosY; //手指移动时Y轴的值
    private boolean isTop = true;//判断是否到达顶部
    private CloseView callBack;

    //滑动到顶部时如果继续下滑则回调此方法
    public interface CloseView {
        void colseCallBack();
    }


    public void setCloseViewListener(CloseView callBack){
        this.callBack = callBack;
    }


    public SlideHideRelativeLayout(Context context) {
        super(context);
    }

    public SlideHideRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideHideRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPosY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mCurPosY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (mCurPosY - mPosY > 0 && (Math.abs(mCurPosY - mPosY) > 10)) {
                    //向下滑動
                    if (isTop) {
                        callBack.colseCallBack();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

}
