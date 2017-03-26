package pl.cyrzan.prowadzpatryk.ui.trips;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.model.ListTrip;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.model.enums.LocationType;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends BaseFragment implements TripsContract.View {

    private static final String TAG = "TripsFragment";

    @Inject
    TripsPresenter tripsPresenter;

    @BindView(R.id.trips_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.linear_layout_error)
    LinearLayout linearLayoutErrorScreen;
    @BindView(R.id.text_view_error_screen)
    TextView textViewErrorMessage;

    private Response response;
    private TripAdapter tripAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        tripAdapter = new TripAdapter(null, getActivity(), false);
        tripAdapter.setHasStableIds(false);
        recyclerView.setAdapter(tripAdapter);

        tripsPresenter.loadTrips(null, null, null, null);
    }

    @Override
    protected void setupActivityComponent() {
        Activity activity = getActivity();
        ProwadzPatrykApplication.get(activity).getComponent()
                .plus(new ActivityModule(activity))
                .plus(new FragmentModule(this))
                .inject(this);
    }

    @Override
    public void configViews() {
        tripsPresenter.attachView(this);
    }

    public void setResponse(Response response, Location from, Location to, DateTime date){
        tripsPresenter.loadTrips(response, from, to, date);
        /*Location location = new Location(LocationType.ANY, null, "mama", 50.123, 23.123);
        WrapLocation wrapLocation = new WrapLocation(location, WrapLocation.WrapType.RECENT);
        wrapLocation.setLastUsed("asdas");
        tripsPresenter.saveRecentLoc(wrapLocation);*/
    }

    @Override
    public void showError(int errorReport) {
        linearLayoutErrorScreen.setVisibility(View.VISIBLE);
        textViewErrorMessage.setText(getText(errorReport).toString());
    }

    @Override
    public void complete() {

    }

    @Override
    public void showNoTripsDownloadedMessage() {
        recyclerView.setVisibility(View.GONE);
        showError(R.string.error_loading_trips);
    }

    @Override
    public void showTrips(Response response, Location from, Location to, DateTime date) {
        this.response = response;
        linearLayoutErrorScreen.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        tripAdapter.addAll(ListTrip.getList(response.getPlan().getItinerary(), from, to, date));
    }
}
