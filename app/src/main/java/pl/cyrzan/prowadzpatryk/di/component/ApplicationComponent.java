package pl.cyrzan.prowadzpatryk.di.component;

import android.content.Context;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.db.DbModule;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.ApplicationModule;
import pl.cyrzan.prowadzpatryk.di.module.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Patryk on 08.02.2017.
 */

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                NetworkModule.class,
                DbModule.class
        }
)
public interface ApplicationComponent {


    ActivityComponent plus(ActivityModule activityModule);

    void inject(ProwadzPatrykApplication prowadzPatrykApplication);
}
