package pl.cyrzan.prowadzpatryk.ui.main;

import android.util.Log;

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
}
