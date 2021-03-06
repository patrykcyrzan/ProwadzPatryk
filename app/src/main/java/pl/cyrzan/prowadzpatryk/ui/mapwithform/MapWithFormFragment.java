package pl.cyrzan.prowadzpatryk.ui.mapwithform;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.squareup.sqlbrite.BriteDatabase;
import com.vlad1m1r.lemniscate.BernoullisProgressView;

import org.opentripplanner.routing.core.TraverseModeSet;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Polyline;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.model.Itinerary;
import pl.cyrzan.prowadzpatryk.model.Leg;
import pl.cyrzan.prowadzpatryk.model.LegShape;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.Product;
import pl.cyrzan.prowadzpatryk.model.Response;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.model.enums.LocationType;
import pl.cyrzan.prowadzpatryk.service.api.model.TripRequest;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.service.preferences.model.UserPreferences;
import pl.cyrzan.prowadzpatryk.ui.base.BaseFragment;
import pl.cyrzan.prowadzpatryk.ui.common.dialogs.ProductsPreferencesDialog;
import pl.cyrzan.prowadzpatryk.ui.common.views.SlideUp;
import pl.cyrzan.prowadzpatryk.ui.common.views.dateandtimeview.TimeAndDateView;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.LocationInput;
import pl.cyrzan.prowadzpatryk.ui.common.views.inputWithGps.LocationGpsInput;
import pl.cyrzan.prowadzpatryk.ui.main.MainActivity;
import pl.cyrzan.prowadzpatryk.ui.main.MainAdapter;
import pl.cyrzan.prowadzpatryk.ui.trips.TripsFragment;
import pl.cyrzan.prowadzpatryk.util.MainUtil;
import pl.cyrzan.prowadzpatryk.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapWithFormFragment extends BaseFragment implements MapEventsReceiver, MapWithFormContract.View,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private static final String TAG = "MapWithFormFragment";

    private MapView map;
    private SlideUp slideUp;
    private boolean showingMore = false;
    private boolean now = true, today = true;
    private ProductsAdapter productsAdapter;
    private Animation fabAnimForward;
    private Animation fabAnimBackward;

    @Inject
    BriteDatabase db;

    @Inject
    MapWithFormPresenter mapWithFormPresenter;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.slideView)
    View sliderView;
    @BindView(R.id.dim)
    View dim;
    @BindView(R.id.locationInput)
    LocationInput locationInput;
    @BindView(R.id.locationGpsInput)
    LocationGpsInput locationGpsInput;
    @BindView(R.id.time_and_date_line_layout)
    TimeAndDateView timeAndDateLayout;
    @BindView(R.id.show_more_button)
    Button showMoreButton;
    @BindView(R.id.more_layout)
    LinearLayout moreLayout;
    @BindView(R.id.products_list)
    RecyclerView productsRecyclerView;
    @BindView(R.id.progressBar)
    BernoullisProgressView progressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_with_form, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map = (MapView) getView().findViewById(R.id.map);
        if(map != null) {
            map.setMultiTouchControls(true);
            map.setTilesScaledToDpi(true);
        }

        fabAnimForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotation_forward);
        fabAnimBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotation_backward);

        setupMap();
        showLess(false);
        initSlideUp();
        initLocationInput();
        mapWithFormPresenter.loadPreferences();
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
        mapWithFormPresenter.attachView(this);
    }

    private void initLocationInput(){
        Log.i(TAG, mapWithFormPresenter.toString());
        locationInput.setOnLocationInputActionListener(mapWithFormPresenter);
        locationGpsInput.setOnLocationInputActionListener(mapWithFormPresenter);

        mapWithFormPresenter.load5RecentLocs();
    }

    private void initSlideUp(){
        slideUp = new SlideUp.Builder(sliderView)
                .withListeners(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {
                        dim.setAlpha((float)(0.5 - (percent / 100)*0.5));
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE){
                            //fab.startAnimation(fabAnim);
                        } else if (visibility == View.VISIBLE){
                            fab.startAnimation(fabAnimForward);
                        }
                    }
                })
                .withStartGravity(Gravity.TOP)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
    }

    private void initProductsList(List<Product> productList){
        productsAdapter = new ProductsAdapter(getActivity(), productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        productsRecyclerView.setLayoutManager(linearLayoutManager);
        productsRecyclerView.setAdapter(productsAdapter);
    }

    private void setupMap() {

        map.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        map.getOverlays().add(0, mapEventsOverlay);

        IMapController mapController = map.getController();
        GeoPoint startPoint = new GeoPoint(54.360, 18.639);
        mapController.setZoom(11);
        mapController.setCenter(startPoint);
    }

    @OnClick(R.id.fab)
    public void onFabClick(){
        Log.i(TAG, "onClickFab");
        if(slideUp.isVisible()){
            slideUp.hide();
            fab.startAnimation(fabAnimBackward);
        } else {
            slideUp.show();
        }
    }

    private void hideSlideUp(){
        if(slideUp.isVisible()) {
            slideUp.hide();
            fab.startAnimation(fabAnimBackward);
        }
    }

    @OnClick(R.id.show_more_button)
    public void onMoreClick(){
        if(moreLayout.getVisibility() == GONE){
            showMore(true);
        } else {
            showLess(true);
        }
    }

    @OnClick({R.id.products_card, R.id.products_layout})
    public void onProductsCardClick(){
        mapWithFormPresenter.loadProductsPreferencesDialog();
    }

    @OnClick(R.id.search_button)
    public void onSearchClick(){
        if(locationGpsInput == null && locationInput == null){
            return;
        }

        TripRequest request = new TripRequest();

        if(checkLocation(locationInput)){
            request.setTo(locationInput.getLocation().getCoordAddress());
        } else {
            ViewUtil.makeToast(getContext(), getResources().getString(R.string.error_invalid_to));
            return;
        }

        if(checkLocation(locationGpsInput)){
            request.setFrom(locationGpsInput.getLocation().getCoordAddress());
        } else {
            ViewUtil.makeToast(getContext(), getResources().getString(R.string.error_invalid_from));
            return;
        }

        request.setDateTime(timeAndDateLayout.getDateTime());

        mapWithFormPresenter.loadTrips(request);
        progressView.setVisibility(View.VISIBLE);
    }

    private Boolean checkLocation(LocationInput locationInput){
        Location location = locationInput.getLocation();

        if(location == null){
            return false;
        } else {
            return true;
        }
    }

    private void showMore(boolean animate) {
        showingMore = true;

        if(animate && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

            moreLayout.setAlpha(0f);
            moreLayout.animate().setDuration(500).alpha(1f).withEndAction(new Runnable() {
                @Override
                public void run() {
                    moreLayout.setVisibility(VISIBLE);
                    showMoreButton.setText(R.string.less);
                }
            });
        } else {
            moreLayout.setVisibility(VISIBLE);
            showMoreButton.setText(R.string.less);
        }
    }

    private void showLess(boolean animate) {
        showingMore = false;

        moreLayout.setVisibility(GONE);
        showMoreButton.setText(R.string.more);
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {
        return false;
    }

    @Override
    public void showError(int errorReport) {
        ViewUtil.makeToast(getContext(), getText(errorReport).toString());
        progressView.setVisibility(View.GONE);
    }

    @Override
    public void complete() {

    }

    @Override
    public MapWithFormContract.LocationInputView getLocationInputView() {
        return locationInput;
    }

    @Override
    public MapWithFormContract.LocationGpsInputView getLocationGpsInputView() {
        return locationGpsInput;
    }

    @Override
    public void showTrips(Response response) {
        progressView.setVisibility(View.GONE);
        ViewPager viewPager = ((MainActivity)getActivity()).getViewPager();
        MainAdapter adapter = ((MainActivity)getActivity()).getAdapter();
        TripsFragment fragment = (TripsFragment) adapter.getFragment(viewPager, 1);
        fragment.setResponse(response, locationGpsInput.getLocation(), locationInput.getLocation(), timeAndDateLayout.getDateTime());
        viewPager.setCurrentItem(1);
        hideSlideUp();
        Log.i(TAG, "showTrips");
    }

    @Override
    public void initAdapterWithRecentLocs(List<RecentLocs> recentLocs) {
        Log.i(TAG, "initAdapterWithRecentLocs");

        if(locationInput.getAdapter() != null){
            List<WrapLocation> list = new ArrayList<>();
            for (RecentLocs loc:recentLocs){
                Location location = new Location(LocationType.ANY, null, loc.name(), loc.lat(), loc.lon());
                list.add(new WrapLocation(location, WrapLocation.WrapType.RECENT));
            }
            locationInput.getAdapter().setRecentLocations(list);
        }

        if(locationGpsInput.getAdapter() != null){
            List<WrapLocation> list = new ArrayList<>();
            for (RecentLocs loc:recentLocs){
                Location location = new Location(LocationType.ANY, null, loc.name(), loc.lat(), loc.lon());
                list.add(new WrapLocation(location, WrapLocation.WrapType.RECENT));
            }
            locationGpsInput.getAdapter().setRecentLocations(list);
        }
    }

    @Override
    public void showUserPreferences(UserPreferences userPreferences) {
        initProductsList(userPreferences.getProductList());
    }

    @Override
    public void showProductsPreferencesDialog(UserPreferences userPreferences) {
        ProductsPreferencesDialog dialog = ProductsPreferencesDialog.newInstance(userPreferences);
        dialog.setListener(newUserPreferences -> {
            mapWithFormPresenter.savePreferences(newUserPreferences);
            productsAdapter.setItems(newUserPreferences.getProductList());

        });
        FragmentManager manager = getFragmentManager();
        dialog.show(manager, "preferencesDialog");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

    }

    public void showTrip(Itinerary itinerary){
        clearTrip();

        List<GeoPoint> allGeoPoints = new ArrayList<>();

        for(Leg leg : itinerary.legs){
            LegShape legShape = new LegShape(leg.getLegGeometry());

            Polyline polyline = new Polyline();
            if(leg.getMode().isTransit()){
                    polyline.setColor(MainUtil.getColorForProduct(getContext(), leg.getMode()));
            } else if (leg.getMode().isWalk()) {
                polyline.setColor(Color.BLACK);
            }
            polyline.setWidth(6.0f);
            List<GeoPoint> geoPoints = new ArrayList<>(legShape.getLength());
            if(legShape.getLength() > 0){
                for(Location location:legShape.getPoints()){
                    Log.i(TAG,"locations: "+location.toString());
                    geoPoints.add(new GeoPoint(location.getLatAsDouble(), location.getLonAsDouble()));
                }
            }
            polyline.setPoints(geoPoints);
            map.getOverlays().add(polyline);
            map.invalidate();
            allGeoPoints.addAll(geoPoints);
        }

        if(allGeoPoints.size() != 0){
            setViewSpan(allGeoPoints);
        }
    }

    private void setViewSpan(List<GeoPoint> points) {
        if(points.size() == 0) return;

        double maxLat = -180;
        double minLat = 180;
        double maxLon = -180;
        double minLon = 180;

        for(GeoPoint point : points) {
            maxLat = Math.max(point.getLatitude(), maxLat);
            minLat = Math.min(point.getLatitude(), minLat);
            maxLon = Math.max(point.getLongitude(), maxLon);
            minLon = Math.min(point.getLongitude(), minLon);
        }

        double center_lat = (maxLat + minLat) / 2;
        double center_lon = (maxLon + minLon) / 2;
        final GeoPoint center = new GeoPoint(center_lat, center_lon);

        IMapController mapController = map.getController();
        mapController.setCenter(center);
        mapController.setZoom(18);
        mapController.zoomToSpan(maxLat - minLat, maxLon - minLon);
    }

    private void clearTrip(){
        map.getOverlays().clear();
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this);
        map.getOverlays().add(0, mapEventsOverlay);
        map.invalidate();
    }
}
