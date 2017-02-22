package pl.cyrzan.prowadzpatryk.service.preferences;

import com.google.gson.Gson;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 20.02.2017.
 */

@Module
public class PreferencesModule {

    @Provides
    @Singleton
    public PreferencesService providePreferencesService(SharedPreferences sharedPreferences, Gson gson) {
        return new PreferencesServiceImpl(sharedPreferences, gson);
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
