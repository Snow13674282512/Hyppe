package com.quyum.hyppe.ui.home.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class SelectLocationBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public String area;
        public String street;
        public String distance;
        public int type;
    }
}
