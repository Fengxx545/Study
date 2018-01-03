package com.zry.study.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Hasee on 2017/12/11.
 */

public class SlideBarView extends View {

    public static String[] b = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };
    private Paint mPaint = new Paint();
    private int choose = -1;
    private OnTouchingBarChangedListener mOnTouchingBarChangedListener;
    private View mCenterView;

    public SlideBarView(Context context) {
        super(context);
    }

    public SlideBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / b.length;// 获取每一个字母的高度

        for (int i = 0; i < b.length; i++) {
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30);
            if (i == choose) {// 选中的状态
                mPaint.setColor(Color.parseColor("#3399ff"));
                mPaint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半
            float xPos = width / 2 - mPaint.measureText(b[i]) / 2;
            float yPos = singleHeight * (i + 1);
            canvas.drawText(b[i], xPos, yPos, mPaint);
            mPaint.reset();

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingBarChangedListener listener = mOnTouchingBarChangedListener;
        final int c = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackground(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mCenterView != null) {
                    mCenterView.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                if (oldChoose != c) {
                    if (c >= 0 && c < b.length) {
                        if (listener != null) {
                            listener.onTouchingBarChanged(b[c]);
                        }
                        if (mCenterView != null) {
                            ((TextView)mCenterView).setText(b[c]);
                            mCenterView.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }

                break;
        }
        return true;
    }


    public void setCenterView(View centerView) {
        mCenterView = centerView;
    }

    public void setOnTouchingBarChangedListener(OnTouchingBarChangedListener onTouchingBarChangedListener) {
        mOnTouchingBarChangedListener = onTouchingBarChangedListener;
    }

    public interface OnTouchingBarChangedListener {
        void onTouchingBarChanged(String s);
    }
}
