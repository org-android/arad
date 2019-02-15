package com.ruitu.router_module.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.Map;

//地图导航工具类
public class MapNavUtil {
    //1.百度地图包名
    public static final String BAIDUMAP_PACKAGENAME = "com.baidu.BaiduMap";
    //2.高德地图包名
    public static final String AUTONAVI_PACKAGENAME = "com.autonavi.minimap";
    //3.腾讯地图包名
    public static final String QQMAP_PACKAGENAME = "com.tencent.map";

    //参数的key
    //高德的坐标系 "gd_lng" (高德_经度)、"gd_lat"（纬度）、"destination"（目的地名称）
    public static final String GCJO2_LNG = "gd_lng";
    public static final String GCJO2_LAT = "gd_lat";

    public static final String BD_LNG = "bd_lng";
    public static final String BD_LAT = "bd_lat";
    public static final String DESTINATION = "destination";

    //调用百度地图
    public static void invokeBaiDuMap(Context context, Map arg) {
        try {
            Uri uri = Uri.parse("baidumap://map/geocoder?" +
                    "location=" + arg.get(BD_LAT) + "," + arg.get(BD_LNG) +
                    "&name=" + arg.get(DESTINATION) + //终点的显示名称
                    "&coord_type=bd09ll");//坐标 （百度同样支持他自己的db0911的坐标，但是高德和腾讯不支持）
            Intent intent = new Intent();
            intent.setPackage(BAIDUMAP_PACKAGENAME);
            intent.setData(uri);

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //调用高德地图
    public static void invokeAuToNaveMap(Context context, Map arg) {
        try {
            Uri uri = Uri.parse("androidamap://route?sourceApplication={你的应用名称}" +
                    "&dlat=" + arg.get(GCJO2_LAT)//终点的纬度
                    + "&dlon=" + arg.get(GCJO2_LNG)//终点的经度
                    + "&dname=" + arg.get(DESTINATION)////终点的显示名称
                    + "&dev=0&m=0&t=0");
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addCategory("android.intent.category.DEFAULT");

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //调用腾讯地图
    public static void invokeQQMap(Context context, Map arg) {
        try {
            Uri uri = Uri.parse("qqmap://map/routeplan?type=drive" +
                    "&to=" + arg.get(DESTINATION)//终点的显示名称 必要参数
                    + "&tocoord=" + arg.get(GCJO2_LAT) + "," + arg.get(GCJO2_LNG)//终点的经纬度
                    + "&referer={你的应用名称}");
            Intent intent = new Intent();
            intent.setData(uri);

            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
