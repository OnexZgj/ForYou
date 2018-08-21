package com.it.onex.foryou.di.component;

import android.app.Activity;
import android.content.Context;

import com.it.onex.foryou.activity.AddTaskActivity;
import com.it.onex.foryou.di.module.ActivityModule;
import com.it.onex.foryou.di.scope.ContextLife;
import com.it.onex.foryou.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by OnexZgj on 2018/4/2.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(AddTaskActivity activity);

}
