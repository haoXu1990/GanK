package com.xuhao.gank.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xuhao.gank.R;
import com.xuhao.gank.bean.GanHuo;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class Alladapter extends BaseQuickAdapter<GanHuo, BaseViewHolder> {


    public Alladapter(int layoutResId, @Nullable List<GanHuo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GanHuo item) {

        Glide.with(mContext)
                .load(item.getUrl())
                .transition(withCrossFade())
                .into((ImageView) helper.getView(R.id.iv_fuli));



    }

}
