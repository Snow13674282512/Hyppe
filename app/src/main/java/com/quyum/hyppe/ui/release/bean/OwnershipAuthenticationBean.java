package com.quyum.hyppe.ui.release.bean;

import java.io.Serializable;

public class OwnershipAuthenticationBean implements Serializable {
    public String title;
    public String name;
    public String location;
    public String time;
    public String device;
    public String deviceModel;
    public String screenwriter;
    public String cast;

    public OwnershipAuthenticationBean(String title, String name, String location, String time, String device, String deviceModel, String screenwriter, String cast) {
        this.title = title;
        this.name = name;
        this.location = location;
        this.time = time;
        this.device = device;
        this.deviceModel = deviceModel;
        this.screenwriter = screenwriter;
        this.cast = cast;
    }
}
