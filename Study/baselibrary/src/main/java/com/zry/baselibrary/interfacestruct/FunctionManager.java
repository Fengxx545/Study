package com.zry.baselibrary.interfacestruct;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Hasee on 2017/12/18.
 */

public class FunctionManager {
    private HashMap<String,NoParamNoResultFunction> mNoParamNoResultFunctionHashMap;
    private HashMap<String,OnlyParamFunction> mOnlyParamFunctionHashMap;
    private HashMap<String,OnlyResultFunction> mOnlyResultFunctionHashMap;
    private HashMap<String,ParamResultFunction> mParamResultFunctionHashMap;

    private static FunctionManager mManager;
    private FunctionManager(){
        mNoParamNoResultFunctionHashMap = new HashMap<>();
        mOnlyParamFunctionHashMap = new HashMap<>();
        mOnlyResultFunctionHashMap = new HashMap<>();
        mParamResultFunctionHashMap = new HashMap<>();
    }
    public static FunctionManager getInstance(){
        if (mManager == null){
            mManager = new FunctionManager();
        }
        return mManager;
    }


    public FunctionManager addFunction(NoParamNoResultFunction function){
        mNoParamNoResultFunctionHashMap.put(function.mFuntionName,function);
        return this;
    }

    public FunctionManager addFunction(OnlyParamFunction function){
        mOnlyParamFunctionHashMap.put(function.mFuntionName,function);
        return this;
    }

    public FunctionManager addFunction(OnlyResultFunction function){
        mOnlyResultFunctionHashMap.put(function.mFuntionName,function);
        return this;
    }

    public void invokeFunction(String name){
        if (TextUtils.isEmpty(name)) return;

        if (mNoParamNoResultFunctionHashMap != null){
            NoParamNoResultFunction noParamNoResultFunction = mNoParamNoResultFunctionHashMap.get(name);
            if (noParamNoResultFunction != null){
                noParamNoResultFunction.function();
            }else{
                //跑出一个异常
            }
        }
    }
    public <P> void invokeFunction(String name,P param){
        if (TextUtils.isEmpty(name)) return;

        if (mOnlyParamFunctionHashMap != null){
            OnlyParamFunction onlyParamFunction = mOnlyParamFunctionHashMap.get(name);
            if (onlyParamFunction != null){
                onlyParamFunction.function(param);
            }else{
                //跑出一个异常
            }
        }
    }

    public <R> R invokeFunction(String name, Class<R> c){
        if (TextUtils.isEmpty(name)) return null;

        if (mOnlyResultFunctionHashMap != null){
            OnlyResultFunction onlyResultFunction = mOnlyResultFunctionHashMap.get(name);
            if (onlyResultFunction != null){
                if (c != null){
                    return c.cast(onlyResultFunction.function());
                }else{
                    return (R) onlyResultFunction.function();
                }

            }else{
                //跑出一个异常
            }
        }
        return null;
    }

    public <R,P> R invokeFunction(String name, Class<R> c, P param){
        if (TextUtils.isEmpty(name)) return null;

        if (mParamResultFunctionHashMap != null){
            ParamResultFunction paramResultFunction = mParamResultFunctionHashMap.get(name);
            if (paramResultFunction != null){
                if (c != null){
                    return c.cast(paramResultFunction.function(param));
                }else{
                    return (R) paramResultFunction.function(param);
                }

            }else{
                //跑出一个异常
            }
        }
        return null;
    }
}
