package pl.cyrzan.prowadzpatryk.di.component;

import android.content.Context;

import pl.cyrzan.prowadzpatryk.di.ApplicationContext;
import pl.cyrzan.prowadzpatryk.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Patryk on 08.02.2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
}
