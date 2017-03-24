package pl.cyrzan.prowadzpatryk.ui.common.views.inputWithGps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.ui.base.BaseActivity;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.LocationInput;
import pl.cyrzan.prowadzpatryk.ui.common.views.input.LocationInputAdapter;
import pl.cyrzan.prowadzpatryk.ui.mapwithform.MapWithFormContract;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.List;

import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.GPS;

/**
 * Created by Patryk on 05.03.2017.
 */

public class LocationGpsInput extends LocationInput implements MapWithFormContract.LocationGpsInputView, LocationListener {

    private final static String TAG = "LocationGpsInput";

    private LocationManager locationManager;
    private BaseActivity activity;
    private volatile boolean searching = false;
    private Location gps_location = null;
    protected int caller = 0;

    public LocationGpsInput(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(!isInEditMode()) {
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            activity = ((BaseActivity) getContext());
            activity.getComponent().inject(this);
        }
    }

    @Override
    protected LocationInputAdapter createAdapter(){
        return new LocationInputAdapter(getContext(), this, true, true);
    }

    @Override
    public void sendRequest(String text){
        onLocationActionListener.onTextChangedGps(text);
    }

    @Override
    protected void cancelTask() {
        onLocationActionListener.stopSuggestLocationsTaskGps();
    }

    @Override
    public void onItemClickListener(WrapLocation loc, View view) {
        if(loc.getType() == GPS){
            locationACTV.setText("");

            activateGPS();
        } else {
            super.onItemClickListener(loc, view);
        }
    }

    public void activateGPS() {
        if(searching) return;

        // check permissions
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(getContext(), R.string.permission_denied_gps, Toast.LENGTH_LONG).show();
            } else {
                // No explanation needed, we can request the permission
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, caller);
            }

            return;
        }

        searching = true;

        List<String> providers = locationManager.getProviders(true);

        for(String provider : providers) {
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestSingleUpdate(provider, this, null);

            Log.d(getClass().getSimpleName(), "Register provider for location updates: " + provider);
        }

        // check if there is a non-passive provider available
        if(providers.size() == 0 || (providers.size() == 1 && providers.get(0).equals(LocationManager.PASSIVE_PROVIDER))) {
            locationManager.removeUpdates(this);
            Toast.makeText(getContext(), getContext().getString(R.string.error_no_location_provider), Toast.LENGTH_LONG).show();

            // Set the flag that there is currently no active search. Otherwise the App won't
            // allow new searches even after GPS has been reenabled, because the app "hangs" in
            // a semistate where searching = true but now real search is active.
            searching = false;
            return;
        }

        // clear input
        setLocation(null, MainUtil.getTintedDrawable(getContext(), R.drawable.ic_gps));
        clearButton.setVisibility(View.VISIBLE);

        // clear current GPS location, because we are looking to find a new one
        gps_location = null;

        // show GPS button blinking
        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        status.startAnimation(animation);

        locationACTV.setHint(R.string.searching_position);
        locationACTV.clearFocus();

        //if(gpsListener != null) gpsListener.activateGPS();
    }

    public void deactivateGPS() {
        searching = false;

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }

        // deactivate button
        status.clearAnimation();
        locationACTV.setHint(hint);

        //if(gpsListener != null) gpsListener.deactivateGPS();
    }

    public void onLocationChanged(Location location) {
        setLocation(location, MainUtil.getTintedDrawable(getContext(), R.drawable.ic_gps));
        //if(gpsListener != null) gpsListener.onLocationChanged(location);
        deactivateGPS();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }

        // only execute if we still do not have a location to make super sure this is not run twice
        if(gps_location == null) {
            Log.d(getClass().getSimpleName(), "Found location: " + location.toString());

            // create location based on GPS coordinates
            gps_location = Location.coord(location.getLatitude(), location.getLongitude());
            onLocationChanged(gps_location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
