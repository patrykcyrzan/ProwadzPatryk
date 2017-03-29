package pl.cyrzan.prowadzpatryk.di.component;

import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.ui.about.AboutActivity;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.ui.github.GithubActivity;
import pl.cyrzan.prowadzpatryk.ui.main.MainActivity;
import pl.cyrzan.prowadzpatryk.ui.splash.SplashActivity;

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

    void inject(AboutActivity aboutActivity);

    void inject(GithubActivity githubActivity);

    void inject(SplashActivity splashActivity);

}
