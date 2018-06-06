package com.xuhao.gank.http;

import android.text.TextUtils;

import com.xuhao.gank.utils.JsonUtil;

import java.lang.reflect.Type;

public abstract class HttpRespons<T> {

    Class<T> t;

    public Type type;

    public HttpRespons(Type t) {
        this.type = t;
    }


    public abstract void onError(String msg);

    public abstract void onSuccess(T t);

    public void parse(String json){

        if (TextUtils.isEmpty(json)){

            onError("网络请求失败");
            return;
        }

        if (t == String.class){
            onSuccess((T) json);
            return;
        }

        // 开始解析Json
        T res = JsonUtil.parseJson(json, type);

        if (res != null){
            onSuccess(res);
        }
        else {
            onError("json解析失败");
        }

    }
}
