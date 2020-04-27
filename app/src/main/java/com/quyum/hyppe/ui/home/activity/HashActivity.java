package com.quyum.hyppe.ui.home.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.weight.TitleBar;

import butterknife.BindView;

public class HashActivity extends BaseActivity {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_creator_tv)
    TextView contentCreatorTv;
    @BindView(R.id.orgin_location_tv)
    TextView orginLocationTv;
    @BindView(R.id.time_created_tv)
    TextView timeCreatedTv;
    @BindView(R.id.production_device_tv)
    TextView productionDeviceTv;
    @BindView(R.id.device_model_tv)
    TextView deviceModelTv;
    @BindView(R.id.screenwriter_tv)
    TextView screenwriterTv;
    @BindView(R.id.cast_tv)
    TextView castTv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.player)
    VideoView mVideoView;
    TiktokBean tiktokBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hash;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        bar.setTitleText("Hash");
        return true;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {
        Rect scrollBounds = new Rect();
        scrollview.getHitRect(scrollBounds);
        scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (mVideoView.getLocalVisibleRect(scrollBounds)) {
                    mVideoView.resume();
                    //子控件至少有一个像素在可视范围内
                    // Any portion of the childView, even a single pixel, is within the visible window
                } else {
                    mVideoView.pause();
                    //子控件完全不在可视范围内
                    // NONE of the childView is within the visible window
                }
            }
        });
    }

    @Override
    protected void initView() {
        mVideoView.setUrl(tiktokBean.videoDownloadUrl);
        StandardVideoController controller = new StandardVideoController(this);
        //根据屏幕方向自动进入/退出全屏
//        controller.setEnableOrientation(true);

        PrepareView prepareView = new PrepareView(this);//准备播放界面
        prepareView.setClickStart();
        ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
        Glide.with(this).load(tiktokBean.coverImgUrl).into(thumb);
        controller.addControlComponent(prepareView);

        controller.addControlComponent(new CompleteView(this));//自动完成播放界面

        controller.addControlComponent(new ErrorView(this));//错误界面

        TitleView titleView = new TitleView(this);//标题栏
        controller.addControlComponent(titleView);


        VodControlView vodControlView = new VodControlView(this);//点播控制条
        //是否显示底部进度条。默认显示
//                vodControlView.showBottomProgress(false);
        controller.addControlComponent(vodControlView);

        GestureView gestureControlView = new GestureView(this);//滑动控制视图
        controller.addControlComponent(gestureControlView);
        //根据是否为直播决定是否需要滑动调节进度
        controller.setCanChangePosition(true);

        //设置标题
        titleView.setTitle(tiktokBean.title);

        //注意：以上组件如果你想单独定制，我推荐你把源码复制一份出来，然后改成你想要的样子。
        //改完之后再通过addControlComponent添加上去
        //你也可以通过addControlComponent添加一些你自己的组件，具体实现方式参考现有组件的实现。
        //这个组件不一定是View，请发挥你的想象力😃

        //如果你不需要单独配置各个组件，可以直接调用此方法快速添加以上组件
//            controller.addDefaultControlComponent(title, isLive);

        //竖屏也开启手势操作，默认关闭
//            controller.setEnableInNormal(true);
        //滑动调节亮度，音量，进度，默认开启
//            controller.setGestureEnabled(false);
        //适配刘海屏，默认开启
//            controller.setAdaptCutout(false);
        mVideoView.setEnableAudioFocus(false);
        mVideoView.setVideoController(controller);
        mVideoView.addOnStateChangeListener(mOnStateChangeListener);

    }

    private VideoView.OnStateChangeListener mOnStateChangeListener = new VideoView.SimpleOnStateChangeListener() {
        @Override
        public void onPlayerStateChanged(int playerState) {
            switch (playerState) {
                case VideoView.PLAYER_NORMAL://小屏
                    break;
                case VideoView.PLAYER_FULL_SCREEN://全屏
                    break;
            }
        }

        @Override
        public void onPlayStateChanged(int playState) {
            switch (playState) {
                case VideoView.STATE_IDLE:
                    break;
                case VideoView.STATE_PREPARING:
                    //在STATE_PREPARING时设置setMute(true)可实现静音播放
//                    mVideoView.setMute(true);
                    break;
                case VideoView.STATE_PREPARED:
                    break;
                case VideoView.STATE_PLAYING:
                    //需在此时获取视频宽高
                    int[] videoSize = mVideoView.getVideoSize();
                    L.d("视频宽：" + videoSize[0]);
                    L.d("视频高：" + videoSize[1]);
                    break;
                case VideoView.STATE_PAUSED:
                    break;
                case VideoView.STATE_BUFFERING:
                    break;
                case VideoView.STATE_BUFFERED:
                    break;
                case VideoView.STATE_PLAYBACK_COMPLETED:
                    break;
                case VideoView.STATE_ERROR:
                    break;
            }
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        tiktokBean = (TiktokBean) getIntent().getSerializableExtra("data");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
    }

    @Override
    protected void doOnBackPressed() {
        super.doOnBackPressed();
        if (mVideoView == null || !mVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }

}
