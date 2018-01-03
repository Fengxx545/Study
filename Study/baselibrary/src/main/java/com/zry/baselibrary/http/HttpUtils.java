package com.zry.baselibrary.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hasee on 2017/12/8.
 */

public class HttpUtils{
    private static IHttpEngine mEngine= new RetrofitEngine();

    private String mUrl;
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x110;
    private static final int GET_TYPE = 0x111;
    private Context mContext;
    private Map<String,Object> mParams;

    private HttpUtils(Context context){
        mContext = context;
        mParams = new HashMap<>();
    }

    //初始化默认引擎
    public static void init(IHttpEngine engine){
        mEngine = engine;
    }

    public static HttpUtils with(Context context){
        return new HttpUtils(context);
    }

    public HttpUtils url(String url){
        mUrl = url;
        return this;
    }

    public HttpUtils post(){
        mType = POST_TYPE;
        return this;
    }
    public HttpUtils get(){
        mType = GET_TYPE;
        return this;
    }

    public HttpUtils addParam(String key, Object value){
        mParams.put(key,value);
        return this;
    }

    public HttpUtils addParam(Map<String,Object> params){
        mParams.putAll(params);
        return this;
    }

    public void execute(HttpCallBack callBack){
        if (callBack == null){
            callBack = HttpCallBack.DEFAULT_EMPTY_CALLBACK;
        }

        if (mType == POST_TYPE){
            post(mUrl,mParams,callBack);
        }

        if (mType == GET_TYPE){
            get(mUrl,mParams,callBack);
        }


    }

    public void execute(){
        execute(null);
    }

    //切换引擎
    public HttpUtils exchangeEngine(IHttpEngine engine){
        mEngine = engine;
        return this;
    }


    private void get(String url, Map<String, Object> params, HttpCallBack callBack) {
        mEngine.get(url,params,callBack);
    }

    private void post(String url, Map<String, Object> params, HttpCallBack callBack) {
        mEngine.post(url,params,callBack);
    }
}
