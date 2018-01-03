package com.zry.baselibrary.interfacestruct;


/**
 *
 * @param <R>   返回结果的泛型
 * @param <P>  参数的泛型
 */

public abstract class ParamResultFunction<R,P> extends Function {
    public ParamResultFunction(String funtionName) {
        super(funtionName);
    }
    public abstract R function(P p);
}
