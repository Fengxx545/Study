package com.zry.baselibrary.title;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 定义一个TitleBar的模板
 * @param <P> 泛型,P必须继承BaseTitleBar.Builder.BaseParam
 */
public abstract class BaseTitleBar<P extends BaseTitleBar.Builder.BaseParam> implements TitleBarInterface {
    private P mParam;
    private View mTitleView;

    public BaseTitleBar(P param) {
        mParam = param;
        //创建并且绑定
        creatAndBindView();
    }


    /**
     * 返回一个param,自己在外面定义的类型的
     * @return
     */
    public P getParam() {
        return mParam;
    }


    protected void setText(int viewId, String text) {
        TextView view = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

    protected void setImage(int viewId, int imageRes) {
        ImageView view = findViewById(viewId);
        if (imageRes != -1) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(imageRes);
        }
    }

    /**
     * 自定义一个findViewById 避免强转
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int viewId) {
        return (T) mTitleView.findViewById(viewId);
    }


    protected void creatAndBindView() {
        if (mParam.mViewGroup == null){
            ViewGroup activityRoot = (ViewGroup) ((Activity)mParam.mContext).getWindow().getDecorView();
            mParam.mViewGroup = (ViewGroup) activityRoot.getChildAt(0);
        }

        if (mParam.mViewGroup == null)return;
        //第一步先获取view
        mTitleView = LayoutInflater.from(mParam.mContext)
                .inflate(bindLayout(), mParam.mViewGroup, false);
        //第二,把view添加到父布局中
        mParam.mViewGroup.addView(mTitleView, 0);
        //第三,设置view
        setView();

    }

    /**
     * Builder模式中Builder的父类
     */
    public abstract static class Builder {


        public abstract BaseTitleBar builder();

        /**
         * Param的父类
         */
        public static class BaseParam {
            public Context mContext;
            public ViewGroup mViewGroup;

            public BaseParam(Context context, ViewGroup viewGroup) {
                mContext = context;
                mViewGroup = viewGroup;
            }
        }

    }


}
