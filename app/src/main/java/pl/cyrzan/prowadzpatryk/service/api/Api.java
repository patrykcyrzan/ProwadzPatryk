package pl.cyrzan.prowadzpatryk.service.api;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Patryk on 20.02.2017.
 */

public interface Api {

    @GET("/otp/routers/default/plan?arriveBy=false&wheelchair=false&locale=pl")
    Observable<ResponseBody> getRoutesFromTo(@Query("fromPlace") String fromPlace,
                                             @Query("toPlace") String toPlace,
                                             @Query("time") String time,
                                             @Query("date") String date,
                                             @Query("mode") String mode,
                                             @Query("maxWalkDistance") String maxWalkDistance);

    @GET("/otp/routers/default/geocode")
    Observable<List<SuggestLocationResponse>> getSuggestLocations(@Query("query") String query);
}
