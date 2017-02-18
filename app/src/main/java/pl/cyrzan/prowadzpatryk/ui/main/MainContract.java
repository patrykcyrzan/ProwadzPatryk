package pl.cyrzan.prowadzpatryk.ui.main;

import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;

/**
 * Created by Patryk on 12.02.2017.
 */

public interface MainContract {

    interface View extends BaseContract.BaseView {
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
    }

}
