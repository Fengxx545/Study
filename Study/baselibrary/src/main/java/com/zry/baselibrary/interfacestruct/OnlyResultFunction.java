package com.zry.baselibrary.interfacestruct;

/**
 *
 * @param <R>R代表result 结果的泛型
 */

public abstract class OnlyResultFunction<R> extends Function {
    public OnlyResultFunction(String funtionName) {
        super(funtionName);
    }
    public abstract R function();
}
