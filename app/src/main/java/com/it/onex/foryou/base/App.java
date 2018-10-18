package com.it.onex.foryou.base;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.it.onex.foryou.R;
import com.it.onex.foryou.di.component.ApplicationComponent;
import com.it.onex.foryou.di.component.DaggerApplicationComponent;
import com.it.onex.foryou.di.module.ApplicationModule;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created by OnexZgj on 2018/1/18.
 * des : App
 */

public class App extends Application {
    private ApplicationComponent mApplicationComponent;
    private static App mInstance;
    public Vibrator mVibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        CrashReport.initCrashReport(getApplicationContext(), "e81cc8ae10", false);
        initApplicationComponent();
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        Utils.init(this);
        intARouter();
        Logger.addLogAdapter(new AndroidLogAdapter());

        //配置ToastUtils的相关的属性
        ToastUtils.setGravity(Gravity.TOP,0, (int) (80 * Utils.getApp().getResources().getDisplayMetrics().density + 0.5));
        ToastUtils.setBgColor(getResources().getColor(R.color.colorWhite));
        ToastUtils.setMsgColor(getResources().getColor(R.color.colorAccent));

//        FlowManager.init(this);
    }

    /**
     * 初始化路由
     */
    private void intARouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    public static App getInstance() {
        return mInstance;
    }
}
