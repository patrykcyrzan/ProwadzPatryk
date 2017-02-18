package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.ui.main.MapWithFormFragment;
import pl.cyrzan.prowadzpatryk.ui.main.TripsFragment;

import dagger.Subcomponent;

/**
 * Created by Patryk on 09.02.2017.
 * This component inject dependencies to all Fragments across the application
 */

@Subcomponent(
        modules = {
                FragmentModule.class,
        }
)
public interface FragmentComponent {

    void inject(MapWithFormFragment mapWithFormFragment);

    void inject(TripsFragment tripsFragment);
}