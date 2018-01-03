package com.zry.baselibrary.http;

/**
 * Created by Hasee on 2017/12/8.
 */

public interface HttpCallBack {


    public void onError(Exception e);


    public void onSuccess(String result);

    /**
     * 默认空的回调
     */
    public final HttpCallBack DEFAULT_EMPTY_CALLBACK = new HttpCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };


}
