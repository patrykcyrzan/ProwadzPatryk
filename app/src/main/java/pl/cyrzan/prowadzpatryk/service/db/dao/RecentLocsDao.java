package pl.cyrzan.prowadzpatryk.service.db.dao;

import android.text.TextUtils;

import com.squareup.sqlbrite.BriteDatabase;

import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Patryk on 15.02.2017.
 */

public class RecentLocsDao {

    private static final String SELECT_BY_ID =
            "SELECT * FROM " + RecentLocs.TABLE + " WHERE " + RecentLocs.ID + " = ? ";

    private static final String LIST_QUERY =
            "SELECT * FROM " + RecentLocs.TABLE + " LIMIT 5";

    private static final String SELECT_ALL_RECENT_LOCS =
            "SELECT * FROM " + RecentLocs.TABLE + " LIMIT 5";

    public static Observable<RecentLocs> getPlayerById(BriteDatabase database, String playerId) {
        return database.createQuery(RecentLocs.TABLE, SELECT_BY_ID, playerId).mapToOne(RecentLocs.mapper());
    }

    public static Observable<List<RecentLocs>> getRecentLocsByQuery(BriteDatabase database, RecentLocsQuery query){
        return database.createQuery(RecentLocs.TABLE, query.sql, query.values).mapToList(RecentLocs.mapper());
    }

    public static Observable<List<RecentLocs>> get5RecentLocs(BriteDatabase database){
        return database.createQuery(RecentLocs.TABLE, SELECT_ALL_RECENT_LOCS).mapToList(RecentLocs.mapper());
    }

    public static void saveNewRecentLoc(BriteDatabase database, WrapLocation location){
        database.insert(RecentLocs.TABLE, new RecentLocs.Builder()
                .name(location.getName())
                .lat(location.getLocation().lat)
                .lon(location.getLocation().lon)
                .lastUsed(location.getLastUsed())
                .build());
    }

    public static final class SQLBuilder {
        private String sql = "SELECT * FROM " + RecentLocs.TABLE + " WHERE ";
        private final List<String> values = new ArrayList<>();

        public static SQLBuilder with() {
            return new SQLBuilder();
        }

        private String getAndSql() {
            if (!values.isEmpty()) {
                sql = sql + " and ";
            }
            return sql;
        }

        private String getOrSql() {
            if (!values.isEmpty()) {
                sql = sql + " or ";
            }
            return sql;
        }

        public SQLBuilder id(long playerId) {
            sql = getAndSql() + RecentLocs.ID + " = ? ";
            values.add(String.valueOf(playerId));
            return this;
        }

        public RecentLocsQuery build() {
            if (values.isEmpty()) {
                sql = sql.replace("WHERE", "");
            }
            return new RecentLocsQuery(sql, values.toArray(new String[values.size()]));
        }
    }

    public static class RecentLocsQuery {
        public final String sql;
        public final String[] values;


        public RecentLocsQuery(String sql, String[] values)  {
            this.sql = sql + " ORDER BY " + RecentLocs.ID + " DESC, " + RecentLocs.LAST_USED + " ASC";
            this.values = values;
        }
    }

}
