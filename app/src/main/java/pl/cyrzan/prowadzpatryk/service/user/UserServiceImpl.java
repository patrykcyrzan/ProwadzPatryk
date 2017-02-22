package pl.cyrzan.prowadzpatryk.service.user;

import pl.cyrzan.prowadzpatryk.service.preferences.PreferencesService;

import javax.inject.Inject;

/**
 * Created by Patryk on 20.02.2017.
 */

public class UserServiceImpl implements UserService {

    private PreferencesService preferencesService;

    @Inject
    public UserServiceImpl(PreferencesService preferencesService) {
        this.preferencesService = preferencesService;
    }
}
