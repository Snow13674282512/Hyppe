package com.quyum.hyppe.ui.home.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class FansBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public String name;
        public String price;
        public Boolean isFollow = false;
    }
}
