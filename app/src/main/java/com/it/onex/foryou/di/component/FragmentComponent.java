package com.it.onex.foryou.di.component;

import android.app.Activity;
import android.content.Context;

import com.it.onex.foryou.di.module.FragmentModule;
import com.it.onex.foryou.di.scope.ContextLife;
import com.it.onex.foryou.di.scope.PerFragment;
import com.it.onex.foryou.fragment.done.DoneFragment;
import com.it.onex.foryou.fragment.undone.UndoneFragment;

import dagger.Component;

/**
 * Created by OnexZgj on 2018/5/4.
 * 使用的是Dagger2的方法和参数
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(UndoneFragment fragment);


    void inject(DoneFragment fragment);




}
