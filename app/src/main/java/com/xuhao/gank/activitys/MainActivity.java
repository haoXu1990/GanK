package com.xuhao.gank.activitys;

import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xuhao.gank.R;
import com.xuhao.gank.fragments.AllFragment;

public class MainActivity extends AppCompatActivity {


    private ImageView mAvatar;


    // 管理器
    private FragmentManager mFragmentManager;

    String mCurrentFragmentTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

        switchFragment("all");

    }

    public void  initView(){

        View view = LayoutInflater.from(this).inflate(R.layout.activity_main,null);


        mAvatar = view.findViewById(R.id.avatar);

        // 获取Fragment管理器
        mFragmentManager = getSupportFragmentManager();


        // 设置头像默认图片
        Glide.with(MainActivity.this)
                .load(R.mipmap.avatar)
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
