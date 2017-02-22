package pl.cyrzan.prowadzpatryk.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Application;


import org.joda.time.LocalDate;

import pl.cyrzan.prowadzpatryk.service.api.LocalDateDeserializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 08.02.2017.
 */

@Singleton
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return application;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }

}
