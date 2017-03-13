package pl.cyrzan.prowadzpatryk.service.api.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opentripplanner.routing.core.OptimizeType;
import org.opentripplanner.routing.core.TraverseMode;
import org.opentripplanner.routing.core.TraverseModeSet;
import org.opentripplanner.util.DateUtils;

import pl.cyrzan.prowadzpatryk.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Patryk on 11.03.2017.
 */

public class TripRequest {
    private String from;
    private String to;
    private List<String> intermediatePlaces;
    private Double maxWalkDistance = Double.valueOf(1.7976931348623157E308D);
    private TraverseModeSet modes;
    private OptimizeType optimize;
    private double triangleTimeFactor;
    private double triangleSlopeFactor;
    private double triangleSafetyFactor;
    private DateTime dateTime;
    private boolean arriveBy;
    private boolean wheelchair;
    private Integer numItineraries;
    private double maxSlope;
    private boolean showIntermediateStops;
    private String[] preferredRoutes;
    private String[] unpreferredRoutes;
    private boolean bikeRental;
    private final HashMap<String, String> parameters;
    private Integer minTransferTime;

    public boolean getBikeRental() {
        return this.bikeRental;
    }

    public void setBikeRental(boolean bikeRental) {
        this.bikeRental = bikeRental;
    }

    public TripRequest() {
        this.optimize = OptimizeType.QUICK;
        this.dateTime = new DateTime();
        this.arriveBy = false;
        this.wheelchair = false;
        this.numItineraries = Integer.valueOf(3);
        this.maxSlope = -1.0D;
        this.showIntermediateStops = false;
        this.parameters = new HashMap();
        this.modes = new TraverseModeSet("TRANSIT,WALK");
        this.setIntermediatePlaces(new ArrayList());
    }

    public HashMap<String, String> getParameters() {
        return this.parameters;
    }

    private void paramPush(String param, Object o) {
        if(o != null) {
            this.parameters.put(param, o.toString());
        }

    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.paramPush("fromPlace", from);
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.paramPush("toPlace", to);
        this.to = to;
    }

    public Double getMaxWalkDistance() {
        return this.maxWalkDistance;
    }

    public void setMaxWalkDistance(Double walk) {
        this.paramPush("maxWalkDistance", walk);
        this.maxWalkDistance = walk;
    }

    public String getModesAsStr() {
        String retVal = null;

        TraverseMode m;
        for(Iterator i$ = this.modes.getModes().iterator(); i$.hasNext(); retVal = retVal + m) {
            m = (TraverseMode)i$.next();
            if(retVal == null) {
                retVal = "";
            } else {
                retVal = retVal + ", ";
            }
        }

        return retVal;
    }

    public void addMode(TraverseMode mode) {
        this.modes.setMode(mode, true);
        this.paramPush("mode", this.modes);
    }

    public void addMode(List<TraverseMode> mList) {
        Iterator i$ = mList.iterator();

        while(i$.hasNext()) {
            TraverseMode m = (TraverseMode)i$.next();
            this.addMode(m);
        }

        this.paramPush("mode", this.modes);
    }

    public OptimizeType getOptimize() {
        return this.optimize;
    }

    public void setOptimize(OptimizeType opt) {
        this.optimize = opt;
        this.paramPush("optimize", this.optimize);
    }

