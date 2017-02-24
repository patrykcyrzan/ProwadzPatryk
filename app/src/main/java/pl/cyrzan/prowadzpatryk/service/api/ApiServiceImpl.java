package pl.cyrzan.prowadzpatryk.service.api;

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

    //Observable<ResponseBody> getRoutesFromTo();
}
