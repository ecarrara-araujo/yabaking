package br.com.ecarrara.yabaking.core.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.ecarrara.yabaking.core.YaBakingApplication;
import br.com.ecarrara.yabaking.core.utils.RxEventBus;
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

    @Provides
    @Singleton
    @Named("stepSelectedEventBus")
    public RxEventBus<Integer> providesStepSelectedEventBus() {
        return new RxEventBus<>();
    }

}
