package pl.cyrzan.prowadzpatryk.model;

import java.io.Serializable;

/**
 * Created by Patryk on 07.01.2017.
 */

public final class Point implements Serializable {
    private static final long serialVersionUID = -256077054671402897L;

    public final double lat, lon;

    public Point(final double lat, final double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /*public static Point fromDouble(final double lat, final double lon) {
        return new Point((int) Math.round(lat * 1E6), (int) Math.round(lon * 1E6));
    }*/

    public double getLatAsDouble() {
        return lat / 1E6;
    }

    public double getLonAsDouble() {
        return lon / 1E6;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Point))
            return false;
        final Point other = (Point) o;
        if (this.lat != other.lat)
            return false;
        if (this.lon != other.lon)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return lat + "/" + lon;
    }
}

