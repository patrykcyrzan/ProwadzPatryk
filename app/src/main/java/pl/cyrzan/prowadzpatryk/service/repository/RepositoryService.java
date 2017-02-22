package pl.cyrzan.prowadzpatryk.service.repository;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by Patryk on 20.02.2017.
 */

public interface RepositoryService {

    Observable<List<SuggestLocationResponse>> getSuggestLocations(String query);
}
