package com.zry.study;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zry.baselibrary.dialog.BaseDialog;
import com.zry.baselibrary.ioc.FindId;
import com.zry.baselibrary.permission.PermissionFail;
import com.zry.baselibrary.permission.PermissionHelper;
import com.zry.baselibrary.permission.PermissionOK;
import com.zry.extendlibrary.OtherActivity;

public class MainActivity extends OtherActivity{


    /**
     * test
     */
    @FindId(R.id.test1)
    private TextView mTest1;
    private String mPhoneNum = "12121211315";

    @Override
    protected void initContentView() {
        setContentView(R.layout.main);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

    }

    private static final int CALL_PHONE_REQUEST_CODE= 0x001;

    @Override
    protected void initData() {
        mTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDialog dialog = new BaseDialog.Builder(MainActivity.this)
                        .setView(R.layout.dialog_view)
                        .setText(R.id.texttv_1,"hahahh")
                        .setDefaultAnimation()
                        .setOnClickListener(R.id.level_view, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"beidianjile",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }
    @PermissionOK(value = CALL_PHONE_REQUEST_CODE)
    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:"+mPhoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @PermissionFail(value = CALL_PHONE_REQUEST_CODE)
    private void callPhonePermissionFail(){
        Toast.makeText(this,"您拒绝了权限",Toast.LENGTH_SHORT).show();
    }


   /* @Onclick(R.id.test1)
    public void testClick(View view){
        Toast.makeText(this,"真的成功了",Toast.LENGTH_SHORT).show();
//        PermissionHelper.builder(this)
//                .requestCode(CALL_PHONE_REQUEST_CODE)
//                .needPermissions(Manifest.permission.CALL_PHONE)
//                .apply();
//        Intent intent = new Intent(this,CityActivity.class);
//        startActivity(intent);

        BaseDialog dialog = new BaseDialog.Builder(this)
                .setView(R.layout.dialog_view)
                .setText(R.id.texttv_1,"hahahh")
                .setDefaultAnimation()
                .setOnClickListener(R.id.level_view, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,"beidianjile",Toast.LENGTH_SHORT).show();
                    }
                }).show();

    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.requestPermissionsResult(this,requestCode,permissions);
    }
}
