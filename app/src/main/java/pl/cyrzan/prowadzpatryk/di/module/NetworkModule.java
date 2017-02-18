package pl.cyrzan.prowadzpatryk.di.module;

import pl.cyrzan.prowadzpatryk.api.ApiFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 11.02.2017.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public ApiFactory provideApiFactory(){
        return new ApiFactory();
    }
}
