package com.zry.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Hasee on 2017/12/12.
 */
class BaseDialogController {

    private Context mContext;
    private BaseDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mDialogViewHelper;

    public BaseDialogController(Context context, BaseDialog baseDialog, Window window) {
        this.mContext = context;
        this.mDialog = baseDialog;
        this.mWindow = window;
    }

    public void setDialogViewHelper(DialogViewHelper dialogViewHelper) {
        mDialogViewHelper = dialogViewHelper;
    }

    public void setText(int viewId, String text) {
        mDialogViewHelper.setText(viewId,text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mDialogViewHelper.setOnClickListener(viewId,listener);
    }

    public <T extends View> T getView(int viewId) {
        return mDialogViewHelper.getView(viewId);
    }


    public static class AlertParams {

        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;

        public SparseArray<String> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mGravity = Gravity.CENTER;
        public int mAnimation = 0;


        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        public void apply(BaseDialogController controller) {

            DialogViewHelper helper = null;
            if (mView != null){
                helper = new DialogViewHelper(mView);

            }

            if (mViewLayoutResId != 0){
                helper = new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if (helper == null){
                throw new IllegalArgumentException("must use Builder setView() first");
            }

            //把dialog 设置view
            controller.mDialog.setContentView(helper.getContentView());
            controller.setDialogViewHelper(helper);

            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++){
                helper.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }

            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++){
                helper.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            controller.mDialog.setCancelable(mCancelable);

            if (mOnCancelListener != null){
                controller.mDialog.setOnCancelListener(mOnCancelListener);
            }

            if (mOnDismissListener != null){
                controller.mDialog.setOnDismissListener(mOnDismissListener);
            }
            if (mOnKeyListener != null){
                controller.mDialog.setOnKeyListener(mOnKeyListener);
            }

            Window window = controller.mWindow;

            window.setGravity(mGravity);

            if (mAnimation != 0){
                window.setWindowAnimations(mAnimation);
            }


            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = mHeight;
            window.setAttributes(attributes);


        }
    }
}
