package com.xuhao.gank.http;


import com.xuhao.gank.bean.GanHuo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequstManger {


    static RequstManger manger;
    static OkHttpClient client;

    private RequstManger() {
        client = new OkHttpClient();
    }

    /*
    *
    *  创建一个单列
    * */
    public static RequstManger shareManager(){

        if (manger == null){
            synchronized (RequstManger.class){
                if (manger == null){
                    manger = new RequstManger();
                }
            }
        }
        return manger;
    }


    public void getData(String url, final HttpRespons respons){

        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                respons.onError("网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()){

                    String data = response.body().string();

                    respons.parse(data);
                }
                else {
                    respons.onError("请求服务器失败");
                }
            }
        });

    }
}
