package com.zry.extendlibrary;

import android.content.Context;
import android.view.ViewGroup;

import com.zry.baselibrary.title.BaseTitleBar;

/**
 * Created by Hasee on 2017/12/19.
 */

public class DefaultTitleBar extends BaseTitleBar<DefaultTitleBar.Builder.DefaultParam> {
    public DefaultTitleBar(DefaultTitleBar.Builder.DefaultParam param) {
        super(param);
    }

    /**
     * 绑定布局
     * @return
     */
    @Override
    public int bindLayout() {
        return R.layout.default_title_view;
    }

    /**
     * 设置view
     */
    @Override
    public void setView() {
        setText(R.id.title, getParam().mTitle);
        setImage(R.id.left_image, getParam().mImageRes);
    }


    public static class Builder extends BaseTitleBar.Builder {
        private DefaultParam P;

        //在构造方法中会初始化DefaultParam
        public Builder(Context context, ViewGroup viewGroup) {
            P = new DefaultParam(context, viewGroup);
        }

        /**
         * 返回想要的TitleBar
         * @return
         */
        @Override
        public DefaultTitleBar builder() {

            DefaultTitleBar titleBar = new DefaultTitleBar(P);
            return titleBar;
        }

        /**
         * 设置title
         * @param title
         * @return
         */
        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        /**
         * 设置左边View的图片
         * @param imageRes
         * @return
         */
        public Builder setLeftIcon(int imageRes) {
            P.mImageRes = imageRes;
            return this;
        }


        public static class DefaultParam extends BaseParam {

            public String mTitle;
            public int mImageRes = -1;

            public DefaultParam(Context context, ViewGroup viewGroup) {
                super(context, viewGroup);
            }
        }

    }


}
