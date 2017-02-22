package pl.cyrzan.prowadzpatryk.ui.main;

import android.util.Log;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.repository.RepositoryService;
import pl.cyrzan.prowadzpatryk.ui.base.RxPresenter;

import java.net.ConnectException;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Patryk on 10.02.2017.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {

    private static final String TAG = "MainPresenter";

    @Inject
    RepositoryService repositoryService;

    @Inject
    public MainPresenter() {
    }

    private void onSuggestLocationsAvailable(List<SuggestLocationResponse> suggestLocations){

    }


    @Override
    public void onTextChanged(String autocompleteText){
        MainContract.View v = view;


        repositoryService.getSuggestLocations(autocompleteText)
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
                            }
                        });
    }

}
