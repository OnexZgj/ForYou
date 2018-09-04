package com.it.onex.foryou.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.it.onex.foryou.MainActivity;
import com.it.onex.foryou.R;

/**
 * Created by Linsa on 2018/9/3:11:00.
 * des:
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        RePlugin.preload("app");
//        SPUtils spUtils = SPUtils.getInstance();
//        boolean study = spUtils.getBoolean("study");
//        if (study) {
//            try {
//                Intent intent = RePlugin.createIntent("com.stx.xhb.enjoylife", "com.stx.xhb.enjoylife.ui.activity.SplashActivity");
//                RePlugin.startActivity(SplashActivity.this,intent);
//                spUtils.put("study", false);
//                finish();
//            } catch (Exception e) {
//                e.printStackTrace();
//                ToastUtils.showShort("插件出错！找360大大吧！");
//            }
//        }else {
//            spUtils.put("study", true);
//        }

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);


    }
}
