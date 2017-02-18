package pl.cyrzan.prowadzpatryk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.cyrzan.prowadzpatryk.db.dto.RecentLocs;

/**
 * Created by Patryk on 15.02.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String CREATE_RECENT_LOCS = ""
            + "CREATE TABLE "
            + RecentLocs.TABLE
            + " ("
            + RecentLocs.ID
            + " INTEGER NOT NULL PRIMARY KEY, "
            + RecentLocs.LAT
            + " INTEGER NOT NULL DEFAULT 0, "
            + RecentLocs.LON
            + " INTEGER NOT NULL DEFAULT 0, "
            + RecentLocs.NAME
            + " TEXT, "
            + RecentLocs.LAST_USED
            + " DATETIME"
            +")";

    public DbOpenHelper(Context context) {
        super(context, "prowadz_patryk.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECENT_LOCS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // do nothing
    }
}
