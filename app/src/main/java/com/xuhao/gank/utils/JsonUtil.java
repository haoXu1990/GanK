package com.xuhao.gank.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class JsonUtil {

    private static final String ERROR = "error";
    private static final String RESULTS = "results";

    static Gson mGson;

    public static <T> T parseJson(String json, Type tClass) {

        if (mGson == null){
            mGson = new Gson();
        }

        if (TextUtils.isEmpty(json)){
            return null;
        }


        String result = fetchJsonResults(json);

        if (result != null){

            return mGson.fromJson(result, tClass);
        }

        return null;
    }


    public static String fetchJsonResults(String json){

        try {

            JSONObject jsonObject = new JSONObject(json);

            // 判断是否存在error字段
            if (jsonObject.isNull(ERROR)){

                return null;
            }
            // 判断error字段结果, true 成功， false 失败
            if (jsonObject.getBoolean(ERROR)){
                return null;
            }

            // 判断RESULTS 字段是否存在
            if (jsonObject.isNull(RESULTS)){
                return null;
            }

            return jsonObject.getString(RESULTS);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
