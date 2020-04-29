package com.quyum.hyppe.ui.daily.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class SignInAddFriendBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public String price;
    }
}
