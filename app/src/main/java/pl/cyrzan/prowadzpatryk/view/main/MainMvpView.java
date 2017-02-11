package pl.cyrzan.prowadzpatryk.view.main;

import pl.cyrzan.prowadzpatryk.view.base.MvpView;

/**
 * Created by Patryk on 10.02.2017.
 */

public interface MainMvpView extends MvpView {
    void showProgress(boolean show);

    void showError(Throwable error);
}
