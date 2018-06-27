package com.xuhao.gank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.xuhao.gank.R;
import com.xuhao.gank.bean.GanHuo;
import com.xuhao.gank.utils.ThemeUtils;

import org.w3c.dom.Text;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class Alladapter extends BaseMultiItemQuickAdapter<GanHuo, BaseViewHolder> {


    public Context mContext;


    public Alladapter(List data, Context context){
        super(data);
        this.mContext = context;

        addItemType(GanHuo.TEXT, R.layout.item_common);
        addItemType(GanHuo.IMG, R.layout.item_fuli);

    }

    @Override
    protected void convert(BaseViewHolder helper, GanHuo item) {


        if (item.getType().equals("福利")){
            Glide.with(mContext)
                    .load(item.getUrl())
                    .transition(withCrossFade())
                    .into((ImageView) helper.getView(R.id.iv_fuli));
        }
        else {

            helper.setText(R.id.tv_item_common_title, item.getDesc());
            setIconDrawable((TextView) helper.getView(R.id.tv_item_common_title), item.getType());
        }
    }


    private void setIconDrawable(TextView view, String type) {

        IIcon icon = null;
        switch (type){
            case "Android":
                icon = MaterialDesignIconic.Icon.gmi_android;
                break;
            case "拓展资源":
                icon = FontAwesome.Icon.faw_location_arrow;
                break;
            case "iOS":
                icon = MaterialDesignIconic.Icon.gmi_apple;
                break;
            case "前端":
                icon = MaterialDesignIconic.Icon.gmi_language_javascript;
                break;
            case "App":
                icon = MaterialDesignIconic.Icon.gmi_apps;
                break;
            case "休息视频":
                icon = MaterialDesignIconic.Icon.gmi_collection_video;
                break;
            case "瞎推荐":
                icon = MaterialDesignIconic.Icon.gmi_view_comfy;
                break;
            case "福利":
                icon = MaterialDesignIconic.Icon.gmi_mood;
                break;

        }

        view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(mContext)
                        .icon(icon)
                        .color(ThemeUtils.getThemeColor(mContext, R.attr.colorPrimary))
                        .sizeDp(16),
                null, null, null);
        view.setCompoundDrawablePadding(ConvertUtils.dp2px(5));
    }

    //    public Alladapter(int layoutResId, @Nullable List<GanHuo> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, GanHuo item) {
//
//        Glide.with(mContext)
//                .load(item.getUrl())
//                .transition(withCrossFade())
//                .into((ImageView) helper.getView(R.id.iv_fuli));
//    }
//

}
