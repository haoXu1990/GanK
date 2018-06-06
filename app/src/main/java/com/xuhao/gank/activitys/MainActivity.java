package com.xuhao.gank.activitys;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.reflect.TypeToken;
import com.xuhao.gank.R;
import com.xuhao.gank.bean.GanHuo;
import com.xuhao.gank.fragments.AllFragment;
import com.xuhao.gank.http.HttpRespons;
import com.xuhao.gank.http.RequstManger;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {


    private ImageView mAvatar;

    private ArrayList<GanHuo> mGanhuos = new ArrayList<>();

    // 管理器
    private FragmentManager mFragmentManager;

    String mCurrentFragmentTag;

    RequestOptions mOptions;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        switchFragment("all");

        loadData();

    }


    public void loadData(){

        String url = "http://gank.io/api/data/福利/1/1";


        RequstManger.shareManager().getData(url, new HttpRespons<List<GanHuo>>(new TypeToken<ArrayList<GanHuo>>(){}.getType()) {
            @Override
            public void onError(String msg) {


            }

            @Override
            public void onSuccess(List<GanHuo> ganHuo) {

                Log.v("ganHuo", ganHuo.toString());
            }
        });


    }

    public void  initView(){

        mAvatar = findViewById(R.id.avatar);

        // 获取Fragment管理器
        mFragmentManager = getSupportFragmentManager();



        mOptions = new RequestOptions().circleCrop()
                .transforms(new CenterCrop(), new RoundedCorners(40));

        // 设置头像默认图片
        Glide.with(this)
                .load(R.drawable.avatar)
                .apply(mOptions)
                .transition(withCrossFade())
                .into(mAvatar);

    }


    public void switchFragment(String name){

        // 检查参数 , 如果当前 tag 为空或则传入的参数名和当前tag相同的话就返回
        if (mCurrentFragmentTag != null && mCurrentFragmentTag.equals(name)){
            return;
        }

        // 设置转场动画
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        // 设置转场动画样式
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);

        // 根据tag 找到当前显示的Fragment
        Fragment currentFragment = mFragmentManager.findFragmentByTag(mCurrentFragmentTag);

        // 根据tag 找到将要显示的Fragment
        Fragment foundFragment = mFragmentManager.findFragmentByTag(name);

        if (foundFragment == null) {
            foundFragment = new AllFragment();
        }

        if (foundFragment.isAdded()) {

            // 显示Fragment
            ft.show(foundFragment);
        }
        else {

            // 把找到的Fragment 放入到FragmentManger中
            ft.add(R.id.container, foundFragment, "all");
        }

        ft.commit();

        mCurrentFragmentTag = name;

    }


}
