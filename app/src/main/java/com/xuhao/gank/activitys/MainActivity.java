package com.xuhao.gank.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wuhenzhizao.titlebar.utils.AppUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xuhao.gank.R;
import com.xuhao.gank.bean.GanHuo;
import com.xuhao.gank.fragments.AllFragment;
import com.xuhao.gank.http.HttpRespons;
import com.xuhao.gank.http.RequstManger;
import com.xuhao.gank.utils.ThemeUtils;
import com.xuhao.gank.widget.ResideLayout;


import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.all) TextView mAll;
    @BindView(R.id.fuli) TextView mFuli;
    @BindView(R.id.android) TextView mAndroid;
    @BindView(R.id.ios) TextView mIos;
    @BindView(R.id.video) TextView mVideo;
    @BindView(R.id.front) TextView mFront;
    @BindView(R.id.resource) TextView mResource;
    @BindView(R.id.app) TextView mApp;
    @BindView(R.id.more) TextView mMore;
    @BindView(R.id.about) TextView mAbout;

    @BindView(R.id.icon_titlebar) ImageView mIcon;
    @BindView(R.id.tv_titlebar) TextView mTtitlebar;
    @BindView(R.id.resideLayout) ResideLayout mResideLayout;

    @BindView(R.id.iv_main_avatar) ImageView mAvatar;
    @BindView(R.id.tv_main_desc) TextView mDesc;


    private ArrayList<GanHuo> mGanhuos = new ArrayList<>();

    private String typeStr = "福利";

    // 管理器
    private FragmentManager mFragmentManager;

    String mCurrentFragmentTag;

    RequestOptions mOptions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 绑定View
        ButterKnife.bind(this);

        initView();

        mIcon.setImageDrawable(new IconicsDrawable(this)
                .color(Color.WHITE)
                .icon(MaterialDesignIconic.Icon.gmi_view_comfy)
                .sizeDp(20));
        mTtitlebar.setText("干货集中营");
        switchFragment("all");


    }

    public void  initView(){



        // 获取Fragment管理器
        mFragmentManager = getSupportFragmentManager();

        mOptions = new RequestOptions().circleCrop()
                .transforms(new CircleCrop(), new RoundedCorners(40));


        RequstManger.shareManager().getData(getUrl(), new HttpRespons<List<GanHuo>>(new TypeToken<ArrayList<GanHuo>>(){}.getType()) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(List<GanHuo> ganHuo) {

                mDesc.setText(ganHuo.get(0).getDesc());

                Glide.with(MainActivity.this)
                        .load(ganHuo.get(0).getUrl())
                        .apply(mOptions)
                        .transition(withCrossFade())
                        .into(mAvatar);
            }
        });


        setIconDrawable(mAll, MaterialDesignIconic.Icon.gmi_view_comfy);
        setIconDrawable(mFuli, MaterialDesignIconic.Icon.gmi_mood);
        setIconDrawable(mAndroid, MaterialDesignIconic.Icon.gmi_android);
        setIconDrawable(mIos, MaterialDesignIconic.Icon.gmi_apple);
        setIconDrawable(mVideo, MaterialDesignIconic.Icon.gmi_collection_video);
        setIconDrawable(mFront, MaterialDesignIconic.Icon.gmi_language_javascript);
        setIconDrawable(mResource, FontAwesome.Icon.faw_location_arrow);
        setIconDrawable(mApp, MaterialDesignIconic.Icon.gmi_apps);
        setIconDrawable(mAbout, MaterialDesignIconic.Icon.gmi_account);
        setIconDrawable(mMore, MaterialDesignIconic.Icon.gmi_more);

    }


    public String getUrl(){

        return "http://gank.io/api/data/" + typeStr + "/"
                + String.valueOf(1) + "/"
                + String.valueOf(1);
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
            //ft.add(R.id.container, foundFragment, name);
            ft.replace(R.id.container, foundFragment, name);
        }

        ft.commit();

        mCurrentFragmentTag = name;

    }

    @OnClick({R.id.icon_titlebar, R.id.all,R.id.fuli, R.id.android, R.id.ios, R.id.video, R.id.front, R.id.resource})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.icon_titlebar:
                mResideLayout.openPane();
                break;
            case R.id.all:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_mood).sizeDp(20));
                mTtitlebar.setText("干货集中营");
                switchFragment("all");
                break;
            case R.id.fuli:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_mood).sizeDp(20));
                mTtitlebar.setText("福利");
                switchFragment("福利");
                break;
            case R.id.android:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_android).sizeDp(20));
                mTtitlebar.setText("Android");
                switchFragment("Android");
                break;
            case R.id.ios:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_apple).sizeDp(20));
                mTtitlebar.setText("iOS");
                switchFragment("iOS");
                break;
            case R.id.video:
                mResideLayout.closePane();
                mIcon.setImageDrawable(new IconicsDrawable(this).color(Color.WHITE).icon(MaterialDesignIconic.Icon.gmi_collection_video).sizeDp(20));
                mTtitlebar.setText("休息视频");
                switchFragment("休息视频");
                break;
        }
    }



    @Override
    public void onBackPressed() {


        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        }
        else  {
            super.onBackPressed();
        }
    }

    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(5));
    }

}
