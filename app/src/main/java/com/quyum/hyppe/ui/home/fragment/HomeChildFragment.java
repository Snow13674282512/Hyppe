package com.quyum.hyppe.ui.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.adapter.VodPagerAdapter;
import com.quyum.hyppe.ui.home.bean.PlayerInfo;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.VerticalViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static androidx.constraintlayout.widget.Constraints.TAG;

@SuppressLint("ValidFragment")
public class HomeChildFragment extends BaseFragment {
    @BindView(R.id.view_pager)
    VerticalViewPager mViewPager;
    private VodPagerAdapter mPagerAdapter;
    private int mCurrentPosition = 0;
    private PlayerInfo mPlayerInfo;
    private List<String> mLiveUrlList = new ArrayList<>();
    private int bufferPageCount = 1;
    //    @BindView(R.id.icon_iv)
//    CircleImageView iconIv;
//    @BindView(R.id.name_tv)
//    TextView nameTv;
//    @BindView(R.id.follow_bt)
//    Button followBt;
//    @BindView(R.id.hash_tv)
//    TextView hashTv;
//    @BindView(R.id.like_tv)
//    TextView likeTv;
//    @BindView(R.id.un_like_tv)
//    TextView unLikeTv;
//    @BindView(R.id.forward_tv)
//    TextView forwardTv;
//    @BindView(R.id.look_comments_bt)
//    Button lookCommentsBt;
//    @BindView(R.id.title_tv)
//    TextView titleTv;
//    @BindView(R.id.expression_tv)
//    TextView expressionTv;
    private int position = 0;
//    Boolean isFollow = false;
//    GiftPopWindow giftPopWindow;
//    EmojisPopWindow emojisPopWindow;

    public static HomeChildFragment newInstance(int position) {
        HomeChildFragment fragment = new HomeChildFragment();
        fragment.position = position;
        return fragment;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.fragment_home_child;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected void initData(Bundle arguments) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        MyApplication.isAtPlayer = true;
        setData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStart(PlayerStartEvent playerStartEvent) {
        if (mPlayerInfo != null) {
            mPlayerInfo.vodPlayer.start();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStop(PlayerStopEvent playerStopEvent) {
        if (mPlayerInfo != null) {
            mPlayerInfo.vodPlayer.pause();
        }
    }

//    void getGiftData() {
//        List<GiftBean.DataBean> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            GiftBean.DataBean dataBean = new GiftBean.DataBean();
//            dataBean.drawable = R.drawable.img_liwu_2;
//            dataBean.title = "50Credit/Point" + i;
//            list.add(dataBean);
//            giftPopWindow.getAdapter().setNewData(list);
//        }
//    }
//
//    void getEmojisData() {
//        List<EmojisBean.DataBean> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            EmojisBean.DataBean dataBean = new EmojisBean.DataBean();
//            dataBean.drawable = R.drawable.icon_emojis_1;
//            dataBean.title = "50Credit/Point" + i;
//            list.add(dataBean);
//            emojisPopWindow.getAdapter().setNewData(list);
//        }
//    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRootView.setTag(position);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                Log.i(TAG, "滑动后，让之前的播放器暂停，old position = " + (mPlayerInfo == null ? null : mPlayerInfo.position));
                if (mPlayerInfo != null) {
                    mPlayerInfo.vodPlayer.setBufferSize(20 * 1024 * 1024);
                    mPlayerInfo.vodPlayer.seekTo(0);
                    mPlayerInfo.vodPlayer.setMute(true);
                    mPlayerInfo.vodPlayer.pause();
                    mPlayerInfo.isPaused = true;
                }
                mCurrentPosition = position;
                mPagerAdapter.setCurrentPosition(mCurrentPosition);
                mPagerAdapter.setPlayerInfo(mPlayerInfo);
                mPlayerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (mPlayerInfo != null && mPlayerInfo.isPaused) {
                    Log.i(TAG, "play start,transformPage position = " + mCurrentPosition);
                    //当前页面视频缓存50m
                    mPlayerInfo.vodPlayer.setBufferSize(50 * 1024 * 1024);
                    mPlayerInfo.vodPlayer.setMute(false);
                    mPlayerInfo.vodPlayer.start();
                    mPlayerInfo.isPaused = false;
                } else {
                    Log.i(TAG, "mPlayerInfo = " + mPlayerInfo);

                }
            }
        });

        mPagerAdapter = new VodPagerAdapter(mActivity, mLiveUrlList);
        mViewPager.setOffscreenPageLimit(bufferPageCount);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);

//        initGiftPop();
//        getGiftData();
//
//        initEmojisPop();
//        getEmojisData();
    }

    void setData() {
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/7eSdPRKt_1818589543_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/1BrIAtvV_1818587477_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/FmdVOTqd_1818586962_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/wq1e35cQ_1818588221_shd.mp4");
        mPagerAdapter.notifyDataSetChanged();
    }

//    void initGiftPop() {
//        MainActivity mainActivity = (MainActivity) getActivity();
//        giftPopWindow = new GiftPopWindow(mainActivity.getMainRl(), mActivity);
//    }
//
//    void initEmojisPop() {
//        MainActivity mainActivity = (MainActivity) getActivity();
//        emojisPopWindow = new EmojisPopWindow(mainActivity.getMainRl(), mActivity, expressionTv);
//    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onLazyLoad() {

    }

//    @OnClick({R.id.hash_ll, R.id.icon_iv, R.id.follow_bt, R.id.gift_iv, R.id.like_ll, R.id.un_like_ll, R.id.expression_ll, R.id.forward_ll, R.id.look_comments_bt})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.hash_ll:
//                startActivity(new Intent(mActivity, HashActivity.class));
//                break;
//            case R.id.icon_iv:
//                break;
//            case R.id.follow_bt:
//                if (isFollow) {
//                    followBt.setBackgroundResource(R.drawable.bg_un_follow);
//                    followBt.setText("+Follow");
//                    isFollow = false;
//                } else {
//                    followBt.setBackgroundResource(R.drawable.bg_follow);
//                    followBt.setText("+Followed");
//                    isFollow = true;
//                }
//                break;
//            case R.id.gift_iv:
//                if (giftPopWindow.getAdapter().getData().size() > 0) {
//                    giftPopWindow.show();
//                } else {
//                    ToastUtils.showToast("暂未获取到礼物数据,请稍后再试");
//                }
//                break;
//            case R.id.like_ll:
//                break;
//            case R.id.un_like_ll:
//                break;
//            case R.id.expression_ll:
//                if (emojisPopWindow.getAdapter().getData().size() > 0) {
//                    emojisPopWindow.show();
//                } else {
//                    ToastUtils.showToast("暂未获取到表情数据,请稍后再试");
//                    getEmojisData();
//                }
//                break;
//            case R.id.forward_ll:
//                break;
//            case R.id.look_comments_bt:
//                MainActivity mainActivity = (MainActivity) getActivity();
//                KeyBoardDialogFragment keyBoardDialogFragment = KeyBoardDialogFragment.newInstance(new CommentsUtils(mainActivity.getMainRl(), mActivity));
//                keyBoardDialogFragment.show(getChildFragmentManager(), "edit");
////                if (commentsPopWindow.getAdapter().getData().size() > 0) {
////                    commentsPopWindow.show();
////                } else {
////                    ToastUtils.showToast("暂未获取到评论数据,请稍后再试");
////                }
//                break;
//        }
//    }

}
