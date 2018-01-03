package com.zry.baselibrary.http;


import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hy on 2016/10/18.
 */

public class Retrofit2Util {
    public static Retrofit getRetrofit(String cachePath, String url) {
        //缓存容量
        long SIZE_OF_CACHE = 15 * 1024 * 1024; // 10 MiB
        //缓存路径
        String cacheFile = cachePath+"/http";

        Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);
        //利用okhttp实现缓存 okio
        OkHttpClient client = new OkHttpClient.Builder()
                //有网络时的拦截器
                .addNetworkInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                //没网络时的拦截器
                .addInterceptor(CachingControlInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                .cache(cache)
                .build();
        //返回retrofit对象
        return   new Retrofit.Builder()
                .baseUrl(url)//根链接
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


}
