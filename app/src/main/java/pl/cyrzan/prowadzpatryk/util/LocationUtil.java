package pl.cyrzan.prowadzpatryk.util;

import pl.cyrzan.prowadzpatryk.model.Location;

/**
 * Created by Patryk on 27.03.2017.
 */

public class LocationUtil {

    public static final Location makeLocation(double lat, double lon) {
        return Location.coord(lat, lon);
    }
}
