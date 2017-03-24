package pl.cyrzan.prowadzpatryk.service.preferences;

import android.support.annotation.Nullable;

import pl.cyrzan.prowadzpatryk.service.preferences.model.UserPreferences;

/**
 * Created by Patryk on 20.02.2017.
 */
public interface PreferencesService {

    String KEY_USER_PREFERENCES = "PreferencesService.KEY_USER_PREFERENCES";
    String KEY_WAS_INTRODUCTION_SHOWN = "PreferencesService.KEY_WAS_INTRODUCTION_SHOWN";

    void setUserPreferences(UserPreferences userPreferences);
    @Nullable
    UserPreferences getUserPreferences();

    boolean shouldIntroductionBeShown();
}
