package pl.cyrzan.prowadzpatryk.view.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.view.base.BaseFragment;
import pl.cyrzan.prowadzpatryk.view.common.views.SlideUp;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapWithFormFragment extends BaseFragment implements MapEventsReceiver {

    private static final String TAG = "MapWithFormFragment";

    private MapView map;
    private SlideUp slideUp;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.slideView)
    View sliderView;
    @BindView(R.id.dim)
    View dim;

    @Override
    public int getLayout() {
        return R.layout.fragment_map_with_form;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(getLayout(), container, false);

        *//*map = (MapView) v.findViewById(R.id.map);
        if(map != null) {
            map.setMultiTouchControls(true);
            map.setTilesScaledToDpi(true);
        }*//*

        initSlideUp();

        return v;
    }*/


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*map = (MapView) getView().findViewById(R.id.map);
        if(map != null) {
            map.setMultiTouchControls(true);
            map.setTilesScaledToDpi(true);
        }

        setupMap();*/

        initSlideUp();
    }

    private void initSlideUp(){
        slideUp = new SlideUp.Builder(sliderView)
                .withListeners(new SlideUp.Listener() {
                    @Override
                    public void onSlide(float percent) {
                        dim.setAlpha((float)(0.5 - (percent / 100)*0.5));
                        Log.i(TAG, "aplha: "+(0.5 - (percent / 100)*0.5));
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
