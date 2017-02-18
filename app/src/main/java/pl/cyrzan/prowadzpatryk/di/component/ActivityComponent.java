package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Patryk on 08.02.2017.
 */

@Subcomponent(
        modules = {
                ActivityModule.class,
        }
)
public interface ActivityComponent {

    FragmentComponent plus(FragmentModule fragmentModule);

    void inject(MainActivity mainActivity);


}
