package com.quyum.hyppe.ui.wallet.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class WalletBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public String title;
        public String time;
    }
}
