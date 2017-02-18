package pl.cyrzan.prowadzpatryk.ui.base;

/**
 * Created by Patryk on 12.02.2017.
 */

public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError();

        void complete();

    }
}
