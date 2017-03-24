package pl.cyrzan.prowadzpatryk.service.api;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.opentripplanner.routing.core.TraverseModeSet;

import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Patryk on 07.02.2017.
 */

public interface ApiService {

    Observable<List<SuggestLocationResponse>> getSuggestLocations(String query);
    Observable<Response> getTrips(String from,
                                  String to,
                                  String time,
                                  String date,
                                  String mode,
                                  Double maxWalkDistance);
}
