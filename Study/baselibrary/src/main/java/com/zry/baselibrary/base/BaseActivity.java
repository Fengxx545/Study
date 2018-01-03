package com.zry.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zry.baselibrary.ioc.ViewInject;

/**
 * Created by Hasee on 2017/12/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initContentView();

        ViewInject.inject(this);

        initTitle();

        initView();

        initData();




    }

    protected abstract void initContentView();

    protected abstract void initTitle();

    protected abstract void initView();



    protected abstract void initData();


}
