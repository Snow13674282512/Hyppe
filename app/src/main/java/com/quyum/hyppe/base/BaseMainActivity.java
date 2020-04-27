package com.quyum.hyppe.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.quyum.hyppe.R;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.event.FinishEvent;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.XDroidConf;
import com.quyum.hyppe.ui.login.activity.LoginAndRegisterActivity;
import com.quyum.hyppe.utils.StatusBar;
import com.quyum.hyppe.utils.StatusNavUtils;
import com.quyum.hyppe.utils.StreamUtil;
import com.quyum.hyppe.utils.ToastUtils;
import com.quyum.hyppe.weight.TitleBar;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import static com.quyum.hyppe.base.MyApplication.getCacheFile;


public abstract class BaseMainActivity extends RxAppCompatActivity {
    public CompositeDisposable compositeDisposable;
    protected ViewGroup mRootView;
    protected LinearLayout mContentRootView;
    protected TitleBar mTitleBar;
    protected BaseMainActivity mContext;
    private RxPermissions rxPermissions;
    private Unbinder mUnBinder;
//    private MyApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            try {
//                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
//                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
//                field.setAccessible(true);
//                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
//            } catch (Exception e) {
//                String s = e.toString();
//            }
//        }
        setContentView(R.layout.base_activity);
        StatusNavUtils.setStatusBarColor(this, 0x00000000);
        mContext = this;
        compositeDisposable = new CompositeDisposable();
//        StatusBar.transportStatus(this);
        basicInitialize();
        mUnBinder = ButterKnife.bind(this);

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

        initData(savedInstanceState);
        initView();
        addListener();
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
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
        final int layoutId = getLayoutId();
        if (layoutId <= 0) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        inflater.inflate(layoutId, mContentRootView, true);
    }

    private void buildTitle() {
        final TitleBar bar = mTitleBar;
        if (bar == null)
            return;
        bar.setActivity(this);
        if (!buildTitle(mTitleBar))
            mTitleBar.setVisibility(View.GONE);
        bar.enableLeftImageBackOnClick();
    }

    protected abstract int getLayoutId();

    protected abstract boolean buildTitle(TitleBar bar);

    protected abstract int getActivityBg();

    private void basicFindViews() {
        mRootView = findViewById(R.id.rootView_baseActivity);
        mContentRootView = findViewById(R.id.content);
        mTitleBar = findViewById(R.id.titleBar);
        mRootView.setBackgroundResource(getActivityBg());
        StatusBar.StatusBarDarkMode(mContext);
    }

    protected abstract void addListener();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        compositeDisposable.dispose();

        //释放资源
        mRootView = null;
        mContentRootView = null;
        mTitleBar = null;
        mContext = null;
        rxPermissions = null;

        super.onDestroy();
    }

    public RxPermissions getRxPermissions() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(XDroidConf.DEV);
        return rxPermissions;
    }

    /*
     * 重写onTouchEvent方法监听
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

//    public boolean onTouchEvent(MotionEvent event) {
//        if (null != this.getCurrentFocus()) {
//            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
//        }
//        return super.onTouchEvent(event);
//    }

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
                    MyApplication.CurrentUser = null;
                    MyApplication.save();
                    SystemParams.getInstance().setMobile(null);
                    SystemParams.getInstance().setPassword(null);
                    LoginAndRegisterActivity.start(mContext);
                    EventBus.getDefault().post(new FinishEvent());
                    finish();
                    break;
            }
        }
    }
}
