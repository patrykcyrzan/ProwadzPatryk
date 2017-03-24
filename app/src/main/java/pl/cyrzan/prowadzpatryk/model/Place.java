package pl.cyrzan.prowadzpatryk.model;

import org.opentripplanner.api.model.AgencyAndId;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Patryk on 19.03.2017.
 */

public class Place implements Serializable {
    private static final long serialVersionUID = 2731076807632135897L;
    public String name;
    public String stopId;
    public String stopCode;
    public Double lon;
    public Double lat;
    public long arrival = 0L;
    public long departure = 0L;
    public String orig;
    public String zoneId;
    public String geometry;

    public Place() {
        this.name = null;
        this.stopId = null;
        this.stopCode = null;
        this.lon = null;
        this.lat = null;
        this.arrival = 0L;
        this.departure = 0L;
    }

    public Place(Double lon, Double lat, String name) {
        this.name = null;
        this.stopId = null;
        this.stopCode = null;
        this.lon = null;
        this.lat = null;
        this.arrival = 0L;
        this.departure = 0L;
        this.lon = lon;
        this.lat = lat;
        this.name = name;
    }

    public Place(Double lon, Double lat, String name, long timeAtState) {
        this(lon, lat, name);
        this.arrival = this.departure = timeAtState;
    }

    public String getGeometry() {
        return this.geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
