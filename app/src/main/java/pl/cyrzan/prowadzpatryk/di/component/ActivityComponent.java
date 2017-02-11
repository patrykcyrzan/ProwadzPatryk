package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.scope.PerActivity;
import pl.cyrzan.prowadzpatryk.view.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.view.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Patryk on 08.02.2017.
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);


}
