package com.quyum.hyppe.api;


import com.quyum.hyppe.base.Constant;
import com.quyum.hyppe.net.XApi;

public class APPApi {
    private static APPService appService;

    public static APPService getHttpService() {
        if (appService == null) {
            synchronized (APPApi.class) {
                if (appService == null) {
                    appService = XApi.getInstance().getRetrofit(Constant.BASE_URL, true).create(APPService.class);
                }
            }
        }
        return appService;
    }
}
