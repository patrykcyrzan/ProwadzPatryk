package pl.cyrzan.prowadzpatryk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.opentripplanner.v092snapshot.api.model.Place;
/**
 * Created by Patryk on 14.03.2017.
 */

public class TripPlan {
    private Date date = null;
    private Place from = null;
    private Place to = null;
    private List<Itinerary> itineraries = new ArrayList();

    public TripPlan() {
    }

    public TripPlan(Place from, Place to, Date date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public void addItinerary(Itinerary itinerary) {
        this.itineraries.add(itinerary);
    }

    public List<Itinerary> getItinerary() {
        return this.itineraries;
    }

    public void setItinerary(List<Itinerary> itinerary) {
        this.itineraries = itinerary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Place getFrom() {
        return from;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public Place getTo() {
        return to;
    }

    public void setTo(Place to) {
        this.to = to;
    }
}
