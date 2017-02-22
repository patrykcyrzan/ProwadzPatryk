package pl.cyrzan.prowadzpatryk.service.preferences;

import android.support.annotation.Nullable;

/**
 * Created by Patryk on 20.02.2017.
 */
public interface PreferencesService {

    String KEY_WAS_INTRODUCTION_SHOWN = "PreferencesService.KEY_WAS_INTRODUCTION_SHOWN";

    boolean shouldIntroductionBeShown();
}
