package pl.cyrzan.prowadzpatryk.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Patryk on 14.03.2017.
 */

public class Itinerary implements Serializable {
    private static final long serialVersionUID = 8579533939962545543L;
    public long duration = 0L;
    private long startTime = 0L;
    private long endTime = 0L;
    public long walkTime = 0L;
    public long transitTime = 0L;
    public long waitingTime = 0L;
    public Double walkDistance = Double.valueOf(0.0D);
    public Double elevationLost = Double.valueOf(0.0D);
    public Double elevationGained = Double.valueOf(0.0D);
    public Integer transfers = Integer.valueOf(0);
    public List<Leg> legs = new ArrayList();
    public boolean tooSloped = false;

    public Itinerary() {
    }

    public void addLeg(Leg leg) {
        if(leg != null) {
            this.legs.add(leg);
        }

    }

    public void removeLeg(Leg leg) {
        if(leg != null) {
            this.legs.remove(leg);
        }

    }

    public void removeBogusLegs() {
        Iterator it = this.legs.iterator();

        while(it.hasNext()) {
            Leg leg = (Leg)it.next();
            if(leg.isBogusNonTransitLeg()) {
                it.remove();
            }
        }

    }
}

