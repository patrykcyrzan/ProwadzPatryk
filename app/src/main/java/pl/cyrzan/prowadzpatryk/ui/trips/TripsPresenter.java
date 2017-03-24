package pl.cyrzan.prowadzpatryk.ui.trips;

import android.util.Log;

import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Patryk on 12.03.2017.
 */

public class TripsPresenter extends RxPresenter<TripsContract.View> implements TripsContract.Presenter<TripsContract.View> {

    private static final String TAG = "TripsPresenter";
    private Subscription recentLocsTaskSub;

    @Inject
    RepositoryService repositoryService;

    @Inject
    public TripsPresenter() {
    }

    @Override
    public void saveRecentLoc(WrapLocation location) {
        repositoryService.saveNewRecentLoc(location);
    }
}
