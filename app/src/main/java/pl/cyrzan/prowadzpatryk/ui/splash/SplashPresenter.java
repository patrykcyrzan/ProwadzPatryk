package pl.cyrzan.prowadzpatryk.ui.splash;

import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 29.03.2017.
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter<SplashContract.View> {

    @Inject
    public SplashPresenter() {
    }

    @Override
    public void loadSplash() {
        view.loadMainScreen();
    }
}
