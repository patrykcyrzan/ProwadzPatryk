package pl.cyrzan.prowadzpatryk.service.db;

import com.squareup.sqlbrite.BriteDatabase;

import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dao.RecentLocsDao;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;

import java.util.List;

import rx.Observable;

/**
 * Created by Patryk on 14.03.2017.
 */

public interface DbService {

    Observable<List<RecentLocs>> getRecentLocsByQuery(RecentLocsDao.RecentLocsQuery query);

    Observable<List<RecentLocs>> get5RecentLocs();

    void saveNewRecentLoc(WrapLocation location);
}
