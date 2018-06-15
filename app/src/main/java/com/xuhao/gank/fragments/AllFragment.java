package com.xuhao.gank.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xuhao.gank.R;
import com.xuhao.gank.adapter.Alladapter;
import com.xuhao.gank.bean.GanHuo;
import com.xuhao.gank.http.HttpRespons;
import com.xuhao.gank.http.RequstManger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class AllFragment  extends Fragment  {


    protected Alladapter mAlladapter;

    protected List<GanHuo> ganhuos = new ArrayList<>();

    protected RecyclerView mlistView;

    protected MyHandler mHandler;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected boolean mRefrshing = true;

    protected int page = 1;

    protected int pageSize = 10;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  =  inflater.inflate(R.layout.fragment_all, container, false);

        mlistView = view.findViewById(R.id.rv_fuli);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeLayout);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();

        initAdapter();

        loadData();
    }


    public String getUrl(){

        return "http://gank.io/api/data/福利/"
                + String.valueOf(pageSize) + "/"
                + String.valueOf(page);
    }

    public void initViews(){



        mHandler = new MyHandler(this);

        // 设置RecyclerView管理器
        mlistView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    public void initAdapter(){

        mAlladapter = new Alladapter(R.layout.item_fuli, ganhuos);


        // 初始化时禁止下拉刷新
        mAlladapter.setEnableLoadMore(false);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRefrshing = true;
                Log.v("allll", "SwipeRefreshLayout:下拉刷新");

                page = 1;
                // 这里加载最新数据
                loadData();
            }
        });



        mAlladapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                page++;

                // 这里加载最新数据
                loadData();
            }
        }, mlistView);



        mlistView.setAdapter(mAlladapter);

    }

    public void loadData(){

        RequstManger.shareManager().getData(getUrl(), new HttpRespons<List<GanHuo>>(new TypeToken<ArrayList<GanHuo>>(){}.getType()) {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onSuccess(List<GanHuo> ganHuo) {

                ganhuos = ganHuo;

                mHandler.sendEmptyMessage(0);
            }
        });
    }


    public void onRefresh(){


        Log.v("allll",  "获取数量" + ganhuos.size());

        // 关闭下拉刷新
        mSwipeRefreshLayout.setRefreshing(false);
        mAlladapter.loadMoreComplete();

        if (mRefrshing){
            mAlladapter.setNewData(ganhuos);
        }
        else {
            mAlladapter.addData(ganhuos);
        }

        mRefrshing = false;

    }

    static class MyHandler extends Handler {
        WeakReference<AllFragment> weakReference;

        public MyHandler(AllFragment hotfragment){

            this.weakReference = new WeakReference<>(hotfragment);
        }


        @Override
        public void handleMessage(Message msg) {

            AllFragment allFragment = weakReference.get();

            if (allFragment ==null){ return;}

            switch (msg.what){

                case 0:
                    allFragment.onRefresh();
                    break;
                default:
                    break;


            }
        }
    }
}
