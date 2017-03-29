package pl.cyrzan.prowadzpatryk.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;

/**
 * Created by Patryk on 15.02.2017.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbOpenHelper";

    private static final int VERSION = 8;

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
            + " INTEGER NOT NULL DEFAULT 0"
            +")";

    private static final String UPGRADE_TABLE_RECENT_LOCS_VER_3 = ""
            + "DROP TABLE "+RecentLocs.TABLE+"; "
            + CREATE_RECENT_LOCS;

    public DbOpenHelper(Context context) {
        super(context, "prowadz_patryk.db", null /* factory */, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_RECENT_LOCS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "OldVersion: "+oldVersion+" newVersion: "+newVersion);
        sqLiteDatabase.execSQL(CREATE_RECENT_LOCS);
    }
}
