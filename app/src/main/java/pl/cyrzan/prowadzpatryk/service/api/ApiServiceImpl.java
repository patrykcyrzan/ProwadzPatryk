package pl.cyrzan.prowadzpatryk.service.api;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.opentripplanner.routing.core.TraverseModeSet;

import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Patryk on 20.02.2017.
 */

public class ApiServiceImpl implements ApiService {

    private Api api;

    @Inject
    public ApiServiceImpl(Api api) {
        this.api = api;
    }

    @Override
    public Observable<List<SuggestLocationResponse>> getSuggestLocations(String query) {
        return api.getSuggestLocations(query);
    }

    @Override
    public Observable<Response> getTrips(String from, String to, String time, String date, String mode, Double maxWalkDistance) {
        return api.getTrips(from, to, time, date, mode, maxWalkDistance);
    }

    //Observable<ResponseBody> getRoutesFromTo();
}
