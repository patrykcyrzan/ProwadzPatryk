package pl.cyrzan.prowadzpatryk.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import org.opentripplanner.routing.core.TraverseModeSet;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.model.enums.LocationType;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;

import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.GPS;
import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.MAP;
import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.NORMAL;
import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.RECENT;

/**
 * Created by Patryk on 13.02.2017.
 */

public final class MainUtil {

    static public Drawable getDrawableForLocation(Context context, WrapLocation w, boolean is_fav) {
        return getDrawableForLocation(context, null, w, is_fav);
    }

    static public Drawable getDrawableForLocation(Context context, Location l) {
        if(l == null) return getTintedDrawable(context, R.drawable.ic_location);

        // Sprawdz czy ta lokalizacja jest w ulubionych
        //List<WrapLocation> fav_list = getFavLocationList(context, FavLocation.FavLocationType.FROM);
        WrapLocation w = new WrapLocation(l);

        return getDrawableForLocation(context, w, false);
    }

    static public Drawable getDrawableForLocation(Context context, @Nullable Location home, WrapLocation w, boolean is_fav) {
        if(w == null || w.getLocation() == null) return null;
        //if (home == null) home = RecentsDB.getHome(context);
        Location l = w.getLocation();

        /*if(w.getType() == HOME || l.equals(home)) {
            return getTintedDrawable(context, R.drawable.ic_action_home_on);
        }*/
        if(w.getType() == GPS) {
            return getTintedDrawable(context, R.drawable.ic_gps);
        }
        else if(w.getType() == MAP) {
            return getTintedDrawable(context, R.drawable.ic_action_location_map);
        }
        else if(w.getType() == RECENT) {
            return getTintedDrawable(context, R.drawable.ic_history_location);
        }
        else if(w.getType() == NORMAL) {
            return getTintedDrawable(context, R.drawable.ic_location_address);
        }
        else if(is_fav) {
            return getTintedDrawable(context, R.drawable.ic_action_star);
        }
        else {
            if(l.type.equals(LocationType.ADDRESS)) {
                return getTintedDrawable(context, R.drawable.ic_location_address);
            } else if(l.type.equals(LocationType.POI)) {
                return getTintedDrawable(context, R.drawable.ic_action_about);
            } else if(l.type.equals(LocationType.STATION)) {
                return getTintedDrawable(context, R.drawable.ic_tab_stations);
            } else if(l.type.equals(LocationType.COORD)) {
                return getTintedDrawable(context, R.drawable.ic_gps);
            } else {
                return null;
            }
        }
    }

    static public String getNameForProduct(Context context, TraverseMode mode){
        String name;

        switch(mode) {
            case RAIL:
                name = context.getString(R.string.rail);
                break;
            case TRAM:
                name = context.getString(R.string.tram);
                break;
            case BUS:
                name = context.getString(R.string.bus);
                break;
            case REGIONAL_TRAIN:
                name = context.getString(R.string.regional_train);
                break;
            case TRANSIT:
                name = context.getString(R.string.transit);
                break;
            case CABLE_CAR:
                name = context.getString(R.string.cable_car);
                break;
            case WALK:
                name = context.getString(R.string.rail);
                break;
            default:
                name = "undefinied";
                break;
        }

        return name;
    }


    static public int getColorForProduct(Context context, TraverseMode mode) {
        int color;

        switch(mode) {
            case RAIL:
                color = ContextCompat.getColor(context, R.color.product_regional_train);
                break;
            case TRAM:
                color = ContextCompat.getColor(context, R.color.product_tram);
                break;
            case BUS:
                color = ContextCompat.getColor(context, R.color.product_bus);
                break;
            case REGIONAL_TRAIN:
                color = ContextCompat.getColor(context, R.color.product_subway);
                break;
            case CABLE_CAR:
                color = ContextCompat.getColor(context, R.color.product_bus);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.product_bus);
                break;
        }

        return color;
    }

    static public int getDrawableForProduct(TraverseMode mode) {
        int image_res = R.drawable.product_bus;

        switch(mode) {
            case RAIL:
                image_res = R.drawable.product_regional_train;
                break;
            case TRAM:
                image_res = R.drawable.product_tram;
                break;
            case BUS:
                image_res = R.drawable.product_bus;
                break;
            case REGIONAL_TRAIN:
                image_res = R.drawable.product_subway;
                break;
            case CABLE_CAR:
                image_res = R.drawable.product_bus;
                break;
            case WALK:
                image_res = R.drawable.ic_walk;
                break;
            default:
                image_res = R.drawable.ic_walk;
                break;
        }

        return image_res;
    }

    static public Drawable getTintedDrawable(Context context, boolean dark, Drawable drawable) {
        /*if(dark) {
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.drawableTintDark), PorterDuff.Mode.SRC_IN);
        }
        else {
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.drawableTintLight), PorterDuff.Mode.SRC_IN);
        }*/
        drawable.setColorFilter(ContextCompat.getColor(context, R.color.drawableTintLight), PorterDuff.Mode.SRC_IN);
        return drawable.mutate();
    }

    static public Drawable getTintedDrawable(Context context, Drawable drawable) {
        /*if(Preferences.darkThemeEnabled(context)) {
            return getTintedDrawable(context, true, drawable);
        }
        else {
            return getTintedDrawable(context, false, drawable);
        }*/
        return getTintedDrawable(context, false, drawable);
    }

    static public Drawable getTintedDrawable(Context context, boolean dark, int res) {
        return getTintedDrawable(context, dark, ContextCompat.getDrawable(context, res));
    }

    static public Drawable getTintedDrawable(Context context, int res) {
        return getTintedDrawable(context, ContextCompat.getDrawable(context, res));
    }

    static public String getLocationName(Location l) {
        if(l == null) {
            return "";
        } else if(l.type.equals(LocationType.COORD)) {
            return getCoordinationName(l);
        } else if(l.uniqueShortName() != null) {
            return l.uniqueShortName();
        } else {
            return "";
        }
    }

    static public String getFullLocName(Location l) {
        if(l.hasName()) {
            return l.place == null ? l.uniqueShortName() : l.name + ", " + l.place;
        } else {
            return getLocationName(l);
        }
    }

    static public String getCoordinationName(Location location) {
        return getCoordinationName(location.getLatAsDouble(), location.getLonAsDouble());
    }

    static public String getCoordinationName(double lat, double lon) {
        String latStr;
        try {
            latStr = String.valueOf(lat).substring(0, 7);
        } catch(StringIndexOutOfBoundsException e) {
            latStr = String.valueOf(lat);
        }

        String lonStr;
        try {
            lonStr = String.valueOf(lon).substring(0, 7);
        } catch(StringIndexOutOfBoundsException e) {
            lonStr = String.valueOf(lon);
        }

        return latStr + "/" + lonStr;
    }
}
