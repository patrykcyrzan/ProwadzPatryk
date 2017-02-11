package pl.cyrzan.prowadzpatryk.di.module;

import android.app.Activity;
import android.content.Context;

import pl.cyrzan.prowadzpatryk.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 08.02.2017.
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return activity;
    }
}
