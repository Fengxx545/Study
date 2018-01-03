package com.zry.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;

import com.zry.baselibrary.R;

/**
 * Created by Hasee on 2017/12/12.
 */

public class BaseDialog extends Dialog {

    final BaseDialogController mAlert;

    /**
     * 不需要主题的构造
     * @param context
     */
    protected BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    /**
     * 自定义主题的构造
     * @param context
     * @param theme
     */
    protected BaseDialog(Context context, int theme) {
        super(context, theme);
        mAlert = new BaseDialogController(getContext(), this, getWindow());

    }


    public void setText(int viewId, String text) {
        mAlert.setText(viewId,text);
    }


    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId,listener);
    }


    public <T extends View>T getView(int viewId) {
        return mAlert.getView(viewId);
    }



    public static class Builder{
        private final BaseDialogController.AlertParams P;
        private final int mTheme;

        public Builder(@NonNull Context context) {
            this(context, R.style.dialog);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            P = new BaseDialogController.AlertParams(context, themeResId);
            mTheme = themeResId;
        }

        /**
         * builder必须先setview
         * 传layoutResId
         * @param layoutResId
         * @return
         */
        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        /**
         * 直接传view
         * @param view
         * @return
         */
        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置文本文字
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId,String text) {
            P.mTextArray.append(viewId,text);
            return this;
        }

        /**
         * 设置点击事件
         * @param viewId
         * @param listener
         * @return
         */
        public Builder setOnClickListener(int viewId,View.OnClickListener listener){
            P.mClickArray.append(viewId,listener);
            return this;
        }


        /**
         * 点击空白区域是否能取消
         * @param isCancelable
         * @return
         */
        public Builder isCancelable(boolean isCancelable) {
            P.mCancelable = isCancelable;
            return this;
        }

        /**
         * 设置dialog宽,默认ViewGroup.LayoutParams.WRAP_CONTENT
         * @param width
         * @return
         */
        public Builder setWidth(int width){
            P.mWidth = width;
            return this;
        }

        /**
         * 设置dialog高 默认ViewGroup.LayoutParams.WRAP_CONTENT
         * @param height
         * @return
         */
        public Builder setHeight(int height){
            P.mHeight = height;
            return this;
        }

        /**
         * 设置在底部
         * @return
         */
        public Builder setFromBottom(){
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置默认动画
         * @return
         */
        public Builder setDefaultAnimation(){
            P.mAnimation = R.style.DialogDefaultAnimation;
            return this;
        }

        /**
         * 自定义动画
         * @param animation
         * @return
         */
        public Builder setAnimation(@StyleRes int animation){
            P.mAnimation = animation;
            return this;
        }


        public Builder setCancelListener(DialogInterface.OnCancelListener cancelListener) {
            P.mOnCancelListener = cancelListener;
            return this;
        }

        public Builder setDismissListener(DialogInterface.OnDismissListener dismissListener) {
            P.mOnDismissListener = dismissListener;
            return this;
        }

        public Builder setKeyListener(DialogInterface.OnKeyListener keyListener) {
            P.mOnKeyListener = keyListener;
            return this;
        }

        /**
         * Creates an {@link BaseDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        public BaseDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final BaseDialog dialog = new BaseDialog(P.mContext, mTheme);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        /**
         * Creates an {@link BaseDialog} with the arguments supplied to this
         * builder and immediately displays the dialog.
         * <p>
         * Calling this method is functionally identical to:
         * <pre>
         *     AlertDialog dialog = builder.create();
         *     dialog.show();
         * </pre>
         */
        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }


    }


}
