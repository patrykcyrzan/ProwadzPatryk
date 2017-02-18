package pl.cyrzan.prowadzpatryk.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.sqlbrite.BriteDatabase;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

import pl.cyrzan.prowadzpatryk.ProwadzPatrykApplication;
import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.di.module.ActivityModule;
import pl.cyrzan.prowadzpatryk.di.module.FragmentModule;
import pl.cyrzan.prowadzpatryk.ui.base.BaseFragment;
import pl.cyrzan.prowadzpatryk.ui.common.views.SlideUp;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapWithFormFragment extends BaseFragment implements MapEventsReceiver {

    private static final String TAG = "MapWithFormFragment";

    private MapView map;
    private SlideUp slideUp;

    @Inject
    BriteDatabase db;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.slideView)
    View sliderView;
    @BindView(R.id.dim)
    View dim;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_with_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        ProwadzPatrykApplication.get(activity).getComponent()
                .plus(new ActivityModule(activity))
                .plus(new FragmentModule(this))
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        map = (MapView) getView().findViewById(R.id.map);
        if(map != null) {
            map.setMultiTouchControls(true);
            map.setTilesScaledToDpi(true);
        }

        setupMap();

        initSlideUp();

        db.insert(RecentLocs.TABLE, new RecentLocs.Builder().lat(52.12355).lon(45.468786).name("asdas").lastUsed("2016-02-02 15:02:15").build());
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
                            fab.show();
                        }
                    }
                })
                .withStartGravity(Gravity.BOTTOM)
                .withLoggingEnabled(true)
                .withGesturesEnabled(true)
                .withStartState(SlideUp.State.HIDDEN)
                .build();
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
    public void onClickFab(){
        Log.i(TAG, "onClickFab");
        slideUp.show();
        fab.hide();
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint geoPoint) {
        return false;
    }
}
