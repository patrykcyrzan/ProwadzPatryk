package pl.cyrzan.prowadzpatryk.service.preferences;

import com.google.gson.Gson;

import android.content.SharedPreferences;

import pl.cyrzan.prowadzpatryk.service.api.ApiService;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.service.user.UserService;

import javax.inject.Inject;

import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;

/**
 * Created by Patryk on 20.02.2017.
 */

public class PreferencesServiceImpl implements PreferencesService {

    private SharedPreferences mSharedPreferences;
    private Gson mGson;

    public PreferencesServiceImpl(SharedPreferences sharedPreferences, Gson gson) {
        mSharedPreferences = sharedPreferences;
        mGson = gson;
    }

    @Override
    public boolean shouldIntroductionBeShown() {
        boolean wasIntroductionShown = mSharedPreferences.getBoolean(KEY_WAS_INTRODUCTION_SHOWN, false);

        if (wasIntroductionShown) {
            return false;
        }

        mSharedPreferences.edit().putBoolean(KEY_WAS_INTRODUCTION_SHOWN, true).apply();
        return true;
    }
}
