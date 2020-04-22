package com.quyum.hyppe.ui.home.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.quyum.hyppe.R;
import com.quyum.hyppe.api.APPApi;
import com.quyum.hyppe.base.BaseActivity;
import com.quyum.hyppe.base.MyApplication;
import com.quyum.hyppe.base.UserBean;
import com.quyum.hyppe.config.SystemParams;
import com.quyum.hyppe.net.ApiSubscriber;
import com.quyum.hyppe.net.NetError;
import com.quyum.hyppe.net.XApi;
import com.quyum.hyppe.net.XLog;
import com.quyum.hyppe.ui.login.activity.LoginAndRegisterActivity;
import com.quyum.hyppe.weight.TitleBar;

/**
 * <pre>
 *     author : Xue
 *     time   : 2019/01/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected boolean buildTitle(TitleBar bar) {
        return false;
    }

    @Override
    protected int getActivityBg() {
        return 0;
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        XLog.e("WelcomeActivity", "-------------发送一个延迟消息：" + System.currentTimeMillis());
        login();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void login() {
        //从缓存中读取为空的话
        if (!isEmptys(SystemParams.getInstance().getMobile()) && !isEmptys(SystemParams.getInstance().getPassword())) {
            //有手机号和密码登录过--调登录接口登录
            loginHttp();
        } else {
            LoginAndRegisterActivity.start(mContext);
            //跳转到首页
//            MainActivity.start(mContext);
            finish();
        }
    }

    //从网络获取当前登录信息
    void loginHttp() {
        APPApi.getHttpService().login(SystemParams.getInstance().getMobile(), SystemParams.getInstance().getPassword(), null)
                .compose(XApi.getApiTransformer())
                .compose(XApi.getScheduler())
                .subscribe(new ApiSubscriber<UserBean>() {
                    @Override
                    protected void onFail(NetError error) {
                        SystemParams.getInstance().setToken("");
                        SystemParams.getInstance().setMobile("");
                        MyApplication.CurrentUser = null;
                        //登录成功-跳转首页
//                        MainActivity.start(mContext);
                        LoginAndRegisterActivity.start(mContext);
                        finish();
                    }

                    @Override
                    public void onNext(UserBean data) {
                        SystemParams.getInstance().setToken(data.data.ui_token);
                        SystemParams.getInstance().setMobile(data.data.ui_phone);
                        MyApplication.CurrentUser = data.data;
                        MyApplication.save();//保存一下
                        //登录成功-跳转首页
                        MainActivity.start(mContext);
                        finish();
                    }
                });
    }

    boolean isEmptys(String s) {
        return "".equals(s) || TextUtils.isEmpty(s);
    }

}
