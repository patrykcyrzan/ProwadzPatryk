package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.di.scope.ConfigPersistent;

import dagger.Component;

/**
 * Created by Patryk on 09.02.2017.
 */

@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

}
