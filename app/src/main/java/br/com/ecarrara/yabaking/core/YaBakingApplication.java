package br.com.ecarrara.yabaking.core;

import android.app.Application;

import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import br.com.ecarrara.yabaking.core.data.DBFlowRecipeDatabase;
import br.com.ecarrara.yabaking.core.di.Injector;

public class YaBakingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initializeOrm();
        Injector.initialize(this);
    }

    private void initializeOrm() {
        FlowManager.init(
                FlowConfig.builder(this)
                        .addDatabaseConfig(
                                DatabaseConfig.builder(DBFlowRecipeDatabase.class)
                                .databaseName(DBFlowRecipeDatabase.NAME)
                                .build())
                        .build()

        );

    }

}
