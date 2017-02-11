package pl.cyrzan.prowadzpatryk.api;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Patryk on 07.02.2017.
 */

public interface ApiService {

    @GET("/otp/routers/default/plan?arriveBy=false&wheelchair=false&locale=pl")
    Call<ResponseBody> getRoutesFromTo(@Query("fromPlace") String fromPlace,
                                       @Query("toPlace") String toPlace,
                                       @Query("time") String time,
                                       @Query("date") String date,
                                       @Query("mode") String mode,
                                       @Query("maxWalkDistance") String maxWalkDistance);

    @GET("/otp/routers/default/geocode")
    Call<List<ResponseBody>> getSuggestLocations(@Query("query") String query);
}
