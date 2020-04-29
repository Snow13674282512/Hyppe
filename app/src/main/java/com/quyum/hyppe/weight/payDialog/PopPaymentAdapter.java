package com.quyum.hyppe.weight.payDialog;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.setting.bean.PaymentBean;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class PopPaymentAdapter extends BaseQuickAdapter<PaymentBean.DataBean, BaseViewHolder> {

    public PopPaymentAdapter() {
        super(R.layout.item_payment);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PaymentBean.DataBean item) {
        if (TextUtils.isEmpty(item.cardNumber)) {
            helper.setVisible(R.id.bank_ll, false);
            helper.setVisible(R.id.fixed_ll, true);
            helper.setText(R.id.pay_way_tv, item.title);
            helper.setImageResource(R.id.img_iv, item.drawable);
        } else {
            helper.setVisible(R.id.bank_ll, true);
            helper.setVisible(R.id.fixed_ll, false);
            helper.setText(R.id.card_number, item.cardNumber);
        }
    }
}
