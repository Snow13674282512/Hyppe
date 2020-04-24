package com.quyum.hyppe.ui.home.bean;

import com.quyum.hyppe.base.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class ChannelsHashTagBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public List<TiktokBean> list = new ArrayList<>();
        public String title;
    }
}
