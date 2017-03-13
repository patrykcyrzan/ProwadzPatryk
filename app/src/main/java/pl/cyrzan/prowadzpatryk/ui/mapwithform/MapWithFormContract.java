package pl.cyrzan.prowadzpatryk.ui.mapwithform;

import org.opentripplanner.api.ws.Request;
import org.opentripplanner.v092snapshot.api.ws.Response;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.api.model.TripRequest;
import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.OnLocationActionListener;
import pl.cyrzan.prowadzpatryk.ui.main.MainContract;

import java.util.List;

/**
 * Created by Patryk on 07.03.2017.
 */

public interface MapWithFormContract {

    interface View extends BaseContract.BaseView {
        LocationInputView getLocationInputView();
        LocationGpsInputView getLocationGpsInputView();

        void showTrips(Response response);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>, OnLocationActionListener {
        void loadTrips(TripRequest request);
    }

    interface LocationInputView {
        void onSuggestLocationsResult(List<SuggestLocationResponse> suggestLocations);
        void onSuggestLocationsResultFail();
    }

    interface LocationGpsInputView {
        void onSuggestLocationsResult(List<SuggestLocationResponse> suggestLocations);
        void onSuggestLocationsResultFail();
    }
}
