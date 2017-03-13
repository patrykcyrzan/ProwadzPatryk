package pl.cyrzan.prowadzpatryk.service.api;

import org.joda.time.DateTime;
import org.opentripplanner.routing.core.TraverseModeSet;
import org.opentripplanner.v092snapshot.api.ws.Response;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Patryk on 23.02.2017.
 */

public interface Api {

    public static final String API_VERSION = "api/v1";

    @GET(API_VERSION + "/gettrips")
    Observable<Response> getTrips(@Query("fromPlace") String from,
                                  @Query("toPlace") String to,
                                  @Query("time") String time,
                                  @Query("date") String date,
                                  @Query("mode") String mode,
                                  @Query("maxWalkDistance") Double maxWalkDistance);

    @GET(API_VERSION + "/getsuggestlocations")
    Observable<List<SuggestLocationResponse>> getSuggestLocations(@Query("query") String query);
}
