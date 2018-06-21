package com.xuhao.gank.activitys;

import android.graphics.Color;
import android.os.Bundle;
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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.wuhenzhizao.titlebar.utils.AppUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.xuhao.gank.R;
import com.xuhao.gank.bean.GanHuo;
import com.xuhao.gank.fragments.AllFragment;
import com.xuhao.gank.utils.ThemeUtils;
import com.xuhao.gank.widget.ResideLayout;


import java.util.ArrayList;

import javax.crypto.Mac;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.icon_titlebar) ImageView mIcon;
    @BindView(R.id.tv_titlebar) TextView mTtitlebar;
    @BindView(R.id.resideLayout) ResideLayout mResideLayout;






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





        setIconDrawable(mAll, MaterialDesignIconic.Icon.gmi_view_comfy);
        setIconDrawable(mFuli, MaterialDesignIconic.Icon.gmi_mood);
        setIconDrawable(mAndroid, MaterialDesignIconic.Icon.gmi_android);
        setIconDrawable(mIos, MaterialDesignIconic.Icon.gmi_apple);
        setIconDrawable(mVideo, MaterialDesignIconic.Icon.gmi_collection_video);
        setIconDrawable(mFront, MaterialDesignIconic.Icon.gmi_language_javascript);
        setIconDrawable(mResource, FontAwesome.Icon.faw_location_arrow);
        setIconDrawable(mApp, MaterialDesignIconic.Icon.gmi_apps);
//        setIconDrawable(mAbout, MaterialDesignIconic.Icon.gmi_account);
//        setIconDrawable(mTheme, MaterialDesignIconic.Icon.gmi_palette);
//        setIconDrawable(mMore, MaterialDesignIconic.Icon.gmi_more);

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


    private void setIconDrawable(TextView view, IIcon icon) {
        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(this)
                        .icon(icon)
                        .color(Color.WHITE)
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(5));
    }

}
