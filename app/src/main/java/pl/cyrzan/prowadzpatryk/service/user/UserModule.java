package pl.cyrzan.prowadzpatryk.service.user;

import pl.cyrzan.prowadzpatryk.service.preferences.PreferencesService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 20.02.2017.
 */

@Module
public class UserModule {

    @Singleton
    @Provides
    public UserService provideUserService(PreferencesService preferencesService) {
        return new UserServiceImpl(preferencesService);
    }
}
