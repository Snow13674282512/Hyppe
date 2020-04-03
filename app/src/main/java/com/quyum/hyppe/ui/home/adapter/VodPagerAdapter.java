package com.quyum.hyppe.ui.home.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.netease.neliveplayer.playerkit.sdk.PlayerManager;
import com.netease.neliveplayer.playerkit.sdk.VodPlayer;
import com.netease.neliveplayer.playerkit.sdk.VodPlayerObserver;
import com.netease.neliveplayer.playerkit.sdk.model.AutoRetryConfig;
import com.netease.neliveplayer.playerkit.sdk.model.CacheConfig;
import com.netease.neliveplayer.playerkit.sdk.model.DataSourceConfig;
import com.netease.neliveplayer.playerkit.sdk.model.VideoBufferStrategy;
import com.netease.neliveplayer.playerkit.sdk.model.VideoOptions;
import com.netease.neliveplayer.playerkit.sdk.model.VideoScaleMode;
import com.netease.neliveplayer.playerkit.sdk.view.AdvanceTextureView;
import com.netease.neliveplayer.sdk.model.NEAutoRetryConfig;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.home.bean.PlayerInfo;
import com.quyum.hyppe.utils.player.ShortPlayerObserver;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.quyum.hyppe.utils.ToastUtils.showToast;

public class VodPagerAdapter extends PagerAdapter {
    private ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
    private List<String> liveUrlList;
    private Context context;
    private int mCurrentPosition = 0;
    private PlayerInfo mPlayerInfo;
    private AutoRetryConfig autoRetryConfig;
    private boolean isErrorShow = false;

    public VodPagerAdapter(Context context, List<String> liveUrlList) {
        this.liveUrlList = liveUrlList;
        this.context = context;
        autoRetryConfig = new AutoRetryConfig();
        autoRetryConfig.count = 0;
        autoRetryConfig.delayDefault = 3000;
        autoRetryConfig.retryListener = onRetryListener;
    }

    public void setCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public void setPlayerInfo(PlayerInfo mPlayerInfo) {
        this.mPlayerInfo = mPlayerInfo;
    }

    private NEAutoRetryConfig.OnRetryListener onRetryListener = new NEAutoRetryConfig.OnRetryListener() {

        @Override
        public void onRetry(int what, int extra) {
            showToast("开始重试，错误类型：" + what + "，附加信息：" + extra);
        }
    };

    @Override
    public int getCount() {
        if (liveUrlList == null || liveUrlList.size() < 1) {
            return 0;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i(TAG, "instantiateItem " + position);

        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.texture_item_layout, null);
        view.setId(position);

        // 获取此player
        PlayerInfo playerInfo = instantiatePlayerInfo(position);
        AdvanceTextureView advanceSingleTextureView = view.findViewById(R.id.live_texture);
        LinearLayout bufferLayout = view.findViewById(R.id.buffering_prompt);
        ImageView coverImage = view.findViewById(R.id.render_cover);
        bufferLayout.setVisibility(View.VISIBLE);
        coverImage.setVisibility(View.VISIBLE);
        playerInfo.advanceSingleTextureView = advanceSingleTextureView;
        playerInfo.bufferLayout = bufferLayout;
        playerInfo.coverImage = coverImage;
        playerInfo.vodPlayer.setupRenderView(advanceSingleTextureView, VideoScaleMode.FIT);

        playerInfo.vodPlayer.setMute(true);
        if (mCurrentPosition == position) {
            mPlayerInfo = playerInfo;
        }
        container.addView(view);

        return view;
    }

