package br.com.ecarrara.yabaking.core.di;

import br.com.ecarrara.yabaking.core.YaBakingApplication;

public final class Injector {

    private Injector() { /* must not be constructed */ }

    private static ApplicationComponent applicationComponent;

    public static void initialize(YaBakingApplication application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public static ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

}
