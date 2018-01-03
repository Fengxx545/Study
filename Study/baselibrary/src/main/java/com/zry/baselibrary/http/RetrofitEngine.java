package com.zry.baselibrary.http;

import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Hasee on 2017/12/23.
 */

public class RetrofitEngine<T> implements IHttpEngine {

    @Override
    public void get(String url, Map<String, Object> params, HttpCallBack callBack) {
        Retrofit2Util.getRetrofit("",url).create(Class<T> service)
    }

    @Override
    public void post(String url, Map<String, Object> params, HttpCallBack callBack) {
    }
}
