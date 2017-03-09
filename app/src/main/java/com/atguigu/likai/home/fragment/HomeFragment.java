package com.atguigu.likai.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

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
 *
 */

public class HomeFragment extends BaseFragment {


    @InjectView(R.id.rv_home)
    RecyclerView rvHome;
    @InjectView(R.id.ib_top)
    ImageButton ibTop;
    private HomeAdapter adapter;

    @Override
    public View initView() {
        Log.e("TAG", "主页视图被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        ButterKnife.inject(this, view);
        return view;
    }

    /**
     * 1.把数据绑定到控件上的时候，重新该方法
     * 2.联网请求，把得到的数据绑定到视图上
     */
    @Override
    public void initData() {
        super.initData();
        getDataFromNet();
    }


    private void getDataFromNet() {
        OkHttpUtils
                .get()
                .id(100)
                .url(Constants.HOME_URL)
                .build()
                .execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("TAG", "联网失败"+e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.e("TAG", "联网成功");
                processData(response);
            }
        });
    }

    /**
     * getItemViewType根据位置得到相应的类型
     * onCreateViewHolder当前的类型
     * onBindViewHolder绑定数据
     * @param response
     */

    private void processData(String response) {

        //使用fastjson解析json数据
        HomeBean homeBean = JSON.parseObject(response,HomeBean.class);
        Log.e("TAG", "解析数据成功=="+homeBean.getResult().getHot_info().get(0).getName());

        //设置RecycleView的适配器
        adapter = new HomeAdapter(mContext,homeBean.getResult());
        rvHome.setAdapter(adapter);
        //设置布局管理器
        rvHome.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
