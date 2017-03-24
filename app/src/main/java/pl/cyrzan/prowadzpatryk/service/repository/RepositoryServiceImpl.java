package pl.cyrzan.prowadzpatryk.service.repository;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.opentripplanner.routing.core.TraverseModeSet;

import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.api.ApiService;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.db.DbService;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
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
    private DbService dbService;

    @Inject
    public RepositoryServiceImpl(ApiService apiService, PreferencesService preferencesService, UserService userService, DbService dbService) {
        this.apiService = checkNotNull(apiService, "ApiService cannot be null");
        this.preferencesService = checkNotNull(preferencesService, "PreferencesService cannot be null");
        this.userService = checkNotNull(userService, "UserService cannot be null");
        this.dbService = checkNotNull(dbService, "DbService cannot be null");
    }

    @Override
    public Observable<List<SuggestLocationResponse>> getSuggestLocations(String query) {
        return apiService.getSuggestLocations(query);
    }

    @Override
    public Observable<Response> getTrips(String from, String to, String time, String date, String mode, Double maxWalkDistance) {
        return apiService.getTrips(from, to, time, date, mode, maxWalkDistance);
    }

    @Override
    public Observable<List<RecentLocs>> get5RecentLocs() {
        return dbService.get5RecentLocs();
    }

    @Override
    public void saveNewRecentLoc(WrapLocation location) {
        dbService.saveNewRecentLoc(location);
    }
}
