package com.zry.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by Hasee on 2017/12/12.
 */

public class DialogViewHelper {

    private SparseArray<WeakReference<View>> mViewArray;
    private View mContentView;

    public DialogViewHelper(Context context, int viewLayoutResId) {
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId,null);
        mViewArray = new SparseArray<>();
    }

    public DialogViewHelper(View view) {
        mContentView = view;
        mViewArray = new SparseArray<>();
    }

    public void setText(int viewId, String text) {
        TextView tv = getView(viewId);
        if (tv != null){
            tv.setText(text);
        }
    }


    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null){
            view.setOnClickListener(listener);
        }

    }

    public View getContentView() {
        return mContentView;
    }

    public <T extends View>T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViewArray.get(viewId);
        View view = null;
        if (viewWeakReference != null){
            view = viewWeakReference.get();
        }
        if (view == null){
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViewArray.append(viewId, new WeakReference<View>(view));
            }
        }
        return (T) view;
    }
}
