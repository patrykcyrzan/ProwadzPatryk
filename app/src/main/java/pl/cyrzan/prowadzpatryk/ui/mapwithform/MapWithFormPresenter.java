package pl.cyrzan.prowadzpatryk.ui.mapwithform;

import android.util.Log;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;
import pl.cyrzan.prowadzpatryk.ui.main.MainContract;

import java.net.ConnectException;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Patryk on 07.03.2017.
 */

public class MapWithFormPresenter extends RxPresenter<MapWithFormContract.View> implements MapWithFormContract.Presenter<MapWithFormContract.View>  {

    private static final String TAG = "MapWithFormPresenter";
    private Subscription suggestLocationsTaskSub;

    @Inject
    RepositoryService repositoryService;

    @Inject
    public MapWithFormPresenter() {
    }

    private void onSuggestLocationsAvailable(List<SuggestLocationResponse> suggestLocations){
        view.getLocationInputView().onSuggestLocationsResult(suggestLocations);
    }

    private void onSuggestLocationsGpsAvailable(List<SuggestLocationResponse> suggestLocations){
        view.getLocationGpsInputView().onSuggestLocationsResult(suggestLocations);
    }

    @Override
    public void onTextChanged(String autocompleteText) {
        Log.i(TAG, "Wyslij");

        suggestLocationsTaskSub = repositoryService.getSuggestLocations(autocompleteText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuggestLocationsAvailable,
                        throwable -> {
                            try {
                                if(throwable instanceof ConnectException) {
                                    view.showError(R.string.error_unable_to_conect);
                                } else {
                                    view.showError(R.string.error_unable_to_load_suggest_locations);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Problem with error handling code", e);
                            } finally {
                                Log.i(TAG, "Unable to find suggest locations", throwable);
                                view.getLocationInputView().onSuggestLocationsResultFail();
                            }
                        });
        addSubscribe(suggestLocationsTaskSub);
    }

    @Override
    public void stopSuggestLocationsTask() {
        Log.i(TAG, "nie wysylaj");
        view.getLocationInputView().onSuggestLocationsResult(null);
        if(suggestLocationsTaskSub != null) {
            suggestLocationsTaskSub.unsubscribe();
        }
    }

    @Override
    public void onTextChangedGps(String autocompleteText) {
        Log.i(TAG, "Wyslij");

        suggestLocationsTaskSub = repositoryService.getSuggestLocations(autocompleteText)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuggestLocationsGpsAvailable,
                        throwable -> {
                            try {
                                if(throwable instanceof ConnectException) {
                                    view.showError(R.string.error_unable_to_conect);
                                } else {
                                    view.showError(R.string.error_unable_to_load_suggest_locations);
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Problem with error handling code", e);
                            } finally {
                                Log.i(TAG, "Unable to find suggest locations", throwable);
                                view.getLocationInputView().onSuggestLocationsResultFail();
                            }
                        });
        addSubscribe(suggestLocationsTaskSub);
    }

    @Override
    public void stopSuggestLocationsTaskGps() {
        Log.i(TAG, "nie wysylaj");
        view.getLocationGpsInputView().onSuggestLocationsResult(null);
        if(suggestLocationsTaskSub != null) {
            suggestLocationsTaskSub.unsubscribe();
        }
    }
}
