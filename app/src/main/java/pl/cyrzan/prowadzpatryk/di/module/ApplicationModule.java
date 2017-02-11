package pl.cyrzan.prowadzpatryk.di.module;

import android.app.Application;
import android.content.Context;

import pl.cyrzan.prowadzpatryk.api.ApiFactory;
import pl.cyrzan.prowadzpatryk.api.ApiService;
import pl.cyrzan.prowadzpatryk.di.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 08.02.2017.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    static ApiService provideApiService() {
        return ApiFactory.makeApiService();
    }

}
