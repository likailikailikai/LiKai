package com.atguigu.likai;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class WelcomeActivity extends AppCompatActivity {


    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //三秒后进入主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //进入主界面
                startMainActivity();
            }
        }, 3000);

    }

    private void startMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

        //关闭欢迎页面
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击快速进入主界面
        if(event.getAction()== MotionEvent.ACTION_DOWN) {
            startMainActivity();
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //消除信息
        handler.removeCallbacksAndMessages(null);
    }
}
