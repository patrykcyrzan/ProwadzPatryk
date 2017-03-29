package pl.cyrzan.prowadzpatryk.service.db.dto;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.gabrielittner.auto.value.cursor.ColumnName;
import com.google.auto.value.AutoValue;
import rx.functions.Func1;

/**
 * Created by Patryk on 15.02.2017.
 */

@AutoValue
public abstract class RecentLocs implements Parcelable{

    public static final String TABLE = "rec_locs";

    public static final String ID = "_id";
    public static final String LAT = "lat";
    public static final String LON = "lon";
    public static final String NAME = "name";
    public static final String LAST_USED = "last_used";

    @ColumnName(ID)
    public abstract long id();

    @ColumnName(LAT)
    public abstract double lat();

    @ColumnName(LON)
    public abstract double lon();

    @Nullable
    @ColumnName(NAME)
    public abstract String name();

    @ColumnName(LAST_USED)
    public abstract long lastUsed();

    public static Func1<Cursor, RecentLocs> mapper() {
        return AutoValue_RecentLocs.MAPPER;
    }

    public static final class Builder {
        private final ContentValues values = new ContentValues();

        public Builder id(long id) {
            values.put(ID, id);
            return this;
        }

        public Builder name(String name) {
            values.put(NAME, name);
            return this;
        }

        public Builder lastUsed(long lastUsed) {
            values.put(LAST_USED, lastUsed);
            return this;
        }

        public Builder lat(double lat) {
            values.put(LAT, lat);
            return this;
        }

        public Builder lon(double lon) {
            values.put(LON, lon);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
