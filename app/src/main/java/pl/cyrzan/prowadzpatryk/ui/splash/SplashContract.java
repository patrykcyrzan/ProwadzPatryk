package pl.cyrzan.prowadzpatryk.ui.splash;

import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;

/**
 * Created by Patryk on 29.03.2017.
 */

public interface SplashContract {

    interface View extends BaseContract.BaseView {
        void loadMainScreen();
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>{
        void loadSplash();
    }
}
