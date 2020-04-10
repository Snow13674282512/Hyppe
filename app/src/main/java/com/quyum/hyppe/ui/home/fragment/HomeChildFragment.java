package com.quyum.hyppe.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.PlayerChildSwitchStopEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.activity.HashActivity;
import com.quyum.hyppe.ui.home.activity.MainActivity;
import com.quyum.hyppe.ui.home.adapter.CommentDialogMutiAdapter;
import com.quyum.hyppe.ui.home.adapter.VodPagerAdapter;
import com.quyum.hyppe.ui.home.bean.CommentEntity;
import com.quyum.hyppe.ui.home.bean.CommentMoreBean;
import com.quyum.hyppe.ui.home.bean.EmojisBean;
import com.quyum.hyppe.ui.home.bean.FirstLevelBean;
import com.quyum.hyppe.ui.home.bean.GiftBean;
import com.quyum.hyppe.ui.home.bean.PlayerInfo;
import com.quyum.hyppe.ui.home.bean.SecondLevelBean;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.EmojisPopWindow;
import com.quyum.hyppe.weight.GiftPopWindow;
import com.quyum.hyppe.weight.InputTextMsgDialog;
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
public class HomeChildFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.view_pager)
    VerticalViewPager mViewPager;

    private VodPagerAdapter mPagerAdapter;
    private int mCurrentPosition = 0;
    private PlayerInfo mPlayerInfo;
    private List<String> mLiveUrlList = new ArrayList<>();
    private int bufferPageCount = 1;
    private int position = 0;
    GiftPopWindow giftPopWindow;
    EmojisPopWindow emojisPopWindow;
    private View.OnClickListener giftOnClickListener;
    private View.OnClickListener followOnClickListener;
    private View.OnClickListener hashOnClickListener;
    private View.OnClickListener likeOnClickListener;
    private View.OnClickListener unLikeOnClickListener;
    private View.OnClickListener emojiOnClickListener;
    private View.OnClickListener forwardOnClickListener;
    private View.OnClickListener commentsOnClickListener;

    private List<MultiItemEntity> data = new ArrayList<>();
    private List<FirstLevelBean> datas = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private InputTextMsgDialog inputTextMsgDialog;
    private float slideOffset1 = 0;
    private String content = "我听见你的声音，有种特别的感觉。让我不断想，不敢再忘记你。如果真的有一天，爱情理想会实现，我会加倍努力好好对你，永远不改变";
    private CommentDialogMutiAdapter bottomSheetAdapter;
    private RecyclerView rv_dialog_lists;
    private long totalCount = 22;
    private int offsetY;


    public static HomeChildFragment newInstance(int position) {
        HomeChildFragment fragment = new HomeChildFragment();
        fragment.position = position;
        return fragment;
    }

    public void setPlayerInfo(PlayerInfo mPlayerInfo) {
        this.mPlayerInfo = mPlayerInfo;
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
        dataSort(0);
        showSheetDialog();
    }

    private void initRefresh() {
        datas.clear();
        setDataComments();
        dataSort(0);
        bottomSheetAdapter.setNewData(data);
    }

    //原始数据 一般是从服务器接口请求过来的
    private void setDataComments() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setContent("第" + (i + 1) + "人评论内容" + (i % 3 == 0 ? content + (i + 1) + "次" : ""));
            firstLevelBean.setCreateTime(System.currentTimeMillis());
            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3370302115,85956606&fm=26&gp=0.jpg");
            firstLevelBean.setId(i + "");
            firstLevelBean.setIsLike(0);
            firstLevelBean.setLikeCount(i);
            firstLevelBean.setUserName("星梦缘" + (i + 1));
            firstLevelBean.setTotalCount(i + size);

            List<SecondLevelBean> beans = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                SecondLevelBean secondLevelBean = new SecondLevelBean();
                secondLevelBean.setContent("一级第" + (i + 1) + "人 二级第" + (j + 1) + "人评论内容" + (j % 3 == 0 ? content + (j + 1) + "次" : ""));
                secondLevelBean.setCreateTime(System.currentTimeMillis());
                secondLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
                secondLevelBean.setId(j + "");
                secondLevelBean.setIsLike(0);
                secondLevelBean.setLikeCount(j);
                secondLevelBean.setUserName("星梦缘" + (i + 1) + "  " + (j + 1));
                secondLevelBean.setIsReply(j % 5 == 0 ? 1 : 0);
                secondLevelBean.setReplyUserName(j % 5 == 0 ? "闭嘴家族" + j : "");
                secondLevelBean.setTotalCount(firstLevelBean.getTotalCount());
                beans.add(secondLevelBean);
                firstLevelBean.setSecondLevelBeans(beans);
            }
            datas.add(firstLevelBean);
        }
    }

    /**
     * 对数据重新进行排列
     * 目的是为了让一级评论和二级评论同为item
     * 解决滑动卡顿问题
     *
     * @param position
     */
    private void dataSort(int position) {
        if (datas.isEmpty()) {
            data.add(new MultiItemEntity() {
                @Override
                public int getItemType() {
                    return CommentEntity.TYPE_COMMENT_EMPTY;
                }
            });
            return;
        }

        if (position <= 0) data.clear();
        int posCount = data.size();
        int count = datas.size();
        for (int i = 0; i < count; i++) {
            if (i < position) continue;

            //一级评论
            FirstLevelBean firstLevelBean = datas.get(i);
            if (firstLevelBean == null) continue;
            firstLevelBean.setPosition(i);
            posCount += 2;
            List<SecondLevelBean> secondLevelBeans = firstLevelBean.getSecondLevelBeans();
            if (secondLevelBeans == null || secondLevelBeans.isEmpty()) {
                firstLevelBean.setPositionCount(posCount);
                data.add(firstLevelBean);
                continue;
            }
            int beanSize = secondLevelBeans.size();
            posCount += beanSize;
            firstLevelBean.setPositionCount(posCount);
            data.add(firstLevelBean);

            //二级评论
            for (int j = 0; j < beanSize; j++) {
                SecondLevelBean secondLevelBean = secondLevelBeans.get(j);
                secondLevelBean.setChildPosition(j);
                secondLevelBean.setPosition(i);
                secondLevelBean.setPositionCount(posCount);
                data.add(secondLevelBean);
            }

            //展示更多的item
            if (beanSize <= 18) {
                CommentMoreBean moreBean = new CommentMoreBean();
                moreBean.setPosition(i);
                moreBean.setPositionCount(posCount);
                moreBean.setTotalCount(firstLevelBean.getTotalCount());
                data.add(moreBean);
            }

        }
    }

    private void showSheetDialog() {
        if (bottomSheetDialog != null) {
            return;
        }
        View view = View.inflate(getContext(), R.layout.pop_comments, null);
        ImageView iv_dialog_close = view.findViewById(R.id.cancel_iv);
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
                //添加二级评论
                initInputTextMsgDialog(null, false, null, -1);
            }
        });

        bottomSheetAdapter = new CommentDialogMutiAdapter(data);
        rv_dialog_lists.setHasFixedSize(true);
        rv_dialog_lists.setLayoutManager(new LinearLayoutManager(getContext()));
        closeDefaultAnimator(rv_dialog_lists);
        bottomSheetAdapter.setOnLoadMoreListener(this, rv_dialog_lists);
        rv_dialog_lists.setAdapter(bottomSheetAdapter);

        bottomSheetAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view1, int position) {
                switch ((int) view1.getTag()) {
                    case CommentEntity.TYPE_COMMENT_PARENT:
                        if (view1.getId() == R.id.rl_group) {
                            //添加二级评论
                            initInputTextMsgDialog((View) view1.getParent(), false, bottomSheetAdapter.getData().get(position), position);
                        } else if (view1.getId() == R.id.ll_like) {
                            //一级评论点赞 项目中还得通知服务器 成功才可以修改
                            FirstLevelBean bean = (FirstLevelBean) bottomSheetAdapter.getData().get(position);
                            bean.setLikeCount(bean.getLikeCount() + (bean.getIsLike() == 0 ? 1 : -1));
                            bean.setIsLike(bean.getIsLike() == 0 ? 1 : 0);
                            datas.set(bean.getPosition(), bean);
                            dataSort(0);
                            bottomSheetAdapter.notifyDataSetChanged();
                        }
                        break;
                    case CommentEntity.TYPE_COMMENT_CHILD:

                        if (view1.getId() == R.id.rl_group) {
                            //添加二级评论（回复）
                            initInputTextMsgDialog(view1, true, bottomSheetAdapter.getData().get(position), position);
                        } else if (view1.getId() == R.id.ll_like) {
                            //二级评论点赞 项目中还得通知服务器 成功才可以修改
                            SecondLevelBean bean = (SecondLevelBean) bottomSheetAdapter.getData().get(position);
                            bean.setLikeCount(bean.getLikeCount() + (bean.getIsLike() == 0 ? 1 : -1));
                            bean.setIsLike(bean.getIsLike() == 0 ? 1 : 0);

                            List<SecondLevelBean> secondLevelBeans = datas.get((int) bean.getPosition()).getSecondLevelBeans();
                            secondLevelBeans.set(bean.getChildPosition(), bean);
//                            CommentMultiActivity.this.dataSort(0);
                            bottomSheetAdapter.notifyDataSetChanged();
                        }

                        break;
                    case CommentEntity.TYPE_COMMENT_MORE:
                        //在项目中是从服务器获取数据，其实就是二级评论分页获取
                        CommentMoreBean moreBean = (CommentMoreBean) bottomSheetAdapter.getData().get(position);
                        SecondLevelBean secondLevelBean = new SecondLevelBean();
                        secondLevelBean.setContent("more comment" + 1);
                        secondLevelBean.setCreateTime(System.currentTimeMillis());
                        secondLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
                        secondLevelBean.setId(1 + "");
                        secondLevelBean.setIsLike(0);
                        secondLevelBean.setLikeCount(0);
                        secondLevelBean.setUserName("星梦缘" + 1);
                        secondLevelBean.setIsReply(0);
                        secondLevelBean.setReplyUserName("闭嘴家族" + 1);
                        secondLevelBean.setTotalCount(moreBean.getTotalCount() + 1);

                        datas.get((int) moreBean.getPosition()).getSecondLevelBeans().add(secondLevelBean);
                        dataSort(0);
                        bottomSheetAdapter.notifyDataSetChanged();

                        break;
                    case CommentEntity.TYPE_COMMENT_EMPTY:
                        initRefresh();
                        break;

                }

            }
        });

        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.dialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());

        //dialog滑动监听
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset1 <= -0.28) {
                        //当向下滑动时 值为负数
                        bottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                slideOffset1 = slideOffset;//记录滑动值
            }
        });
    }

    int positionCount = 0;

    private void initInputTextMsgDialog(View view, final boolean isReply, final MultiItemEntity item, final int position) {
        dismissInputDialog();
        if (view != null) {
            offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(getContext(), R.style.dialog_center);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    addComment(isReply, item, position, msg);
                }

                @Override
                public void dismiss() {
                    //item滑动到原位
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();
    }

    //添加评论
    private void addComment(boolean isReply, MultiItemEntity item, final int position, String msg) {
        final String userName = "hui";
        if (position >= 0) {
            //添加二级评论
            int pos = 0;
            String replyUserName = "未知";
            if (item instanceof FirstLevelBean) {
                FirstLevelBean firstLevelBean = (FirstLevelBean) item;
                positionCount = (int) (firstLevelBean.getPositionCount() + 1);
                pos = (int) firstLevelBean.getPosition();
                replyUserName = firstLevelBean.getUserName();
            } else if (item instanceof SecondLevelBean) {
                SecondLevelBean secondLevelBean = (SecondLevelBean) item;
                positionCount = (int) (secondLevelBean.getPositionCount() + 1);
                pos = (int) secondLevelBean.getPosition();
                replyUserName = secondLevelBean.getUserName();
            }

            SecondLevelBean secondLevelBean = new SecondLevelBean();
            secondLevelBean.setReplyUserName(replyUserName);
            secondLevelBean.setIsReply(isReply ? 1 : 0);
            secondLevelBean.setContent(msg);
            secondLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3370302115,85956606&fm=26&gp=0.jpg");
            secondLevelBean.setCreateTime(System.currentTimeMillis());
            secondLevelBean.setIsLike(0);
            secondLevelBean.setUserName(userName);
            secondLevelBean.setId("");
            secondLevelBean.setPosition(positionCount);

            datas.get(pos).getSecondLevelBeans().add(secondLevelBean);
            dataSort(0);
            bottomSheetAdapter.notifyDataSetChanged();
            rv_dialog_lists.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((LinearLayoutManager) rv_dialog_lists.getLayoutManager())
                            .scrollToPositionWithOffset(positionCount >= data.size() - 1 ? data.size() - 1
                                    : positionCount, positionCount >= data.size() - 1 ? Integer.MIN_VALUE : rv_dialog_lists.getHeight());
                }
            }, 100);

        } else {
            //添加一级评论
            FirstLevelBean firstLevelBean = new FirstLevelBean();
            firstLevelBean.setUserName(userName);
            firstLevelBean.setId(bottomSheetAdapter.getItemCount() + 1 + "");
            firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            firstLevelBean.setCreateTime(System.currentTimeMillis());
            firstLevelBean.setContent(msg);
            firstLevelBean.setLikeCount(0);
            firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
            datas.add(0, firstLevelBean);
            dataSort(0);
            bottomSheetAdapter.notifyDataSetChanged();
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

    @Override
    public void onLoadMoreRequested() {
        if (datas.size() >= totalCount) {
            bottomSheetAdapter.loadMoreEnd(false);
            return;
        }
        FirstLevelBean firstLevelBean = new FirstLevelBean();
        firstLevelBean.setUserName("hui");
        firstLevelBean.setId((datas.size() + 1) + "");
        firstLevelBean.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
        firstLevelBean.setCreateTime(System.currentTimeMillis());
        firstLevelBean.setContent("add loadmore comment");
        firstLevelBean.setLikeCount(0);
        firstLevelBean.setSecondLevelBeans(new ArrayList<SecondLevelBean>());
        datas.add(firstLevelBean);
        dataSort(datas.size() - 1);
        bottomSheetAdapter.notifyDataSetChanged();
        bottomSheetAdapter.loadMoreComplete();

    }

    // item滑动
    public void scrollLocation(int offsetY) {
        try {
            rv_dialog_lists.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭默认局部刷新动画
     */
    public void closeDefaultAnimator(RecyclerView mRvCustomer) {
        if (null == mRvCustomer) return;
        mRvCustomer.getItemAnimator().setAddDuration(0);
        mRvCustomer.getItemAnimator().setChangeDuration(0);
        mRvCustomer.getItemAnimator().setMoveDuration(0);
        mRvCustomer.getItemAnimator().setRemoveDuration(0);
        ((SimpleItemAnimator) mRvCustomer.getItemAnimator()).setSupportsChangeAnimations(false);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStart(PlayerStartEvent playerStartEvent) {
        if (mPlayerInfo != null) {
            if (position == 0 && MyApplication.isAtPlayerOne) {
                mPlayerInfo.vodPlayer.start();
            } else if (position == 1 && MyApplication.isAtPlayerTwo) {
                mPlayerInfo.vodPlayer.start();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStop(PlayerStopEvent playerStopEvent) {
        if (mPlayerInfo != null) {
            mPlayerInfo.vodPlayer.stop();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void playerStopChildSwitch(PlayerChildSwitchStopEvent playerChildSwitchStopEvent) {
        if (mPlayerInfo != null) {
            if (position == 0 && !MyApplication.isAtPlayerOne) {
                mPlayerInfo.vodPlayer.stop();
            } else if (position == 1 && !MyApplication.isAtPlayerTwo) {
                mPlayerInfo.vodPlayer.stop();
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        listener();
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

        mPagerAdapter = new VodPagerAdapter(this, mActivity, mLiveUrlList, position, giftOnClickListener, followOnClickListener, hashOnClickListener,
                likeOnClickListener, unLikeOnClickListener, emojiOnClickListener, forwardOnClickListener, commentsOnClickListener);
        mViewPager.setOffscreenPageLimit(bufferPageCount);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        setData();
        initGiftPop();
        getGiftData();

        initEmojisPop();
        getEmojisData();
    }

    void setData() {
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/7eSdPRKt_1818589543_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/1BrIAtvV_1818587477_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/FmdVOTqd_1818586962_shd.mp4");
        mLiveUrlList.add("http://vodhj5bqn44.vod.126.net/vodhj5bqn44/wq1e35cQ_1818588221_shd.mp4");
        mPagerAdapter.notifyDataSetChanged();
    }

    void initGiftPop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        giftPopWindow = new GiftPopWindow(mainActivity.getMainRl(), mActivity);
    }

    void initEmojisPop() {
        MainActivity mainActivity = (MainActivity) getActivity();
        emojisPopWindow = new EmojisPopWindow(mainActivity.getMainRl(), mActivity);
    }

    void listener() {
        giftOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                if (giftPopWindow.getAdapter().getData().size() > 0) {
                    giftPopWindow.show();
                } else {
                    ToastUtils.showToast("暂未获取到礼物数据,请稍后再试");
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
            }
        };
        commentsOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                mPlayerInfo.vodPlayer.stop();
                bottomSheetAdapter.notifyDataSetChanged();
                slideOffset1 = 0;
                bottomSheetDialog.show();
            }
        };
    }

    @Override
    protected void addListener() {
    }


    @Override
    protected void onLazyLoad() {

    }

}
