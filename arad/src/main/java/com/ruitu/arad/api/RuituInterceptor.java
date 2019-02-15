package com.ruitu.arad.api;

import com.ruitu.arad.util.MD5Util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/18.
 */
public class RuituInterceptor implements Interceptor {
    private static String TOKEN_PARAM = "token";
    private static String TIME_PARAM = "timestamp";

    public static String getTimeStamp() {
        return new Date().getTime() + "";
    }

    public static String getToken() {
        return MD5Util.md5String(new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "app").toLowerCase();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request orgRequest = chain.request();
        RequestBody body = orgRequest.body();
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();

        if (body != null) {//说明有请求实体
            RequestBody newBody = null;
            if (body instanceof FormBody) {
                newBody = addParamsToFormBody((FormBody) body, paramsBuilder);
            } else if (body instanceof MultipartBody) {
                newBody = addParamsToMultipartBody((MultipartBody) body, paramsBuilder);
            }

            if (null != newBody) {
//                Logg.i(paramsBuilder.toString());//打印参数
                Request newRequest = orgRequest.newBuilder()
                        .url(orgRequest.url())
                        .method(orgRequest.method(), newBody)
                        .build();

                return chain.proceed(newRequest);
            }
        } else {//说明没有请求实体
            HttpUrl newUrl = orgRequest.url().newBuilder()
                    .addQueryParameter(TOKEN_PARAM, getToken())
                    .addQueryParameter(TIME_PARAM, getTimeStamp())
                    .build();

            Request getReq = orgRequest.newBuilder()
                    .method(orgRequest.method(), orgRequest.body())
                    .url(newUrl)
                    .build();

            return chain.proceed(getReq);
        }
        return chain.proceed(orgRequest);
    }

    /**
     * 为MultipartBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private MultipartBody addParamsToMultipartBody(MultipartBody body, StringBuilder paramsBuilder) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        //添加token
        builder.addFormDataPart(TOKEN_PARAM, getToken());
        paramsBuilder.append("token=" + getToken());

        paramsBuilder.append("&");

        //添加时间戳
        builder.addFormDataPart(TIME_PARAM, getTimeStamp());
        paramsBuilder.append("timestamp=" + getTimeStamp());
        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addPart(body.part(i));
        }

        return builder.build();
    }

    /**
     * 为FormBody类型请求体添加参数
     *
     * @param body
     * @param paramsBuilder
     * @return
     */
    private FormBody addParamsToFormBody(FormBody body, StringBuilder paramsBuilder) {
        FormBody.Builder builder = new FormBody.Builder();

        //添加token
        builder.add(TOKEN_PARAM, getToken());
        paramsBuilder.append("token=" + getToken());

        paramsBuilder.append("&");

        //添加时间戳
        builder.add(TIME_PARAM, getTimeStamp());
        paramsBuilder.append("timestamp=" + getTimeStamp());

        //添加原请求体
        for (int i = 0; i < body.size(); i++) {
            builder.addEncoded(body.encodedName(i), body.encodedValue(i));
            paramsBuilder.append("&");
            paramsBuilder.append(body.name(i));
            paramsBuilder.append("=");
            paramsBuilder.append(body.value(i));
        }

        return builder.build();
    }
}
