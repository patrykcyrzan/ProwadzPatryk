package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.service.api.ApiModule;
import pl.cyrzan.prowadzpatryk.service.db.DbModule;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.ApplicationModule;
import pl.cyrzan.prowadzpatryk.eventbus.EventBusModule;
import pl.cyrzan.prowadzpatryk.service.preferences.PreferencesModule;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryModule;
import pl.cyrzan.prowadzpatryk.service.user.UserModule;
import pl.cyrzan.prowadzpatryk.ui.common.views.inputWithGps.LocationGpsInput;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Patryk on 08.02.2017.
 */

@Singleton
@Component(
        modules = {
                ApplicationModule.class,
                RepositoryModule.class,
                PreferencesModule.class,
                UserModule.class,
                ApiModule.class,
                DbModule.class,
                EventBusModule.class
        }
)
public interface ApplicationComponent {


    ActivityComponent plus(ActivityModule activityModule);

    void inject(ProwadzPatrykApplication prowadzPatrykApplication);
    void inject(LocationGpsInput locationGpsInput);
}
