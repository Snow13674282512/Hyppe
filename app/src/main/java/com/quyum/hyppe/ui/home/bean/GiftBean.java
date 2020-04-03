package com.quyum.hyppe.ui.home.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class GiftBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public Integer drawable;
        public String title;
        public Boolean isSelect = false;
    }
}
