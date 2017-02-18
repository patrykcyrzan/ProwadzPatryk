package pl.cyrzan.prowadzpatryk.db.dao;

import android.text.TextUtils;

import com.squareup.sqlbrite.BriteDatabase;

import pl.cyrzan.prowadzpatryk.db.dto.RecentLocs;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.*;

/**
 * Created by Patryk on 15.02.2017.
 */

public class RecentLocsDao {

    private static final String SELECT_BY_ID =
            "SELECT * FROM " + RecentLocs.TABLE + " WHERE " + RecentLocs.ID + " = ? ";

    private static final String LIST_QUERY =
            "SELECT * FROM " + RecentLocs.TABLE + " LIMIT 5";

    public static Observable<RecentLocs> getPlayerById(BriteDatabase database, String playerId) {
        return database.createQuery(RecentLocs.TABLE, SELECT_BY_ID, playerId).mapToOne(RecentLocs.mapper());
    }
}
