package pl.cyrzan.prowadzpatryk.service.repository;

import pl.cyrzan.prowadzpatryk.service.api.ApiService;
import pl.cyrzan.prowadzpatryk.service.db.DbService;
import pl.cyrzan.prowadzpatryk.service.preferences.PreferencesService;
import pl.cyrzan.prowadzpatryk.service.user.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 20.02.2017.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    public RepositoryService provideRepositoryService(
            ApiService apiService, PreferencesService preferencesService, UserService userService, DbService dbService) {
        return new RepositoryServiceImpl(apiService, preferencesService, userService, dbService);
    }
}
