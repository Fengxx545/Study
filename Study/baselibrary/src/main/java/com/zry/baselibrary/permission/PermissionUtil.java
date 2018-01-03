package com.zry.baselibrary.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hasee on 2017/12/8.
 */

public class PermissionUtil {

    private PermissionUtil() {
    }

    /**
     * 判断是否需要动态申请权限
     * @return
     */
    public static boolean isNeedApplyPerminssion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 执行同意权限后的方法
     * @param object
     * @param requestCode
     */
    public static void executeSuccessMethod(Object object, int requestCode) {

        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionOK annotation = method.getAnnotation(PermissionOK.class);
            if (annotation != null) {
                int methodCode = annotation.value();
                if (methodCode == requestCode) {
                    executeMethod(method, object);
                }
            }
        }
    }

    private static void executeMethod(Method method, Object object) {
        method.setAccessible(true);
        try {
            method.invoke(object,new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取没有被允许的权限列表
     * @param object
     * @param requestPermissions
     * @return
     */
    public static List<String> getDenyPermissions(Object object, String[] requestPermissions) {
        List<String> denyPermissions = new ArrayList<>();
        for (String requestPermission : requestPermissions) {
            int i = ContextCompat.checkSelfPermission(getActivity(object), requestPermission);
            if (i == PackageManager.PERMISSION_DENIED){
                denyPermissions.add(requestPermission);
            }
        }
        return denyPermissions;
    }

    private static Activity getActivity(Object object) {
        if (object instanceof Activity)
            return (Activity) object;
        if (object instanceof Fragment)
            return ((Fragment) object).getActivity();
        return null;
    }

    /**
     * 请求权限
     * @param object
     * @param denyPermissionArray
     * @param requestCode
     */
    public static void requestPermission(Object object, String[] denyPermissionArray, int requestCode) {
        ActivityCompat.requestPermissions(getActivity(object),denyPermissionArray,requestCode);
    }

    /**
     * 执行拒绝权限后的方法
     * @param object
     * @param requestCode
     */
    public static void executeFailMethod(Object object, int requestCode) {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            PermissionFail annotation = method.getAnnotation(PermissionFail.class);
            if (annotation != null) {
                int methodCode = annotation.value();
                if (methodCode == requestCode) {
                    executeMethod(method, object);
                }
            }
        }
    }
}
