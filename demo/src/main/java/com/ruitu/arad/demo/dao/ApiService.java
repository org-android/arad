package com.ruitu.arad.demo.dao;


import com.ruitu.arad.api.BaseResult;
import com.ruitu.router_module.bean.RongToken;
import com.ruitu.router_module.bean.Version;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    //融云获取token接口
    @FormUrlEncoded
    @POST("getToken.json")
    Observable<RongToken> reqRongToken(
            @Header("App-Key") String appKey,
            @Header("Nonce") String Nonce,
            @Header("Timestamp") String Timestamp,
            @Header("Signature") String Signature,
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("portraitUri") String portraitUri);

//    // index/appIndex app首页
//    @GET("index/appIndex")
//    Observable<BaseResult<HomeData>> reqAppHome(@Query("cityName") String cityName, @Query("accountId") String accountId);

    // cart/addCart 加入购物车
    @FormUrlEncoded
    @POST("cart/addCart")
    Observable<BaseResult> addToCart(@Field("goodsId") String goodsId, @Field("accountId")
            String accountId, @Field("num") int num, @Field("skuId") String skuId);

    // version/checkOne  检查新版本
    @GET("version/checkOne")
    Observable<BaseResult<Version>> reqNewVersion();
}