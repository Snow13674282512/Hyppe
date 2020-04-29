package com.quyum.hyppe.ui.advertising.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class AdvertisingConditionsBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public Boolean isSelect = false;
        public String name;
    }
}
