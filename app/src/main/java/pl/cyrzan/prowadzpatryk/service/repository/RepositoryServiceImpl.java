package pl.cyrzan.prowadzpatryk.service.repository;

import pl.cyrzan.prowadzpatryk.service.api.ApiService;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.preferences.PreferencesService;
import pl.cyrzan.prowadzpatryk.service.user.UserService;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;

/**
 * Created by Patryk on 20.02.2017.
 */

public class RepositoryServiceImpl implements RepositoryService {

    private ApiService apiService;
    private PreferencesService preferencesService;
    private UserService userService;

    @Inject
    public RepositoryServiceImpl(ApiService apiService, PreferencesService preferencesService, UserService userService) {
        this.apiService = checkNotNull(apiService, "ApiService cannot be null");
        this.preferencesService = checkNotNull(preferencesService, "PreferencesService cannot be null");
        this.userService = checkNotNull(userService, "UserService cannot be null");
    }

    @Override
    public Observable<List<SuggestLocationResponse>> getSuggestLocations(String query) {
        return apiService.getSuggestLocations(query);
    }
}
