package br.com.ecarrara.yabaking.core;

import android.app.Application;

import br.com.ecarrara.yabaking.core.di.Injector;

public class YaBakingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initialize(this);
    }

}
