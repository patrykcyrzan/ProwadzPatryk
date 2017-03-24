package pl.cyrzan.prowadzpatryk.service.db;

import com.squareup.sqlbrite.BriteDatabase;

import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dao.RecentLocsDao;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by Patryk on 14.03.2017.
 */

public class DbServiceImpl implements DbService {

    @Inject
    BriteDatabase database;

    @Inject
    public DbServiceImpl(BriteDatabase database){
        this.database = database;
    }

    @Override
    public Observable<List<RecentLocs>> getRecentLocsByQuery(RecentLocsDao.RecentLocsQuery query) {
        return RecentLocsDao.getRecentLocsByQuery(database, query);
    }

    @Override
    public Observable<List<RecentLocs>> get5RecentLocs() {
        return RecentLocsDao.get5RecentLocs(database);
    }

    @Override
    public void saveNewRecentLoc(WrapLocation location) {
        RecentLocsDao.saveNewRecentLoc(database, location);
    }
}
