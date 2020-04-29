package com.quyum.hyppe.ui.release.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.List;

public class SelectDeviceBean extends BaseModel {
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
