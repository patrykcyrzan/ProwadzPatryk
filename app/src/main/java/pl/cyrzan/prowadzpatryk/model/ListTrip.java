package pl.cyrzan.prowadzpatryk.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Patryk on 19.03.2017.
 */

public class ListTrip {

    private DateTime date = null;
    private Location from = null;
    private Location to = null;
    private Itinerary itinerary;
    private boolean expanded;

    private ListTrip(Itinerary itinerary, Location from, Location to, DateTime date){
        this.itinerary = itinerary;
        this.from = from;
        this.to = to;
        this.date = date;
        this.expanded = false;
    }

    public static List<ListTrip> getList(List<Itinerary> tripList, Location from, Location to, DateTime date){
        List<ListTrip> newList = new ArrayList<>();

        for(Itinerary itinerary : tripList){
            newList.add(new ListTrip(itinerary, from, to, date));
        }

        return newList;
    }

    public DateTime getDate() {
        return date;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
