package pl.cyrzan.prowadzpatryk.service.db;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Patryk on 13.02.2017.
 */

//@Singleton
@Module
public class DbModule {
    @Provides @Singleton SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @Provides @Singleton SqlBrite provideSqlBrite() {
        return SqlBrite.create(message -> Timber.tag("Database").v(message));
    }

    @Provides
    @Singleton
    public DbService provideDbService(BriteDatabase database){
        return new DbServiceImpl(database);
    }

    @Provides
    @Singleton
    BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(true);
        return db;
    }
}
