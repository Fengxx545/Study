package com.zry.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by Hasee on 2017/12/6.
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;


    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }


    public View findViewById(int viewId){
        return mActivity == null ? mView.findViewById(viewId) : mActivity.findViewById(viewId);
    }

}
