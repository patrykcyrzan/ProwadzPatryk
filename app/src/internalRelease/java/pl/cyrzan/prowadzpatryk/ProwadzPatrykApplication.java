package pl.cyrzan.prowadzpatryk;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.sqlbrite.BriteDatabase;

import pl.cyrzan.prowadzpatryk.di.component.ApplicationComponent;
import pl.cyrzan.prowadzpatryk.di.component.DaggerApplicationComponent;
import pl.cyrzan.prowadzpatryk.di.module.ApplicationModule;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Patryk on 07.02.2017.
 */

public class ProwadzPatrykApplication extends Application {

    @Inject
    BriteDatabase db;

    private ApplicationComponent applicationComponent;

    public static ProwadzPatrykApplication get(Context context) {
        return (ProwadzPatrykApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent== null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }
}
