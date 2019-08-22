package com.ruitu.arad;

import com.bumptech.glide.request.RequestOptions;
import com.github.jdsjlzx.interfaces.IRefreshHeader;

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

    public static RequestOptions opts;// Glide的通用配置选项
    public static RequestOptions opts_circle;// Glide的圆形通用配置选项

    public static IRefreshHeader refreshHeader;// 下拉刷新的头部
}
