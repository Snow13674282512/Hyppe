package com.quyum.hyppe.ui.release.adapter;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.quyum.hyppe.R;
import com.quyum.hyppe.ui.release.bean.SelectLocationBean;
import com.quyum.hyppe.utils.UiUtil;

/**
 * <pre>
 *     author : Xue
 *     time   : 2018/12/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SelectLocationAdapter extends BaseQuickAdapter<SelectLocationBean.DataBean, BaseViewHolder> {
    public String searchStr = "";

    public SelectLocationAdapter() {
        super(R.layout.item_select_location);
    }

    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final SelectLocationBean.DataBean item) {
        if (TextUtils.isEmpty(item.street)) {
            helper.setGone(R.id.street_tv, false);
        } else {
            helper.setGone(R.id.street_tv, true);
        }
        helper.setText(R.id.distance_tv, item.distance);
        switch (item.type) {
            case 2://姓名
                setColor(helper.getView(R.id.area_tv), item.area, 2);
                helper.setText(R.id.street_tv, item.street);
                break;
            case 3://手机
                helper.setText(R.id.area_tv, item.area);
                setColor(helper.getView(R.id.street_tv), item.street, 3);
                break;
            case 4://缩写
                setColor(helper.getView(R.id.street_tv), item.area, 4);
                helper.setText(R.id.street_tv, item.street);
                break;
            case 5://全拼
                setColor(helper.getView(R.id.area_tv), item.area, 5);
                helper.setText(R.id.street_tv, item.street);
                break;
            default:
                helper.setText(R.id.area_tv, item.area);
                helper.setText(R.id.street_tv, item.street);
                break;
        }
    }

    public void setColor(TextView view, String text, int type) {
        int index;
        ForegroundColorSpan span = new ForegroundColorSpan(UiUtil.getColor(mContext, R.color.yellow_color));//要显示的颜色
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        switch (type) {
            case 2://姓名
                index = text.toLowerCase().indexOf(searchStr.toLowerCase());
                if (index != -1) {
                    builder.setSpan(span, index, index + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    view.setText(builder);
                } else {
                    view.setText(builder);
                }
                break;
            case 3://电话
                index = text.indexOf(searchStr);
                if (index != -1) {
                    builder.setSpan(span, index, index + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    view.setText(builder);
                } else {
                    view.setText(builder);
                }
                break;
            case 4://缩写
                String lowerCase = searchStr.toLowerCase();//全部小写
                String firstSpell = UiUtil.getFirstSpell(text);
                index = firstSpell.indexOf(lowerCase);
                if (index != -1) {
                    builder.setSpan(span, index, index + searchStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    view.setText(builder);
                } else {
                    view.setText(text);
                }
                break;
            case 5://全拼
                String lowerCase1 = searchStr.toLowerCase();//全部小写
                int count = text.length();//文字个数
                int start = -1;//开始位置
                int end = -1;//结束位置
                for (int i = 1; i < count + 1; i++) {//每次截取的字符个数
                    for (int j = 0; j < count; j++) {//开始位置
                        String sub;
                        if (j + i <= count) {//当截取的个数小于字符串长度时,直接截取
                            sub = text.substring(j, j + i);
                        } else {//否则截取后面的全部内容
                            sub = text.substring(j);
                        }
                        String pinYin = UiUtil.getPingYin(sub);//获取截取的拼音
                        if (pinYin.contains(lowerCase1)) {//如果截取的字符串能够匹配到
                            start = j;//记录开始位置
                            end = j + i < count ? j + i : count;//结束位置
                            i = count + 1;//跳出外层循环
                            break;//跳出内层循环
                        }
                    }
                }
                //设置变色范围
                if (start != -1 && end != -1) {
                    builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    view.setText(builder);
                } else {
                    view.setText(text);
                }
                break;
        }
    }
}
