package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.di.scope.PerFragment;
import pl.cyrzan.prowadzpatryk.view.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.view.base.BaseFragment;
import pl.cyrzan.prowadzpatryk.view.main.MapWithFormFragment;
import pl.cyrzan.prowadzpatryk.view.main.TripsFragment;

import dagger.Subcomponent;

/**
 * Created by Patryk on 09.02.2017.
 * This component inject dependencies to all Fragments across the application
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(BaseFragment baseFragment);

    void inject(MapWithFormFragment mapWithFormFragment);

    void inject(TripsFragment tripsFragment);
}