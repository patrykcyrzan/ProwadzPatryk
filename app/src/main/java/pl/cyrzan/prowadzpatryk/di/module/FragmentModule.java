package pl.cyrzan.prowadzpatryk.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import pl.cyrzan.prowadzpatryk.di.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Patryk on 08.02.2017.
 */

@Module
public class FragmentModule {
    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    Fragment providesFragment() {
        return fragment;
    }

    @Provides
    Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return fragment.getActivity();
    }

}
