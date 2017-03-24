package pl.cyrzan.prowadzpatryk.service.preferences;

import com.google.gson.Gson;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import pl.cyrzan.prowadzpatryk.service.api.ApiService;
import pl.cyrzan.prowadzpatryk.service.preferences.model.UserPreferences;
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
    public void setUserPreferences(UserPreferences userPreferences) {
        String json = mGson.toJson(userPreferences);
        mSharedPreferences.edit().putString(KEY_USER_PREFERENCES, json).apply();
    }

    @Nullable
    @Override
    public UserPreferences getUserPreferences() {
        String json = mSharedPreferences.getString(KEY_USER_PREFERENCES, null);
        UserPreferences userPreferences = mGson.fromJson(json, UserPreferences.class);
        return userPreferences != null ? userPreferences : new UserPreferences();
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
