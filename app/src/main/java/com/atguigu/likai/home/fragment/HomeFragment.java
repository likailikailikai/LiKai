package com.atguigu.likai.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.likai.R;
import com.atguigu.likai.base.BaseFragment;
import com.atguigu.likai.home.adapter.HomeAdapter;
import com.atguigu.likai.home.bean.HomeBean;
import com.atguigu.likai.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 情v枫 on 2017/2/24.
 */

public class HomeFragment extends BaseFragment {


    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.Swipe_refresh_layout)
    SwipeRefreshLayout SwipeRefreshLayout;
    private HomeAdapter adapter;

    private String url;
    @Override
    public View initView() {
        Log.e("TAG", "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        //设置下拉多少距离起作用
        SwipeRefreshLayout.setDistanceToTriggerSync(100);

        SwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);//设置不同颜色
        //设置背景的颜色
        SwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.holo_blue_bright);
        //设置下拉刷新的监听
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(mContext, "我被拉下来了", Toast.LENGTH_SHORT).show();
                initData();
            }
        });

        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        getDataFromNet(url);
    }


    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .id(100)
                .url(Constants.HOME_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "联网失败" + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "联网成功");
                        processData(response);
                        SwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    /**
     * getItemViewType根据位置得到相应的类型
     * onCreateViewHolder当前的类型
     * onBindViewHolder绑定数据
     *
     * @param response
     */

    private void processData(String response) {

        //使用fastjson解析json数据
        HomeBean homeBean = JSON.parseObject(response, HomeBean.class);
        Log.e("TAG", "解析数据成功==" + homeBean.getResult().getHot_info().get(0).getName());

        //设置RecycleView的适配器
        adapter = new HomeAdapter(mContext, homeBean.getResult());
        recyclerview.setAdapter(adapter);
        //设置布局管理器
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }
}
