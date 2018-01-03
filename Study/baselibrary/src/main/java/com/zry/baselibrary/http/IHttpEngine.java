package com.zry.baselibrary.http;

import java.util.Map;

/**
 * 网络引擎的规范
 */

public interface IHttpEngine {

    //get请求
    void get(String url, Map<String,Object> params,HttpCallBack callBack);


    //post请求
    void post(String url, Map<String,Object> params,HttpCallBack callBack);

    //上传


    //下载


    //https


}
