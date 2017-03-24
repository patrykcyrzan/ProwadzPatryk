package pl.cyrzan.prowadzpatryk.model;

import android.text.TextUtils;

import pl.cyrzan.prowadzpatryk.model.enums.LocationType;
import pl.cyrzan.prowadzpatryk.util.Constants;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

import javax.annotation.Nullable;

import static com.jakewharton.rxbinding.internal.Preconditions.checkArgument;
import static com.jakewharton.rxbinding.internal.Preconditions.checkNotNull;

/**
 * Created by Patryk on 19.02.2017.
 */

public final class Location implements Serializable {
    private static final long serialVersionUID = -2124775933106309127L;

    public final LocationType type;
    public final @Nullable String id;
    public final double lat, lon;
    public final @Nullable String place;
    public final @Nullable String name;
    public final @Nullable Set<Product> products;

    public Location(final LocationType type, final String id, final double lat, final double lon, final String place,
                    final String name, final Set<Product> products) {
        this.type = checkNotNull(type, "type missing");
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.place = place;
        this.name = name;
        this.products = products;

        checkArgument(id == null || id.length() > 0, "ID cannot be the empty string");
        checkArgument(place == null || name != null, "place without name cannot exist");
        if (type == LocationType.ANY) {
        } else if (type == LocationType.COORD) {
            checkArgument(hasLocation(), "coordinates missing");
            checkArgument(place == null && name == null, "coordinates cannot have place or name");
        }
    }

    public Location(final LocationType type, final String id, final double lat, final double lon, final String place,
                    final String name) {
        this(type, id, lat, lon, place, name, null);
    }

    public Location(final LocationType type, final String id, final Point coord, final String place, final String name,
                    final Set<Product> products) {
        this(type, id, coord != null ? coord.lat : 0, coord != null ? coord.lon : 0, place, name, products);
    }

    public Location(final LocationType type, final String id, final Point coord, final String place,
                    final String name) {
        this(type, id, coord != null ? coord.lat : 0, coord != null ? coord.lon : 0, place, name);
    }

    public Location(final LocationType type, final String id, final String place, final String name) {
        this(type, id, 0, 0, place, name);
    }

    public Location(final LocationType type, final String id, final double lat, final double lon) {
        this(type, id, lat, lon, null, null);
    }

    public Location(final LocationType type, final String id, final Point coord) {
        this(type, id, coord != null ? coord.lat : 0, coord != null ? coord.lon : 0);
    }

    public Location(final LocationType type, final String id, final String name, final double lat, final double lon){
        this(type, id, lat, lon, null, name);
    }

    public Location(final LocationType type, final String id) {
        this(type, id, null, null);
    }

    public static Location coord(final double lat, final double lon) {
        return new Location(LocationType.COORD, null, lat, lon);
    }

    public static Location coord(final Point coord) {
        return new Location(LocationType.COORD, null, coord.lat, coord.lon);
    }

    public final boolean hasId() {
        return !TextUtils.isEmpty(id);
    }

    public final boolean hasLocation() {
        return lat != 0 || lon != 0;
    }

    public double getLatAsDouble() {
        return lat;
    }

    public double getLonAsDouble() {
        return lon;
    }

    public final boolean hasName() {
        return name != null;
    }

    public String getCoordAddress(){
        if(hasLocation()){
            return String.format(Constants.OTP_LOCALE, "%g,%g", lat, lon);
        } else {
            return null;
        }
    }

    public final boolean isIdentified() {
        if (type == LocationType.STATION)
            return hasId();

        if (type == LocationType.POI)
            return true;

        if (type == LocationType.ADDRESS || type == LocationType.COORD)
            return hasLocation();

        return false;
    }

    private static final String[] NON_UNIQUE_NAMES = { "Hauptbahnhof", "Hbf", "Bahnhof", "Bf", "Busbahnhof", "ZOB",
            "Schiffstation", "Schiffst.", "Zentrum", "Markt", "Dorf", "Kirche", "Nord", "Ost", "Süd", "West" };

    static {
        Arrays.sort(NON_UNIQUE_NAMES);
    }

    public final String uniqueShortName() {
        if (place != null && name != null && Arrays.binarySearch(NON_UNIQUE_NAMES, name) >= 0)
            return place + ", " + name;
        else if (name != null)
            return name;
        else if (hasId())
            return id;
        else
            return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.lat, lat) != 0) return false;
        if (Double.compare(location.lon, lon) != 0) return false;
        if (type != location.type) return false;
        if (!id.equals(location.id)) return false;
        if (place != null ? !place.equals(location.place) : location.place != null) return false;
        return name != null ? name.equals(location.name) : location.name == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type != null ? type.hashCode() : 0;
        result = 31 * result + id.hashCode();
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "place='" + place + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
    }
}
