package pl.cyrzan.prowadzpatryk.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Patryk on 19.02.2017.
 */

public class LatLng implements Parcelable {

    public static final int RADIUS_EARTH_METERS = 6378137;

    public static final Parcelable.Creator<LatLng> CREATOR = new Parcelable.Creator<LatLng>() {
        public LatLng createFromParcel(Parcel in) {
            return new LatLng(in);
        }

        public LatLng[] newArray(int size) {
            return new LatLng[size];
        }
    };

    private double latitude;
    private double longitude;
    private double altitude = 0.0;

    /**
     * Construct a new latitude, longitude point at (0, 0)
     */
    public LatLng() {
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    /**
     * Construct a new latitude, longitude point given float arguments
     * @param latitude Latitude in degrees
     * @param longitude Longitude in degrees
     */
    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Construct a new latitude, longitude, altitude point given float arguments
     * @param latitude Latitude in degrees
     * @param longitude Longitude in degress
     * @param altitude Altitude in meters
     */
    public LatLng(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    /**
     * Transform a Location into a LatLng point
     * @param location Android Location
     */
    public LatLng(Location location) {
        this(location.getLatitude(), location.getLongitude(), location.getAltitude());
    }

    /**
     * Clone an existing latitude longitude point
     * @param aLatLng LatLng
     */
    public LatLng(LatLng aLatLng) {
        this.latitude = aLatLng.latitude;
        this.longitude = aLatLng.longitude;
        this.altitude = aLatLng.altitude;
    }

    protected LatLng(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        altitude = in.readDouble();
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LatLng latLng = (LatLng) o;

        return Double.compare(latLng.altitude, altitude) == 0 && Double.compare(latLng.latitude, latitude) == 0 && Double.compare(latLng.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "LatLng [latitude=" + latitude + ", longitude=" + longitude + ", altitude=" + altitude + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeDouble(altitude);
    }

    /**
     * Calculate distance between two points
     * @param other Other LatLng to compare to
     * @return distance in meters
     */
    public double distanceTo(LatLng other) {
        if(latitude == other.latitude && longitude == other.longitude){
            // return 0.0 to avoid a NaN
            return 0.0;
        }

        final double a1 = Math.toRadians(this.latitude);
        final double a2 = Math.toRadians(this.longitude);
        final double b1 = Math.toRadians(other.getLatitude());
        final double b2 = Math.toRadians(other.getLongitude());

        final double cosa1 = Math.cos(a1);
        final double cosb1 = Math.cos(b1);

        final double t1 = cosa1 * Math.cos(a2) * cosb1 * Math.cos(b2);
        final double t2 = cosa1 * Math.sin(a2) * cosb1 * Math.sin(b2);
        final double t3 = Math.sin(a1) * Math.sin(b1);
        final double tt = Math.acos(t1 + t2 + t3);

        return RADIUS_EARTH_METERS * tt;
    }
}
