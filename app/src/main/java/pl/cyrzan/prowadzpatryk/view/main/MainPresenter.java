package pl.cyrzan.prowadzpatryk.view.main;

import pl.cyrzan.prowadzpatryk.di.scope.ConfigPersistent;
import pl.cyrzan.prowadzpatryk.view.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 10.02.2017.
 */

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

}
