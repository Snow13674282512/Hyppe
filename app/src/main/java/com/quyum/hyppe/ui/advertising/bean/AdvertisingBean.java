package com.quyum.hyppe.ui.advertising.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class AdvertisingBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public String distance;
        public Boolean isSelect = false;
    }
}
