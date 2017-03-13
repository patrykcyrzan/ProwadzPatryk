package pl.cyrzan.prowadzpatryk.service.api;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
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

    @GET("/otp/routers/default/plan?arriveBy=false&wheelchair=false&locale=pl")
    Observable<Response> getTrips(@Query("fromPlace") String from,
                                  @Query("toPlace") String to,
                                  @Query("time") String time,
                                  @Query("date") String date,
                                  @Query("mode") String mode,
                                  @Query("maxWalkDistance") Double maxWalkDistance);

    @GET("/otp/routers/default/geocode")
    Observable<List<SuggestLocationResponse>> getSuggestLocations(@Query("query") String query);
}