    protected PlayerInfo instantiatePlayerInfo(final int position) {
        Log.i(TAG, "instantiatePlayerInfo " + position);
        PlayerInfo playerInfo = new PlayerInfo();
        VideoOptions options = new VideoOptions();
        options.bufferSize = 50 * 1024 * 1024;
        options.hardwareDecode = true;
        options.bufferStrategy = VideoBufferStrategy.ANTI_JITTER;
        options.loopCount = -1;
        options.isAccurateSeek = true;
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.cacheConfig = new CacheConfig(true, null);
        options.dataSourceConfig = dataSourceConfig;
        VodPlayer vodPlayer = PlayerManager.buildVodPlayer(context, liveUrlList.get(position % liveUrlList.size()), options);
        VodPlayerObserver playerObserver = new ShortPlayerObserver(position) {

            @Override
            public void onError(int code, int extra) {
                Log.i(TAG, "player mCurrentPosition:" + mCurrentPosition + " ,onError code:" + code + " extra:" + extra);
                pauseOtherPlayerInfo(mCurrentPosition);
                if (!isErrorShow && !findPlayerInfo(mCurrentPosition).vodPlayer.isPlaying()) {
                    isErrorShow = true;
                    //发生错误时，这里Demo会弹出对话框提示错误，用户可以在此根据错误码进行重试或者其他操作
                    AlertDialog.Builder build = new AlertDialog.Builder(context);
                    build.setTitle("播放错误").setMessage("错误码：" + code)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    isErrorShow = false;
                                    PlayerInfo curPlayInfo = findPlayerInfo(mCurrentPosition);
                                    curPlayInfo.coverImage.setVisibility(View.GONE);
                                    curPlayInfo.bufferLayout.setVisibility(View.GONE);
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }

            @Override
            public void onFirstVideoRendered() {
                Log.i(TAG, "onFirstVideoRendered，mCurrentPosition->" + mCurrentPosition + ",getPosition()->" + getPosition());
                PlayerInfo curPlayInfo = findPlayerInfo(getPosition());
                curPlayInfo.coverImage.setVisibility(View.GONE);
                curPlayInfo.bufferLayout.setVisibility(View.GONE);
                curPlayInfo.vodPlayer.setupRenderView(curPlayInfo.advanceSingleTextureView, VideoScaleMode.FIT);
                if (mCurrentPosition == getPosition()) {
                    curPlayInfo.vodPlayer.setMute(false);
                    return;
                }
                pausePlayerInfo(getPosition());
            }

        };
        vodPlayer.setAutoRetryConfig(autoRetryConfig);
        vodPlayer.registerPlayerObserver(playerObserver, true);
        Log.i(TAG, "play start,instantiatePlayerInfo position" + position + "，vodPlayer" + vodPlayer);
        vodPlayer.start();
        playerInfo.playURL = liveUrlList.get(position % liveUrlList.size());
        playerInfo.vodPlayer = vodPlayer;
        playerInfo.playerObserver = playerObserver;
        playerInfo.position = position;
        playerInfoList.add(playerInfo);
        return playerInfo;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.i(TAG, "destroyItem" + position);
        destroyPlayerInfo(position);
        container.removeView((View) object);
    }

    public void destroy() {
        for (PlayerInfo playerInfo : playerInfoList) {
            if (playerInfo == null || playerInfo.vodPlayer == null) {
                return;
            }
            playerInfo.vodPlayer.registerPlayerObserver(playerInfo.playerObserver, false);
            playerInfo.vodPlayer.setMute(true);
            playerInfo.vodPlayer.stop();
            playerInfo.vodPlayer = null;
        }
        playerInfoList.clear();
        playerInfoList = null;
        autoRetryConfig = null;
    }


    public void destroyPlayerInfo(int position) {
        PlayerInfo playerInfo = findPlayerInfo(position);
        if (playerInfo == null || playerInfo.vodPlayer == null) {
            return;
        }
        playerInfo.vodPlayer.stop();
        playerInfo.vodPlayer = null;
        playerInfoList.remove(playerInfo);

        Log.i(TAG, "play stop,destroyPlayerInfo position " + position);
    }

    public void pausePlayerInfo(int position) {
        PlayerInfo playerInfo = findPlayerInfo(position);
        if (playerInfo == null || playerInfo.vodPlayer == null) {
            return;
        }
        if (!playerInfo.isPaused) {
            playerInfo.vodPlayer.setMute(true);
            playerInfo.vodPlayer.pause();
            playerInfo.isPaused = true;
            Log.i(TAG, "play pause,pausePlayerInfo position " + position);
        }

    }

    public void pauseOtherPlayerInfo(int position) {
        for (PlayerInfo playerInfo : playerInfoList) {
            if (playerInfo.position != position && playerInfo.vodPlayer.isPlaying()) {
                playerInfo.vodPlayer.setMute(true);
                //其他页面视频缓存20m
                playerInfo.vodPlayer.setBufferSize(20 * 1024 * 1024);
                playerInfo.vodPlayer.pause();
                playerInfo.isPaused = true;
                Log.i(TAG, "play pause,pausePlayerInfo position " + playerInfo.position);
            }
        }

    }


    public PlayerInfo findPlayerInfo(int position) {
        for (int i = 0; i < playerInfoList.size(); i++) {
            PlayerInfo playerInfo = playerInfoList.get(i);
            if (playerInfo.position == position) {
                return playerInfo;
            }
        }
        return null;
    }

}