    public DateTime getDateTime() {
        return this.dateTime;
    }

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(Constants.FORMAT_OTP_SERVER_DATE_REQUEST);
        LocalDate date = dateTime.toLocalDate();
        return date.toString(Constants.FORMAT_OTP_SERVER_DATE_REQUEST);
    }

    public String getTime(){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(Constants.FORMAT_OTP_SERVER_TIME_REQUEST);
        LocalTime time = dateTime.toLocalTime();

        return time.toString(Constants.FORMAT_OTP_SERVER_TIME_REQUEST);
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isArriveBy() {
        return this.arriveBy;
    }

    public void setArriveBy(boolean arriveBy) {
        this.paramPush("arriveBy", Boolean.valueOf(arriveBy));
        this.arriveBy = arriveBy;
    }

    public Integer getNumItineraries() {
        return this.numItineraries;
    }

    public void setNumItineraries(Integer numItineraries) {
        if(numItineraries.intValue() < 1 || numItineraries.intValue() > 10) {
            numItineraries = Integer.valueOf(3);
        }

        this.paramPush("numItineraries", numItineraries);
        this.numItineraries = numItineraries;
    }

    public String toHtmlString() {
        return this.toString("<br/>");
    }

    public String toString() {
        return this.toString(" ");
    }

    public String toString(String sep) {
        return this.getFrom() + sep + this.getTo() + sep + this.getMaxWalkDistance() + sep + this.getDateTime() + sep + this.isArriveBy() + sep + this.getOptimize() + sep + this.getModesAsStr() + sep + this.getNumItineraries();
    }

    public TraverseModeSet getModeSet() {
        return this.modes;
    }

    public String getModes(){
        return TraverseMode.TRANSIT.toString()+","+TraverseMode.WALK.toString();
    }

    public void removeMode(TraverseMode mode) {
        this.modes.setMode(mode, false);
        this.paramPush("mode", this.modes);
    }

    public void setModes(TraverseModeSet modes) {
        this.modes = modes;
        this.paramPush("mode", modes);
    }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
        this.paramPush("wheelchair", Boolean.valueOf(wheelchair));
    }

    public boolean getWheelchair() {
        return this.wheelchair;
    }

    public void setIntermediatePlaces(List<String> intermediatePlaces) {
        this.intermediatePlaces = intermediatePlaces;
    }

    public List<String> getIntermediatePlaces() {
        return this.intermediatePlaces;
    }

    public double getMaxSlope() {
        return this.maxSlope;
    }

    public void setMaxSlope(double maxSlope) {
        this.maxSlope = maxSlope;
    }

    public void setShowIntermediateStops(boolean showIntermediateStops) {
        this.showIntermediateStops = showIntermediateStops;
        this.paramPush("showIntermediateStops", Boolean.valueOf(showIntermediateStops));
    }

    public boolean getShowIntermediateStops() {
        return this.showIntermediateStops;
    }

    public void setMinTransferTime(Integer minTransferTime) {
        this.minTransferTime = minTransferTime;
    }

    public Integer getMinTransferTime() {
        return this.minTransferTime;
    }

    public void setPreferredRoutes(String[] preferredRoutes) {
        this.preferredRoutes = preferredRoutes;
    }

    public String[] getPreferredRoutes() {
        return this.preferredRoutes;
    }

    public void setUnpreferredRoutes(String[] unpreferredRoutes) {
        this.unpreferredRoutes = unpreferredRoutes;
    }

    public String[] getUnpreferredRoutes() {
        return this.unpreferredRoutes;
    }

    public double getTriangleTimeFactor() {
        return this.triangleTimeFactor;
    }

    public void setTriangleTimeFactor(double triangleTimeFactor) {
        this.paramPush("triangleTimeFactor", Double.valueOf(triangleTimeFactor));
        this.triangleTimeFactor = triangleTimeFactor;
    }

    public double getTriangleSlopeFactor() {
        return this.triangleSlopeFactor;
    }

    public void setTriangleSlopeFactor(double triangleSlopeFactor) {
        this.paramPush("triangleSlopeFactor", Double.valueOf(triangleSlopeFactor));
        this.triangleSlopeFactor = triangleSlopeFactor;
    }

    public double getTriangleSafetyFactor() {
        return this.triangleSafetyFactor;
    }

    public void setTriangleSafetyFactor(double triangleSafetyFactor) {
        this.paramPush("triangleSafetyFactor", Double.valueOf(triangleSafetyFactor));
        this.triangleSafetyFactor = triangleSafetyFactor;
    }
}
