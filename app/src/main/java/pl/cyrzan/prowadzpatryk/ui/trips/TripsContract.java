package pl.cyrzan.prowadzpatryk.ui.trips;

import org.joda.time.DateTime;

import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;

/**
 * Created by Patryk on 12.03.2017.
 */

public interface TripsContract {

    interface View extends BaseContract.BaseView {

        void showNoTripsDownloadedMessage();

        void showTrips(Response response, Location from, Location to, DateTime date);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void saveRecentLoc(WrapLocation location);

        void loadTrips(Response response, Location from, Location to, DateTime date);
    }
}
