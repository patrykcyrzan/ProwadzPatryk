package pl.cyrzan.prowadzpatryk.ui.github;

import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;

/**
 * Created by Patryk on 29.03.2017.
 */

public interface GithubContract {

    interface View extends BaseContract.BaseView {
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
    }
}
