package pl.cyrzan.prowadzpatryk.service.repository;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.opentripplanner.routing.core.TraverseModeSet;

import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;

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
    Observable<List<RecentLocs>> get5RecentLocs();
    void saveNewRecentLoc(WrapLocation location);
}
