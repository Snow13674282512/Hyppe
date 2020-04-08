package com.quyum.hyppe.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.quyum.hyppe.R;
import com.quyum.hyppe.base.BaseFragment;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.event.PlayerChildSwitchStopEvent;
import com.quyum.hyppe.event.PlayerStartEvent;
import com.quyum.hyppe.event.PlayerStopEvent;
import com.quyum.hyppe.ui.home.activity.HashActivity;
import com.quyum.hyppe.ui.home.activity.MainActivity;
import com.quyum.hyppe.ui.home.adapter.PopCommentsAdapter;
import com.quyum.hyppe.ui.home.adapter.VodPagerAdapter;
import com.quyum.hyppe.ui.home.bean.EmojisBean;
import com.quyum.hyppe.ui.home.bean.GiftBean;
import com.quyum.hyppe.ui.home.bean.PlayerInfo;
import com.quyum.hyppe.utils.SlideHideRelativeLayout;
import com.quyum.hyppe.utils.SoftKeyBoardListener;
import com.quyum.hyppe.utils.SoftKeyHideShow;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.EmojisPopWindow;
import com.quyum.hyppe.weight.GiftPopWindow;
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
    @BindView(R.id.commit)
    SlideHideRelativeLayout commit;
    @BindView(R.id.tv_shape)
    TextView tv_shape;
    @BindView(R.id.ll_cancel)
    LinearLayout ll_cancel;
    @BindView(R.id.tv_context)
    TextView tv_context;
    @BindView(R.id.tv_shape2)
    TextView tv_shape2;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.recyclerViewCommit)
    RecyclerView recyclerViewCommit;
    @BindView(R.id.et_context)
    EditText et_context;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;

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

    private Animation showAction, ouAction;
    private PopCommentsAdapter commentsAdapter;
    private SoftKeyBoardListener softKeyBoardListener;//软键盘监听


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
        setSoftKeyBoardListener();
    }

    /**
     * 软键盘监听
     */
    private void setSoftKeyBoardListener() {
        softKeyBoardListener = new SoftKeyBoardListener(mActivity);
        //软键盘状态监听
        softKeyBoardListener.setListener(new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                //动态设置控件宽高
                ViewGroup.LayoutParams params = rl_bottom.getLayoutParams();
                rl_bottom.setPadding(0, 0, 0, height);
                rl_bottom.setLayoutParams(params);
                //当软键盘显示的时候
                rl_bottom.setVisibility(View.VISIBLE);
                tv_shape2.setVisibility(View.VISIBLE);

                et_context.setFocusable(true);
                et_context.setFocusableInTouchMode(true);
                et_context.setCursorVisible(true);
                et_context.requestFocus();
            }

            @Override
            public void keyBoardHide(int height) {
                //当软键盘隐藏的时候
                rl_bottom.setVisibility(View.GONE);
                tv_shape2.setVisibility(View.GONE);
                et_context.setFocusable(false);
                et_context.setFocusableInTouchMode(false);
                et_context.setCursorVisible(false);
            }
        });
        //设置点击事件,显示软键盘
        et_context.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View view) {
                InputMethodManager manager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
        //防止EditText点击两次才获取到焦点
        et_context.setOnTouchListener(new View.OnTouchListener() {
            //按住和松开的标识
            int flag = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                flag++;
                if (flag == 2) {
                    flag = 0;//不要忘记这句话
                    //处理逻辑
                    et_context.setFocusable(true);
                    et_context.setFocusableInTouchMode(true);
                    et_context.setCursorVisible(true);
                }
                return false;
            }
        });
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
                showCommitDialog();
            }
        };
    }

    @Override
    protected void addListener() {
    }

    /**
     * 评论布局
     */
    public void showCommitDialog() {
        if (commentsAdapter == null) {
            commentsAdapter = new PopCommentsAdapter();
            recyclerViewCommit.setLayoutManager(new LinearLayoutManager(mActivity));
            recyclerViewCommit.setAdapter(commentsAdapter);
        } else {
            commentsAdapter.notifyDataSetChanged();
        }

        //为布局设置显示的动画
        showAction = AnimationUtils.loadAnimation(mActivity, R.anim.actionsheet_dialog_in);
        commit.startAnimation(showAction);

        commit.setCloseViewListener(new SlideHideRelativeLayout.CloseView() {
            @Override
            public void colseCallBack() {
                ouAction = AnimationUtils.loadAnimation(mActivity, R.anim.actionsheet_dialog_out);
                commit.startAnimation(ouAction);

                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
            }
        });


        //显示布局和阴影
        commit.setVisibility(View.VISIBLE);
        tv_shape.setVisibility(View.VISIBLE);

        //关闭评论
        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
            }
        });
        //阴影点击,隐藏评论列表和阴影
        tv_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit.setVisibility(View.GONE);
                tv_shape.setVisibility(View.GONE);
            }
        });
        //输入评论点击
        tv_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
        //第二层阴影点击，隐藏输入评论框和软键盘
        tv_shape2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
        //发送评论
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "评论成功", Toast.LENGTH_SHORT).show();
                SoftKeyHideShow.HideShowSoftKey(mActivity);//隐藏软键盘
            }
        });
    }

    @Override
    protected void onLazyLoad() {

    }

}
