package pl.cyrzan.prowadzpatryk.service.api;

import com.google.gson.Gson;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import pl.cyrzan.prowadzpatryk.BuildConfig;
import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Patryk on 23.02.2017.
 */

@Module
public class ApiModule {

    @Singleton
    @Provides
    public ApiService provideApiService(Api api) {
        return new ApiServiceImpl(api);
    }

    @Singleton
    @Provides
    @Named("api")
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.OTP_API_URL)
                .client(makeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .build();
    }

    @Singleton
    @Provides
    public Api provideApi(@Named("api") Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    private static OkHttpClient makeOkHttpClient() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message
                    -> Timber.d(message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
            httpClientBuilder.addNetworkInterceptor(new StethoInterceptor());
        }

        return httpClientBuilder.build();
    }
}
