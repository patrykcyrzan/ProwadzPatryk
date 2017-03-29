package pl.cyrzan.prowadzpatryk.ui.github;

import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 29.03.2017.
 */

public class GithubPresenter extends RxPresenter<GithubContract.View> implements GithubContract.Presenter<GithubContract.View> {

    @Inject
    public GithubPresenter() {
    }
}
