package pl.cyrzan.prowadzpatryk.view.base;

/**
 * Created by Patryk on 09.02.2017.
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */

public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
