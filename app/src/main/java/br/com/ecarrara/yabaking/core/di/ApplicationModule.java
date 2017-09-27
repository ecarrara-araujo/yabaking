package br.com.ecarrara.yabaking.core.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import br.com.ecarrara.yabaking.core.YaBakingApplication;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ApplicationModule {

    private YaBakingApplication applicationContext;

    public ApplicationModule(@NonNull YaBakingApplication yaBakingApplication) {
        this.applicationContext = yaBakingApplication;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return this.applicationContext;
    }

}
