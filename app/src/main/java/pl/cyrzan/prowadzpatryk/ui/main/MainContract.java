package pl.cyrzan.prowadzpatryk.ui.main;

import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.ui.base.BaseContract;
import pl.cyrzan.prowadzpatryk.ui.common.models.SuggestLocationsResult;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.OnLocationInputActionListener;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.OnLocationInputAdapterActionListener;

import java.util.List;

/**
 * Created by Patryk on 12.02.2017.
 */

public interface MainContract {

    interface View extends BaseContract.BaseView {


    }

    interface Presenter<T> extends BaseContract.BasePresenter<T>, OnLocationInputActionListener, OnLocationInputAdapterActionListener {
    }

    interface Adapter {

        void onSuggestLocationsResult(SuggestLocationsResult result);
    }

    interface LocationInput {

        void onSuggestLocationsResult(List<SuggestLocationResponse> suggestLocations);
    }
}
