package pl.cyrzan.prowadzpatryk.ui.main;

import pl.cyrzan.prowadzpatryk.ui.base.MvpView;

/**
 * Created by Patryk on 10.02.2017.
 */

public interface MainMvpView extends MvpView {
    void showProgress(boolean show);

    void showError(Throwable error);
}
