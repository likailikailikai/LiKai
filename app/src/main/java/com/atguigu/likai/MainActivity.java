package com.atguigu.likai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.likai.base.BaseFragment;
import com.atguigu.likai.community.fragment.CommunityFragment;
import com.atguigu.likai.home.fragment.HomeFragment;
import com.atguigu.likai.shoppingcart.fragment.ShoppingCartFragment;
import com.atguigu.likai.home.fragment.TypeFragment;
import com.atguigu.likai.home.fragment.UserFragemnt;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fl)
    FrameLayout mainFl;
    @InjectView(R.id.main_rg)
    RadioGroup mainRg;

    /**
     * 装五个Fragment的集合
     */
    private ArrayList<BaseFragment> fragments;

    /**
     * 对应的位置
     */
    private int position;

    /**
     * 被实现的Fragment
     */
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //初始化Fragment
        initFragment();

        //监听RadioGroup的变化，切换不同的Fragment
        initListener();
    }

    private void initListener() {
        mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        position = 0;
                        break;
                    case R.id.rb_type:
                        position = 1;
                        break;
                    case R.id.rb_community:
                        position = 2;
                        break;
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    case R.id.rb_user:
                        position = 4;
                        break;
                }
                //根据位置切换到对应的Fragment
                Fragment currentFragment = fragments.get(position);
                switchFragment(currentFragment);
            }
        });

        //默认选中主页面
        mainRg.check(R.id.rb_home);
    }

    private void switchFragment(Fragment currentFragment) {
        //切换的不是同一个界面
        if(tempFragment!=currentFragment) {
            //得到FragmentManger
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            //没有就添加
            if(!currentFragment.isAdded()) {
                //缓存隐藏
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //添加
                ft.add(R.id.main_fl,currentFragment);
            }else{
                //缓存隐藏
                if(tempFragment != null) {
                    ft.hide(tempFragment);
                }
                //显示
                ft.show(currentFragment);
            }
            //事务提交
            ft.commit();

            //把当前的赋值成缓存
            tempFragment = currentFragment;
        }
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragemnt());

    }
}
