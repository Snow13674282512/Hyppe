package com.quyum.hyppe.weight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.lifecycle.LifecycleObserver;

import com.quyum.hyppe.R;
import com.quyum.hyppe.event.CommentsOpenEvent;
import com.quyum.hyppe.utils.IKeyBoard;

import org.greenrobot.eventbus.EventBus;

public class CommentsUtils implements IKeyBoard, LifecycleObserver {
    private Context context;
    private View rootView;
    private LinearLayout bottomLl;
    CommentsPopWindow commentsPopWindow;

    public CommentsUtils(RelativeLayout rootRl, Context context) {
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.pop_comments, null);
        bottomLl = rootView.findViewById(R.id.bottom_ll);
        commentsPopWindow = new CommentsPopWindow(rootView, context);
        initListener();
    }

    void initListener() {
        bottomLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsPopWindow.show();
            }
        });
    }


//    @Override
//    public TextEditTextView getEditTextView() {
//        return contentEt;
//    }

    @Override
    public View getRoot() {
        return rootView;
    }

    @Override
    public void onDismiss() {
        commentsPopWindow.close();
    }

}
