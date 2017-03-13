package pl.cyrzan.prowadzpatryk.ui.trips;

import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import javax.inject.Inject;

/**
 * Created by Patryk on 12.03.2017.
 */

public class TripsPresenter extends RxPresenter<TripsContract.View> implements TripsContract.Presenter<TripsContract.View> {

    private static final String TAG = "TripsPresenter";

    @Inject
    RepositoryService repositoryService;

    @Inject
    public TripsPresenter() {
    }
}
