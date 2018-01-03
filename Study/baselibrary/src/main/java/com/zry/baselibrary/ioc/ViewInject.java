package com.zry.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Hasee on 2017/12/6.
 */

public class ViewInject {


    /**
     * activity注入
     * @param activity
     */
    public static void inject(Activity activity){
        inject(new ViewFinder(activity), activity);
    }

    // 兼容View
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    // 兼容Fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }



    //最终要走的方法
    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 方法注解
     * @param viewFinder
     * @param object activity or fragment or view 是反射的类
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //获取到类
        Class clazz = object.getClass();
        //获取到类中所有的方法(私有,共有,保护)
        Method[] declaredMethods = clazz.getDeclaredMethods();
        //遍历方法
        for (Method declaredMethod : declaredMethods) {
            //获取OnClick注解
            Onclick annotation = declaredMethod.getAnnotation(Onclick.class);
            if (annotation != null){
                //有可能有多个View需要setonclicklistener所以用数组接收
                int[] value = annotation.value();
                if (value.length > 0){
                    //遍历所有setOnClickListener的id
                    for (int i : value) {
                        //获取view
                        View onClickView = viewFinder.findViewById(i);
                        if (onClickView != null){
                            //给View设置点击监听
                            onClickView.setOnClickListener(new DeclaredOnClickListener(declaredMethod, object));
                        }
                    }
                }
            }
        }
    }

    /**
     * 属性注解
     * @param viewFinder
     * @param object activity or fragment or view 是反射的类
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {

        // object --> activity or fragment or view 是反射的类
        // viewFinder --> 只是一个view的findViewById的辅助类

        // 1. 获取所有的属性
        Class clazz = object.getClass();
        // 获取所有属性包括私有和公有
        Field[] declaredFields = clazz.getDeclaredFields();
        //遍历属性
        for (Field declaredField : declaredFields) {
            // 2. 获取属性上面FindId的值
            FindId findId = declaredField.getAnnotation(FindId.class);
            if (findId != null) {
                int value = findId.value();
                if (value != -1) {
                    // 3. 通过findViewById获取View
                    View view = viewFinder.findViewById(value);
                    if (view != null){
                        // 4. 反射注入View属性
                        // 设置所有属性都能注入包括私有和公有
                        declaredField.setAccessible(true);
                        try {
                            declaredField.set(object,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }else {
                        throw new RuntimeException("Invalid @ViewInject for "
                                + clazz.getSimpleName() + "." + declaredField.getName());
                    }
                }
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mClass;

        public DeclaredOnClickListener(Method declaredMethod, Object object) {
            this.mMethod = declaredMethod;
            this.mClass = object;
        }

        @Override
        public void onClick(View v) {
            // 设置所有方法都能注入包括私有和公有
            mMethod.setAccessible(true);
            try {
                //反射调用该方法
                mMethod.invoke(mClass,v);
            } catch (Exception e) {
                try {
                    //如果方法中没有传View,也是能走通的
                    mMethod.invoke(mClass, new Object[]{});
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}
