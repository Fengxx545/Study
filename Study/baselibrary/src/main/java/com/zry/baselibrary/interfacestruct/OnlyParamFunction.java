package com.zry.baselibrary.interfacestruct;

/**
 *
 * @param <P> p代表参数的泛型
 */
public abstract class OnlyParamFunction<P> extends Function {
    public OnlyParamFunction(String funtionName) {
        super(funtionName);
    }
    public abstract void function(P param);
}
