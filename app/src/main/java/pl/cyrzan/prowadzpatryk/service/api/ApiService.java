package pl.cyrzan.prowadzpatryk.service.api;

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
}
