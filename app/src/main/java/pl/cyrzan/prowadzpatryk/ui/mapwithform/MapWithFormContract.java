package pl.cyrzan.prowadzpatryk.ui.mapwithform;


import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.api.model.TripRequest;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.service.preferences.model.UserPreferences;
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
        void initAdapterWithRecentLocs(List<RecentLocs> recentLocs);

        void showUserPreferences(UserPreferences userPreferences);

        void showProductsPreferencesDialog(UserPreferences userPreferences);
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>, OnLocationActionListener {
        void loadTrips(TripRequest request);
        void load5RecentLocs();
        void loadPreferences();
        void savePreferences(UserPreferences userPreferences);
        void loadProductsPreferencesDialog();
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
