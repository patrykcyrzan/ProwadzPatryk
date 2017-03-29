package pl.cyrzan.prowadzpatryk.ui.main;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import java.net.ConnectException;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Patryk on 10.02.2017.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {

    private static final String TAG = "MainPresenter";
    private Subscription suggestLocationsTaskSub;

    @Inject
    RepositoryService repositoryService;

    @Inject
    public MainPresenter() {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        view.onCloseDrawer();
        if (item.getItemId() == R.id.about) {
            view.onOpenAbout();
            return true;
        } else if (item.getItemId() == R.id.github) {
            view.onOpenGithub();
            return true;
        }
        return false;
    }
}
