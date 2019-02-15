package com.ruitu.arad;

import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

/**
 * Arad-Android Rapid Application Development
 *
 * @author Beanu
 */
public class Arad {
    public static AradApplication app;
    public static Preferences preferences;
    public static EventBus bus;

    public static RequestOptions opts;//Glide的通用配置选项
    public static RequestOptions opts_shop;//Glide店铺头像错误
    public static RequestOptions opts_circle;//Glide的圆形通用配置选项
    public static RequestOptions opts_circle_fillet;//Glide的圆角通用配置选项
//    public static RequestOptions opts_company_defult_logo;//Glide默认的企业logo


}
