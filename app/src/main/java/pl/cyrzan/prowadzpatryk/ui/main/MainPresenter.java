package pl.cyrzan.prowadzpatryk.ui.main;

import pl.cyrzan.prowadzpatryk.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 10.02.2017.
 */

public class MainPresenter extends BasePresenter<MainMvpView> implements MainContract.Presenter<MainContract.View> {

    @Inject
    public MainPresenter() {
    }

    @Override
    public void attachView(MainContract.View view) {

    }
}
