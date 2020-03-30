package com.quyum.hyppe.base;


import com.quyum.hyppe.net.IModel;

import java.io.Serializable;

/**
 * Created by wanglei on 2016/12/11.
 */

public class BaseModel implements IModel, Serializable {
    public String msg;
    public String code;
    protected boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return !code.equals("1");
    }

    @Override
    public boolean isBizError() {
        return error;
    }

    @Override
    public boolean isRepeatLogin() {
        return code != null && code.equals("-2");
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
