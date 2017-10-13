package br.com.ecarrara.yabaking.core.di;

import br.com.ecarrara.yabaking.core.YaBakingApplication;

import static br.com.ecarrara.yabaking.core.networking.ApiConstants.RECIPES_SERVICE_BASE_URL;

public final class Injector {

    private Injector() { /* must not be constructed */ }

    private static ApplicationComponent applicationComponent;

    public static void initialize(YaBakingApplication application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .networkingModule(new NetworkingModule(RECIPES_SERVICE_BASE_URL))
                .build();
    }

    public static ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

}
