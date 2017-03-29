package pl.cyrzan.prowadzpatryk.ui.about;

import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 29.03.2017.
 */

public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter<AboutContract.View> {

    @Inject
    public AboutPresenter() {
    }
}
