package com.zry.baselibrary.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 * Created by Hasee on 2017/12/8.
 */

public class PermissionHelper {

    private Object mObject;
    private static int mRequestCode;
    private static String[] mRequestPermissions;

    /**
     * 私有构造方法
     * @param o Activity或者时fragment
     */
    private PermissionHelper(Object o) {
        this.mObject = o;
    }

    /**
     * Activity的构建
     * @param activity
     * @return
     */
    public static PermissionHelper builder(Activity activity){
        return new PermissionHelper(activity);
    }
    /**
     * Fragment的构建
     * @param fragment
     * @return
     */
    public static PermissionHelper builder(Fragment fragment){
        return new PermissionHelper(fragment);
    }

    /**
     * 传入请求码
     * @param requestCode
     * @return
     */
    public PermissionHelper requestCode(int requestCode){
        this.mRequestCode = requestCode;
        return this;
    }

    /**
     * 传入请求的权限
     * @param requestPermissions
     * @return
     */
    public PermissionHelper needPermissions(String... requestPermissions){
        this.mRequestPermissions = requestPermissions;
        return this;
    }

    /**
     * 具体执行的方法
     */
    public void apply(){
        //检查版本是否大于6.0
        if (!PermissionUtil.isNeedApplyPerminssion()){
            //不需要申请权限,我们就直接执行方法
            PermissionUtil.executeSuccessMethod(mObject,mRequestCode);
        }else {
            //大于6.0
            //获取没有被允许的权限集合
            List<String> denyPermissions = PermissionUtil.getDenyPermissions(mObject,mRequestPermissions);
            if (denyPermissions.size() > 0){
                String[] denyPermissionArray = denyPermissions.toArray(new String[denyPermissions.size()]);
                //申请权限
                PermissionUtil.requestPermission(mObject,denyPermissionArray,mRequestCode);
            }else{
                //没有被允许的权限集合为0时,说明都被允许了,就执行方法
                PermissionUtil.executeSuccessMethod(mObject,mRequestCode);
            }
        }
    }


    public static void requestPermissionsResult(Object object,int requestCode, String[] permissions) {
        // 再次获取没有授予的权限
        List<String> deniedPermissions = PermissionUtil.getDenyPermissions(object,permissions);

        if(deniedPermissions.size() == 0){
            // 权限用户都同意授予了
            PermissionUtil.executeSuccessMethod(object,requestCode);
        }else{
            // 你申请的权限中 有用户不同意的
            PermissionUtil.executeFailMethod(object,requestCode);
        }
    }
}
