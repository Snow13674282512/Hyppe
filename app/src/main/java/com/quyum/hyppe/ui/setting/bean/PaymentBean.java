package com.quyum.hyppe.ui.setting.bean;

import com.quyum.hyppe.base.BaseModel;

import java.io.Serializable;
import java.util.List;

public class PaymentBean extends BaseModel {
    public List<DataBean> data;

    @Override
    public boolean isNull() {
        return data == null && data.isEmpty();
    }

    public static class DataBean {

        public Integer drawable;
        public String title;
        public String cardNumber;
    }
}
