package com.xuhao.gank.http;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequstManger {


    static RequstManger manger;
    static OkHttpClient client;

    // 定义一个Hnader 用于线程切换, 这样在回掉代码出就可以直接更新UI
    private static android.os.Handler mhander = new Handler(Looper.getMainLooper());


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

                    final String data = response.body().string();

                    mhander.post(new Runnable() {
                        @Override
                        public void run() {
                            respons.parse(data);
                        };
                    });

                }
                else {

                    mhander.post(new Runnable() {
                        @Override
                        public void run() {
                            respons.onError("请求服务器失败");
                        };
                    });
                }
            }
        });

    }
}
