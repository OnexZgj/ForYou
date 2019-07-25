package com.it.onex.foryou.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.it.onex.foryou.MainActivity;
import com.it.onex.foryou.R;
import com.it.onex.foryou.activity.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

/**
 * Created by OnexZgj on 2018/9/3:11:00.
 * des:
 */

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.atpv_as)
    SyncTextPathView atpvAs;
    private boolean study = true;
    private SPUtils spUtils;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        atpvAs.setPathPainter(new FireworksPainter());

        atpvAs.startAnimation(0, 1);



        spUtils = SPUtils.getInstance();
        study = spUtils.getBoolean("study");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (study) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    spUtils.put("study", true);
                    finish();
                }
            }
        }, 3000);


    }

}
