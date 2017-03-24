package pl.cyrzan.prowadzpatryk.ui.trips;

import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;

/**
 * Created by Patryk on 12.03.2017.
 */

public interface TripsContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void saveRecentLoc(WrapLocation location);
    }
}
