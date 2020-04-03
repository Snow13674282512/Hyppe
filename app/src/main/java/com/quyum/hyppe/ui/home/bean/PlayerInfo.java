package com.quyum.hyppe.ui.home.bean;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.netease.neliveplayer.playerkit.sdk.VodPlayer;
import com.netease.neliveplayer.playerkit.sdk.VodPlayerObserver;
import com.netease.neliveplayer.playerkit.sdk.view.AdvanceTextureView;

public class PlayerInfo {
    public VodPlayer vodPlayer;
    public VodPlayerObserver playerObserver;
    public String playURL;
    public AdvanceTextureView advanceSingleTextureView;
    public LinearLayout bufferLayout;
    public ImageView coverImage;
    public int position;
    public boolean isPaused;
}
