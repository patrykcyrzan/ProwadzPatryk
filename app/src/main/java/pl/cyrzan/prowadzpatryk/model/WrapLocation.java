package pl.cyrzan.prowadzpatryk.model;

import android.support.annotation.Nullable;

import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.io.Serializable;

import static com.jakewharton.rxbinding.internal.Preconditions.checkArgument;
import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.NORMAL;
import static pl.cyrzan.prowadzpatryk.model.enums.LocationType.ANY;

/**
 * Created by Patryk on 19.02.2017.
 */
public class WrapLocation implements Serializable {

    public enum WrapType { NORMAL, HOME, GPS, MAP }

    private Location loc;
    private WrapType type;

    public WrapLocation(Location loc) {
        this(loc, NORMAL);
    }

    public WrapLocation(WrapType type) {
        this(new Location(ANY, null), type);
        checkArgument(type != NORMAL, "Type can't be normal");
    }

    public WrapLocation(Location loc, WrapType type) {
        this.loc = loc;
        this.type = type;
    }

    public WrapLocation(LatLng latLng) {
        this.loc = Location.coord(latLng.getLatitude(), latLng.getLongitude());
        this.type = NORMAL;
    }

    public Location getLocation() {
        return loc;
    }

    @Nullable
    public String getId() {
        if (loc == null) return null;
        return loc.id;
    }

    public WrapType getType() {
        return type;
    }

    @Nullable
    public String getName() {
        if (loc == null) return null;
        return MainUtil.getLocationName(loc);
    }

    public boolean hasId() {
        return loc != null && loc.hasId();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(o instanceof WrapLocation) {
            WrapLocation wLoc = (WrapLocation) o;
            if(getLocation() == null) {
                return wLoc.getLocation() == null;
            }
            return getLocation().equals(wLoc.getLocation());
        }
        return false;
    }

    @Override
    public String toString() {
        if(loc == null) return "";
        return MainUtil.getFullLocName(getLocation());
    }

}
