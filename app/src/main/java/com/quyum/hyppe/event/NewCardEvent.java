package com.quyum.hyppe.event;

import com.quyum.hyppe.ui.home.bean.PaymentBean;

public class NewCardEvent {
    public PaymentBean.DataBean dataBean;

    public NewCardEvent(PaymentBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
