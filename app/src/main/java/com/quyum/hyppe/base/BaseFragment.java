package com.quyum.hyppe.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.mingle.widget.LoadingView;
import com.quyum.hyppe.R;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.FinishEvent;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.utils.StreamUtil;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.quyum.hyppe.base.MyApplication.getCacheFile;

public abstract class BaseFragment extends RxFragment {
    protected ViewGroup mViewGroup;
    protected LinearLayout mContentRootView;
    protected TitleBar mTitleBar;
    protected RelativeLayout rootLayout;

    protected Activity mActivity;

    protected View mRootView;

    protected boolean mIsVisible;

    protected boolean mIsPrepare;

    protected LoadingView loadingView;

    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.base_fragment, container, false);
        rootLayout = mRootView.findViewById(R.id.rootView_baseFragment);
        loadingView = mRootView.findViewById(R.id.loadView);
        basicInitialize();

        //点击空白区域系统软键盘消失
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getActivity().onTouchEvent(motionEvent);
                return false;
            }
        });


        mUnBinder = ButterKnife.bind(this, mRootView);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            int statusBarHeight1 = -1;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
            }
            ViewGroup.LayoutParams params = mTitleBar.getTopView().getLayoutParams();
            params.height = statusBarHeight1;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            mTitleBar.getTopView().setLayoutParams(params);
        }
        //加这个是防止空问题
        if (null == MyApplication.CurrentUser) {
            MyApplication.CurrentUser = (UserBean.DataBean) StreamUtil.restoreObject(getCacheFile() + UserBean.DataBean.TAG);
        }

        initData(getArguments());

        initView(savedInstanceState);

        mIsPrepare = true;

        addListener();

        return mRootView;
    }

    private void basicInitialize() {
        basicFindViews();
        basicSetting();
    }

    private void basicSetting() {
        buildTitle();
        addContent();
    }

    private void addContent() {
        final int layoutId = setLayoutResourceId();
        if (layoutId <= 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        inflater.inflate(layoutId, mContentRootView, true);
    }

    private void buildTitle() {
        final TitleBar bar = mTitleBar;
        if (bar == null)
            return;
        bar.setActivity(mActivity);
        if (!buildTitle(mTitleBar))
            mTitleBar.setVisibility(View.GONE);
    }

    protected abstract int setLayoutResourceId();

    protected abstract boolean buildTitle(TitleBar bar);

    private void basicFindViews() {
        mViewGroup = mRootView.findViewById(R.id.rootView_baseFragment);
        mContentRootView = mRootView.findViewById(R.id.content_fragment);
        mTitleBar = mRootView.findViewById(R.id.titleBar);
    }

    protected abstract void initData(Bundle arguments);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void addListener();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        this.mIsVisible = isVisibleToUser;

        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }

    protected void onVisibleToUser() {
        if (mIsPrepare && mIsVisible) {
            onLazyLoad();
        }
    }

    protected abstract void onLazyLoad();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    public void showDError(NetError error, TextView hintView) {
        if (error != null) {
            switch (error.getType()) {
                case NetError.ParseError:
                    if (hintView != null) hintView.setText("数据解析异常");
                    else ToastUtils.showToast("数据解析异常");
                    break;

                case NetError.AuthError:
                    if (hintView != null) hintView.setText("" + error.getMessage());
                    else ToastUtils.showToast("" + error.getMessage());
                    break;

                case NetError.BusinessError:
                    if (hintView != null) hintView.setText("业务异常");
                    else ToastUtils.showToast("业务异常");
                    break;

                case NetError.NoConnectError:
                    if (hintView != null) hintView.setText("网络无连接");
                    else ToastUtils.showToast("网络无连接");
                    break;

                case NetError.NoDataError:
                    if (hintView != null) hintView.setText("数据为空");
                    else ToastUtils.showToast("数据为空");
                    break;

                case NetError.OtherError:
                    if (hintView != null) hintView.setText("网络异常" + error.getMessage());
                    else ToastUtils.showToast("网络异常" + error.getMessage());
                    break;

                case NetError.RepeatLogin:
                    ToastUtils.showToast("您的账号已在其他设备登录,请重新登录");
                    SystemParams.getInstance().setToken(null);
                    SystemParams.getInstance().setMobile(null);
                    SystemParams.getInstance().setPassword(null);
                    MyApplication.CurrentUser = null;
                    MyApplication.save();
//                    LoginAccountActivity.start(mActivity);
                    EventBus.getDefault().post(new FinishEvent());
                    mActivity.finish();
                    break;
            }
        }
    }

}
