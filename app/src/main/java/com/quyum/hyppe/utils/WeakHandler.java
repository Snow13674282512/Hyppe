package com.quyum.hyppe.utils;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.quyum.hyppe.weight.KeyBoardDialogFragment;

public class WeakHandler extends Handler {
    KeyBoardDialogFragment dialogFragment;
    IHandler iHandler;

    public WeakHandler(KeyBoardDialogFragment dialogFragment) {
        this.dialogFragment = dialogFragment;
        iHandler = dialogFragment;
    }

    public interface IHandler {
        void handleMsg(Message message);
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        iHandler.handleMsg(msg);
    }
}
