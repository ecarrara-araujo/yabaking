package br.com.ecarrara.yabaking.core.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import br.com.ecarrara.yabaking.BuildConfig;
import br.com.ecarrara.yabaking.core.utils.AutoValuesGsonAdapterFactory;
import br.com.ecarrara.yabaking.recipes.data.datasource.rest.UdacityRecipesApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
@Module
public class NetworkingModule {

    private final String remoteRecipeDataSourceBaseUrl;

    public NetworkingModule(String remoteRecipeDataSourceBaseUrl) {
        this.remoteRecipeDataSourceBaseUrl = remoteRecipeDataSourceBaseUrl;
    }

    @Provides
    @Singleton
    public UdacityRecipesApi providesUdacityRecipesApi(Retrofit retrofitClient) {
        return retrofitClient.create(UdacityRecipesApi.class);
    }

    @Provides
    @Singleton
    public Retrofit providesRetrofitClient(OkHttpClient okHttpClient,
                                           Converter.Factory gsonConverterFactory) {
        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(remoteRecipeDataSourceBaseUrl)
                .build();
    }

    @Provides
    @Singleton
    public Converter.Factory providesGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(AutoValuesGsonAdapterFactory.create())
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public OkHttpClient providesOkHttpClient(
            @Named("loggingInterceptor") Interceptor loggingInterceptor
    ) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Provides
    @Singleton
    @Named("loggingInterceptor")
    public Interceptor providesLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return logging;
    }

}
