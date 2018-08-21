package com.it.onex.foryou.di.component;

import android.content.Context;

import com.it.onex.foryou.di.module.ServiceModule;
import com.it.onex.foryou.di.scope.ContextLife;
import com.it.onex.foryou.di.scope.PerService;

import dagger.Component;


/**
 * Created by OnexZgj on 2017/1/19.
 */
@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
