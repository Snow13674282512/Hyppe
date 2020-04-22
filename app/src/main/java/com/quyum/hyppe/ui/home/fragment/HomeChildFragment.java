package com.quyum.hyppe.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.PlayerChildSwitchStopEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.activity.HashActivity;
import com.quyum.hyppe.ui.home.activity.MainActivity;
import com.quyum.hyppe.ui.home.adapter.CommentDialogSingleAdapter;
import com.quyum.hyppe.ui.home.adapter.TikTokAdapter;
import com.quyum.hyppe.ui.home.bean.EmojisBean;
import com.quyum.hyppe.ui.home.bean.FirstLevelBean;
import com.quyum.hyppe.ui.home.bean.GiftBean;
import com.quyum.hyppe.ui.home.bean.SecondLevelBean;
import com.quyum.hyppe.ui.home.bean.TiktokBean;
import com.quyum.hyppe.ui.home.bean.TopUpBean;
import com.quyum.hyppe.utils.DataUtil;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.utils.Utils;
import com.quyum.hyppe.utils.cache.PreloadManager;
import com.quyum.hyppe.utils.cache.ProxyVideoCacheManager;
import com.quyum.hyppe.weight.DialogBuilder;
import com.quyum.hyppe.weight.EmojisPopWindow;
import com.quyum.hyppe.weight.GiftPopWindow;
import com.quyum.hyppe.weight.InputTextMsgDialog;
import com.quyum.hyppe.weight.MyDialog;
import com.quyum.hyppe.weight.SharePopWindow;
import com.quyum.hyppe.weight.TitleBar;
import com.quyum.hyppe.weight.TopUpPopWindow;
import com.quyum.hyppe.weight.VerticalViewPager;
import com.quyum.hyppe.weight.controller.TikTokController;
import com.quyum.hyppe.weight.render.TikTokRenderViewFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class HomeChildFragment extends BaseFragment {
    @BindView(R.id.view_pager)
    ViewPager2 mViewPager;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private int mCurPos;
    private List<TiktokBean> mVideoList = new ArrayList<>();
    private PreloadManager mPreloadManager;
    private TikTokController mController;
    private RecyclerView mViewPagerImpl;
    private TikTokAdapter mTikTokAdapter;
    protected VideoView mVideoView;

    private int position = 0;
    GiftPopWindow giftPopWindow;
    EmojisPopWindow emojisPopWindow;
    SharePopWindow sharePopWindow;
    TopUpPopWindow topUpPopWindow;
    private View.OnClickListener giftOnClickListener;
    private View.OnClickListener followOnClickListener;
    private View.OnClickListener hashOnClickListener;
    private View.OnClickListener likeOnClickListener;
    private View.OnClickListener unLikeOnClickListener;
    private View.OnClickListener emojiOnClickListener;
    private View.OnClickListener forwardOnClickListener;
    private View.OnClickListener commentsOnClickListener;

    private List<FirstLevelBean> data = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private InputTextMsgDialog inputTextMsgDialog;
    private float slideOffset1 = 0;
    private String content = "我听见你的声音，有种特别的感觉。让我不断想，不敢再忘记你。如果真的有一天，爱情理想会实现，我会加倍努力好好对你，永远不改变";
    private CommentDialogSingleAdapter bottomSheetAdapter;
    private RecyclerView rv_dialog_lists;
    private long totalCount = 30;
    private int offsetY;
    Boolean isStart = false;
    MyDialog agreedDialog;

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
        setDataComments();
        showSheetDialog();
        initViewPager();
        initVideoView();
        mPreloadManager = PreloadManager.getInstance(getContext());

        addData(null);

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                if (position == 0) {
                    startPlay(0);
                }
            }
        });
    }

    private void initVideoView() {
        mVideoView = new VideoView(getContext());
        mVideoView.setLooping(true);
        //以下只能二选一，看你的需求
        mVideoView.setRenderViewFactory(TikTokRenderViewFactory.create());
//        mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);

        mController = new TikTokController(getContext());
        mVideoView.setVideoController(mController);

    }

    private void initViewPager() {
        listener();
        mViewPager.setOffscreenPageLimit(4);
        mTikTokAdapter = new TikTokAdapter(mVideoList, giftOnClickListener, followOnClickListener, hashOnClickListener,
                likeOnClickListener, unLikeOnClickListener, emojiOnClickListener, forwardOnClickListener, commentsOnClickListener);
        mViewPager.setAdapter(mTikTokAdapter);
        mViewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            private int mCurItem;

            /**
             * VerticalViewPager是否反向滑动
             */
            private boolean mIsReverseScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (position == mCurItem) {
                    return;
                }
                mIsReverseScroll = position < mCurItem;
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == mCurPos) return;
                if (position == mVideoList.size() - 1) {
                    int size = mVideoList.size();
                    mVideoList.addAll(DataUtil.getTiktokDataFromAssets(getContext()));
                    //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
                    mTikTokAdapter.notifyItemRangeChanged(size, mVideoList.size());
                }
                mViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        startPlay(position);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == VerticalViewPager.SCROLL_STATE_DRAGGING) {
                    mCurItem = mViewPager.getCurrentItem();
                }
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    mPreloadManager.resumePreload(mCurPos, mIsReverseScroll);
                } else {
                    mPreloadManager.pausePreload(mCurPos, mIsReverseScroll);
                }
            }
        });

        //ViewPage2内部是通过RecyclerView去实现的，它位于ViewPager2的第0个位置
        mViewPagerImpl = (RecyclerView) mViewPager.getChildAt(0);

    }

    private void startPlay(int position) {
        int count = mViewPagerImpl.getChildCount();
        for (int i = 0; i < count; i++) {
            View itemView = mViewPagerImpl.getChildAt(i);
            TikTokAdapter.ViewHolder viewHolder = (TikTokAdapter.ViewHolder) itemView.getTag();
            if (viewHolder.mPosition == position) {
                mVideoView.release();
                Utils.removeViewFormParent(mVideoView);
                TiktokBean tiktokBean = mVideoList.get(position);
                String playUrl = mPreloadManager.getPlayUrl(tiktokBean.videoDownloadUrl);
                L.i("startPlay: " + "position: " + position + "  url: " + playUrl);
                mVideoView.setUrl(playUrl);
                mController.addControlComponent(viewHolder.mTikTokView, true);
                viewHolder.mPlayerContainer.addView(mVideoView, 0);
                mVideoView.start();
                mCurPos = position;
                break;
            }
        }

    }

    public void addData(View view) {
        int size = mVideoList.size();
        mVideoList.addAll(DataUtil.getTiktokDataFromAssets(getContext()));
        //使用此方法添加数据，使用notifyDataSetChanged会导致正在播放的视频中断
        mTikTokAdapter.notifyItemRangeChanged(size, mVideoList.size());
    }

    //原始数据 一般是从服务器接口请求过来的
    private void setDataComments() {
        for (int i = 0; i < 11; i++) {
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setContent("第" + (i + 1) + "人评论内容" + (i % 3 == 0 ? content + (i + 1) + "次" : ""));
            firstLevelBean.setCreateTime(System.currentTimeMillis());
            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3370302115,85956606&fm=26&gp=0.jpg");
            firstLevelBean.setId(i + "");
            firstLevelBean.setUserId("UserId" + i);
            firstLevelBean.setIsLike(0);
            firstLevelBean.setPosition(i);
            firstLevelBean.setLikeCount(i);
            firstLevelBean.setUserName("星梦缘" + (i + 1));
            data.add(firstLevelBean);
        }

    }

    /**
     * 重新排列数据
     * 未解决滑动卡顿问题
     */
    private void sort() {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            FirstLevelBean firstLevelBean = data.get(i);
            firstLevelBean.setPosition(i);

            List<SecondLevelBean> secondLevelBeans = firstLevelBean.getSecondLevelBeans();
            if (secondLevelBeans == null || secondLevelBeans.isEmpty()) continue;
            int count = secondLevelBeans.size();
            for (int j = 0; j < count; j++) {
                SecondLevelBean secondLevelBean = secondLevelBeans.get(j);
                secondLevelBean.setPosition(i);
                secondLevelBean.setChildPosition(j);
            }
        }

        bottomSheetAdapter.notifyDataSetChanged();

    }

    private void showSheetDialog() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
            return;
        }
        View view = View.inflate(mActivity, R.layout.pop_comments, null);
        ImageView iv_dialog_close = view.findViewById(R.id.cancel_iv);
        ImageView emojiIv = view.findViewById(R.id.emoji_iv);
        TextView numberTv = view.findViewById(R.id.number_tv);

        rv_dialog_lists = view.findViewById(R.id.recycler_view);
        LinearLayout rl_comment = view.findViewById(R.id.bottom_ll);

        iv_dialog_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        rl_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initInputTextMsgDialog(null, false, null, -1, false);
            }
        });
        emojiIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initInputTextMsgDialog(null, false, null, -1, true);
            }
        });

        bottomSheetAdapter = new CommentDialogSingleAdapter();
        bottomSheetAdapter.setNewData(data);
        rv_dialog_lists.setHasFixedSize(true);
        rv_dialog_lists.setLayoutManager(new LinearLayoutManager(mActivity));
        rv_dialog_lists.setItemAnimator(new DefaultItemAnimator());

        bottomSheetAdapter.setLoadMoreView(new SimpleLoadMoreView());
        bottomSheetAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (data.size() >= totalCount) {
                    bottomSheetAdapter.loadMoreEnd(false);
                    return;
                }
                //加载更多
                for (int i = 0; i < 10; i++) {
                    FirstLevelBean firstLevelBean = new FirstLevelBean();
                    firstLevelBean.setUserName("赵丽颖 add more" + i);
                    firstLevelBean.setId(bottomSheetAdapter.getItemCount() + (i + 1) + "");
                    firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
                    firstLevelBean.setCreateTime(System.currentTimeMillis());
                    firstLevelBean.setContent("add more" + i);
                    firstLevelBean.setLikeCount(0);
                    firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
                    data.add(firstLevelBean);
                }
                sort();
                bottomSheetAdapter.loadMoreComplete();
            }
        }, rv_dialog_lists);

        rv_dialog_lists.setAdapter(bottomSheetAdapter);

        bottomSheetDialog = new BottomSheetDialog(mActivity, R.style.dialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset1 <= -0.28) {
                        bottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                slideOffset1 = slideOffset;
            }
        });

    }

    private void initInputTextMsgDialog(View view, final boolean isReply, final String headImg, final int position, Boolean isEmoji) {
        dismissInputDialog();
        if (view != null) {
            offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(mActivity, R.style.dialog_center);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    addComment(isReply, headImg, position, msg);
                }

                @Override
                public void dismiss() {
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();

    }

    //添加评论
    private void addComment(boolean isReply, String headImg, final int position, String msg) {
        if (position >= 0) {
            //添加二级评论
            SecondLevelBean secondLevelBean = new SecondLevelBean();
            FirstLevelBean firstLevelBean = bottomSheetAdapter.getData().get(position);
            secondLevelBean.setReplyUserName("replyUserName");
            secondLevelBean.setIsReply(isReply ? 1 : 0);
            secondLevelBean.setContent(msg);
            secondLevelBean.setHeadImg(headImg);
            secondLevelBean.setCreateTime(System.currentTimeMillis());
            secondLevelBean.setIsLike(0);
            secondLevelBean.setUserName("userName");
            secondLevelBean.setId(firstLevelBean.getSecondLevelBeans() + "");
            firstLevelBean.getSecondLevelBeans().add(secondLevelBean);

            data.set(firstLevelBean.getPosition(), firstLevelBean);
            bottomSheetAdapter.notifyDataSetChanged();
            rv_dialog_lists.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((LinearLayoutManager) rv_dialog_lists.getLayoutManager())
                            .scrollToPositionWithOffset(position == data.size() - 1 ? position
                                    : position + 1, position == data.size() - 1 ? Integer.MIN_VALUE : rv_dialog_lists.getHeight() / 2);
                }
            }, 100);

        } else {
            //添加一级评论
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setUserName("赵丽颖");
            firstLevelBean.setId(bottomSheetAdapter.getItemCount() + 1 + "");
            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            firstLevelBean.setCreateTime(System.currentTimeMillis());
            firstLevelBean.setContent(msg);
            firstLevelBean.setLikeCount(0);
            firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
            data.add(0, firstLevelBean);
            sort();
            rv_dialog_lists.scrollToPosition(0);
        }

    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }

    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }

    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;

    }

    // item滑动
    public void scrollLocation(int offsetY) {
        try {
            rv_dialog_lists.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStart(PlayerStartEvent playerStartEvent) {
        if (mVideoView != null) {
            if (position == 0 && MyApplication.isAtPlayerOne) {
                mVideoView.resume();
            } else if (position == 1 && MyApplication.isAtPlayerTwo) {
                if (!isStart) {
                    mViewPager.post(new Runnable() {
                        @Override
                        public void run() {
                            startPlay(0);
                            isStart = true;
                        }
                    });
                } else {
                    mVideoView.resume();
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
        }
        mPreloadManager.removeAllPreloadTask();
        //清除缓存，实际使用可以不需要清除，这里为了方便测试
        ProxyVideoCacheManager.clearAllCache(getContext());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStop(PlayerStopEvent playerStopEvent) {
        if (mVideoView != null) {
            mVideoView.pause();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStopChildSwitch(PlayerChildSwitchStopEvent playerChildSwitchStopEvent) {
        if (position == 0 && !MyApplication.isAtPlayerOne) {
            if (mVideoView != null) {
                mVideoView.pause();
            }
        } else if (position == 1 && !MyApplication.isAtPlayerTwo) {
            if (mVideoView != null) {
                mVideoView.pause();
            }
        }

    }

    void getGiftData() {
        List<GiftBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GiftBean.DataBean dataBean = new GiftBean.DataBean();
            dataBean.drawable = R.drawable.img_liwu_2;
            dataBean.title = "50Credit/Point" + i;
            list.add(dataBean);
            giftPopWindow.getAdapter().setNewData(list);
        }
    }

    void getEmojisData() {
        List<EmojisBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            EmojisBean.DataBean dataBean = new EmojisBean.DataBean();
            dataBean.drawable = R.drawable.icon_emojis_1;
            dataBean.title = "50Credit/Point" + i;
            list.add(dataBean);
            emojisPopWindow.getAdapter().setNewData(list);
        }
    }

    void getTopUpData() {
        List<TopUpBean.DataBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TopUpBean.DataBean dataBean = new TopUpBean.DataBean();
            dataBean.price = String.valueOf((i + 1) * 100);
            dataBean.num = String.valueOf((i + 1) * 10);
            list.add(dataBean);
            topUpPopWindow.getAdapter().setNewData(list);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRootView.setTag(position);

        initGiftPop();
        getGiftData();

        initEmojisPop();
        getEmojisData();

        initTopUpPop();
        getTopUpData();

        initSharePop();
    }

    void initGiftPop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        giftPopWindow = new GiftPopWindow(mainActivity.getMainRl(), mActivity, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giftPopWindow.close();
                if (mVideoView != null) {
                    mVideoView.pause();
                }
                topUpPopWindow.show();
            }
        });
        giftPopWindow.setOnCloseListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mVideoView != null) {
                    mVideoView.resume();
                }
            }
        });
    }

    void initTopUpPop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        topUpPopWindow = new TopUpPopWindow(mainActivity.getMainRl(), mActivity);
        topUpPopWindow.setOnCloseListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mVideoView != null) {
                    mVideoView.resume();
                }
            }
        });
    }

    void initEmojisPop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        emojisPopWindow = new EmojisPopWindow(mainActivity.getMainRl(), mActivity);
        emojisPopWindow.setOnCloseListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mVideoView != null) {
                    mVideoView.resume();
                }
            }
        });
    }

    void initSharePop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        sharePopWindow = new SharePopWindow(mainActivity.getMainRl(), mActivity, mVideoList.get(mCurPos).videoDownloadUrl, mVideoList.get(mCurPos).videoPlayUrl);
        sharePopWindow.setOnCloseListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mVideoView != null) {
                    mVideoView.resume();
                }
            }
        });
    }

    void initAgreedDialog() {
        agreedDialog = DialogBuilder.selectDialog(mActivity, "Acknowledge", "Message “The ownership of this work belongs to wuhuh dasima. You are not allowed to download this content. If you would like to have the right to download this content, please click the more information or private message to consult with the owner. If malicious happened, the owner has the rights and evidences to file a lawsuit against the downloader.”",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        agreedDialog.cancel();
                        if (giftPopWindow.getAdapter().getData().size() > 0) {
                            giftPopWindow.show();
                            if (mVideoView != null) {
                                mVideoView.pause();
                            }
                        } else {
                            ToastUtils.showToast("暂未获取到礼物数据,请稍后再试");
                        }
                    }
                });
        agreedDialog.show();
    }

    void listener() {
        giftOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (SystemParams.getInstance().getGiftAgreement()) {
                    if (giftPopWindow.getAdapter().getData().size() > 0) {
                        giftPopWindow.show();
                        if (mVideoView != null) {
                            mVideoView.pause();
                        }
                    } else {
                        ToastUtils.showToast("暂未获取到礼物数据,请稍后再试");
                    }
                } else {
                    initAgreedDialog();
                    SystemParams.getInstance().setGiftAgreement(true);
                }
            }
        };
        followOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
            }
        };
        likeOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
            }
        };
        unLikeOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
            }
        };
        emojiOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (emojisPopWindow.getAdapter().getData().size() > 0) {
                    emojisPopWindow.show();
                    if (mVideoView != null) {
                        mVideoView.pause();
                    }
                } else {
                    ToastUtils.showToast("暂未获取到表情数据,请稍后再试");
                    getEmojisData();
                }
            }
        };
        hashOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                startActivity(new Intent(mActivity, HashActivity.class));
            }
        };
        forwardOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                sharePopWindow.show();
                if (mVideoView != null) {
                    mVideoView.pause();
                }
            }
        };
        commentsOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (mVideoView != null) {
                    mVideoView.pause();
                }
                bottomSheetAdapter.notifyDataSetChanged();
                slideOffset1 = 0;
                bottomSheetDialog.show();
            }
        };
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mVideoView != null) {
                    mVideoView.resume();
                }
            }
        });

    }

    @Override
    protected void addListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mVideoList.clear();
                mVideoList.addAll(DataUtil.getTiktokDataFromAssets(getContext()));
                mViewPager.post(new Runnable() {
                    @Override
                    public void run() {
                        startPlay(0);
                    }
                });
                mTikTokAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onLazyLoad() {

    }

}
