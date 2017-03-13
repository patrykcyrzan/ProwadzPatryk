package pl.cyrzan.prowadzpatryk.service.repository;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.opentripplanner.routing.core.TraverseModeSet;
import org.opentripplanner.v092snapshot.api.ws.Response;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by Patryk on 20.02.2017.
 */

public interface RepositoryService {

    Observable<List<SuggestLocationResponse>> getSuggestLocations(String query);
    Observable<Response> getTrips(String from,
                                  String to,
                                  String time,
                                  String date,
                                  String mode,
                                  Double maxWalkDistance);
}
