package com.zry.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hasee on 2017/12/6.
 */


// FIELD 注解只能放在属性上    METHOD 方法上  TYPE 类上  CONSTRUCTOR 构造方法上
@Target(ElementType.METHOD)
// RUNTIME 运行时检测，CLASS 编译时butterKnife使用是这个  SOURCE 源码资源的时候
@Retention(RetentionPolicy.RUNTIME)
public @interface Onclick {
    int[] value();
}
