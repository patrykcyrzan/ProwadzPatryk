package pl.cyrzan.prowadzpatryk.model;

import com.google.gson.annotations.Expose;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.opentripplanner.routing.patch.Alerts;
import org.opentripplanner.v092snapshot.api.model.Note;
import org.opentripplanner.v092snapshot.api.model.WalkStep;
import org.opentripplanner.v092snapshot.util.model.EncodedPolylineBean;

import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Patryk on 14.03.2017.
 */

public class Leg implements Serializable {
    private static final long serialVersionUID = -7657410568807464781L;
    public long startTime = 0L;
    public long endTime = 0L;
    public long duration;
    public Double distance = null;
    public TraverseMode mode;
    public String route;
    public String agencyName;
    public String agencyUrl;
    public int agencyTimeZoneOffset;
    public String routeColor;
    public String routeId;
    public String routeTextColor;
    public Boolean interlineWithPreviousLeg;
    public String tripShortName;
    public String headsign;
    public String agencyId;
    public String tripId;
    public Place from;
    public Place to;
    @JsonProperty("intermediateStops")
    public List<Place> stop;
    public EncodedPolylineBean legGeometry;
    @JsonProperty("steps")
    public List<WalkStep> walkSteps;
    private List<Note> notes;
    public String routeShortName;
    public String routeLongName;
    public String boardRule;
    public String alightRule;
    public Boolean rentedBike;
    boolean bogusNonTransitLeg;

    public Leg() {
        this.mode = TraverseMode.WALK;
        this.route = "";
        this.routeColor = null;
        this.routeId = null;
        this.routeTextColor = null;
        this.tripShortName = null;
        this.headsign = null;
        this.agencyId = null;
        this.tripId = null;
        this.from = null;
        this.to = null;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public long getDuration() {
        return this.duration;
    }

    public Double getDistance() {
        return this.distance;
    }

    public TraverseMode getMode() {
        return this.mode;
    }

    public String getRoute() {
        return this.route;
    }

    public String getAgencyName() {
        return this.agencyName;
    }

    public String getAgencyUrl() {
        return this.agencyUrl;
    }

    public int getAgencyTimeZoneOffset() {
        return this.agencyTimeZoneOffset;
    }

    public String getRouteColor() {
        return this.routeColor;
    }

    public String getRouteId() {
        return this.routeId;
    }

    public String getRouteTextColor() {
        return this.routeTextColor;
    }

    public Boolean getInterlineWithPreviousLeg() {
        return this.interlineWithPreviousLeg;
    }

    public String getTripShortName() {
        return this.tripShortName;
    }

    public String getHeadsign() {
        return this.headsign;
    }

    public String getAgencyId() {
        return this.agencyId;
    }

    public String getTripId() {
        return this.tripId;
    }

    public Place getFrom() {
        return this.from;
    }

    public Place getTo() {
        return this.to;
    }

    public EncodedPolylineBean getLegGeometry() {
        return this.legGeometry;
    }

    public String getRouteShortName() {
        return this.routeShortName;
    }

    public String getRouteLongName() {
        return this.routeLongName;
    }

    public String getBoardRule() {
        return this.boardRule;
    }

    public String getAlightRule() {
        return this.alightRule;
    }

    public Boolean getRentedBike() {
        return this.rentedBike;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setMode(TraverseMode mode) {
        this.mode = mode;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public void setAgencyTimeZoneOffset(int agencyTimeZoneOffset) {
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public void setInterlineWithPreviousLeg(Boolean interlineWithPreviousLeg) {
        this.interlineWithPreviousLeg = interlineWithPreviousLeg;
    }

    public void setTripShortName(String tripShortName) {
        this.tripShortName = tripShortName;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public void setTo(Place to) {
        this.to = to;
    }

    public void setLegGeometry(EncodedPolylineBean legGeometry) {
        this.legGeometry = legGeometry;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public void setBoardRule(String boardRule) {
        this.boardRule = boardRule;
    }

    public void setAlightRule(String alightRule) {
        this.alightRule = alightRule;
    }

    public void setRentedBike(Boolean rentedBike) {
        this.rentedBike = rentedBike;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Leg)) {
            return false;
        } else {
            Leg other = (Leg)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label347: {
                    long this$startTime = this.getStartTime();
                    long other$startTime = other.getStartTime();
                    if(this$startTime == 0L) {
                        if(other$startTime == 0L) {
                            break label347;
                        }
                    } else if(this$startTime == other$startTime) {
                        break label347;
                    }

                    return false;
                }

                long this$endTime = this.getEndTime();
                long other$endTime = other.getEndTime();
                if(this$endTime == 0L) {
                    if(other$endTime != 0L) {
                        return false;
                    }
                } else if(this$endTime != other$endTime) {
                    return false;
                }

                if(this.getDuration() != other.getDuration()) {
                    return false;
                } else {
                    label332: {
                        Double this$distance = this.getDistance();
                        Double other$distance = other.getDistance();
                        if(this$distance == null) {
                            if(other$distance == null) {
                                break label332;
                            }
                        } else if(this$distance.equals(other$distance)) {
                            break label332;
                        }

                        return false;
                    }

                    TraverseMode this$mode = this.getMode();
                    TraverseMode other$mode = other.getMode();
                    if(this$mode == null) {
                        if(other$mode != null) {
                            return false;
                        }
                    } else if(!this$mode.equals(other$mode)) {
                        return false;
                    }

                    String this$route = this.getRoute();
                    String other$route = other.getRoute();
                    if(this$route == null) {
                        if(other$route != null) {
                            return false;
                        }
                    } else if(!this$route.equals(other$route)) {
                        return false;
                    }

                    label311: {
                        String this$agencyName = this.getAgencyName();
                        String other$agencyName = other.getAgencyName();
                        if(this$agencyName == null) {
                            if(other$agencyName == null) {
                                break label311;
                            }
                        } else if(this$agencyName.equals(other$agencyName)) {
                            break label311;
                        }

                        return false;
                    }

                    label304: {
                        String this$agencyUrl = this.getAgencyUrl();
                        String other$agencyUrl = other.getAgencyUrl();
                        if(this$agencyUrl == null) {
                            if(other$agencyUrl == null) {
                                break label304;
                            }
                        } else if(this$agencyUrl.equals(other$agencyUrl)) {
                            break label304;
                        }

                        return false;
                    }

                    if(this.getAgencyTimeZoneOffset() != other.getAgencyTimeZoneOffset()) {
                        return false;
                    } else {
                        String this$routeColor = this.getRouteColor();
                        String other$routeColor = other.getRouteColor();
                        if(this$routeColor == null) {
                            if(other$routeColor != null) {
                                return false;
                            }
                        } else if(!this$routeColor.equals(other$routeColor)) {
                            return false;
                        }

                        String this$routeId = this.getRouteId();
                        String other$routeId = other.getRouteId();
                        if(this$routeId == null) {
                            if(other$routeId != null) {
                                return false;
                            }
                        } else if(!this$routeId.equals(other$routeId)) {
                            return false;
                        }

                        label282: {
                            String this$routeTextColor = this.getRouteTextColor();
                            String other$routeTextColor = other.getRouteTextColor();
                            if(this$routeTextColor == null) {
                                if(other$routeTextColor == null) {
                                    break label282;
                                }
                            } else if(this$routeTextColor.equals(other$routeTextColor)) {
                                break label282;
                            }

                            return false;
                        }

                        Boolean this$interlineWithPreviousLeg = this.getInterlineWithPreviousLeg();
                        Boolean other$interlineWithPreviousLeg = other.getInterlineWithPreviousLeg();
                        if(this$interlineWithPreviousLeg == null) {
                            if(other$interlineWithPreviousLeg != null) {
                                return false;
                            }
                        } else if(!this$interlineWithPreviousLeg.equals(other$interlineWithPreviousLeg)) {
                            return false;
                        }

                        label268: {
                            String this$tripShortName = this.getTripShortName();
                            String other$tripShortName = other.getTripShortName();
                            if(this$tripShortName == null) {
                                if(other$tripShortName == null) {
                                    break label268;
                                }
                            } else if(this$tripShortName.equals(other$tripShortName)) {
                                break label268;
                            }

                            return false;
                        }

                        String this$headsign = this.getHeadsign();
                        String other$headsign = other.getHeadsign();
                        if(this$headsign == null) {
                            if(other$headsign != null) {
                                return false;
                            }
                        } else if(!this$headsign.equals(other$headsign)) {
                            return false;
                        }

                        String this$agencyId = this.getAgencyId();
                        String other$agencyId = other.getAgencyId();
                        if(this$agencyId == null) {
                            if(other$agencyId != null) {
                                return false;
                            }
                        } else if(!this$agencyId.equals(other$agencyId)) {
                            return false;
                        }

                        label247: {
                            String this$tripId = this.getTripId();
                            String other$tripId = other.getTripId();
                            if(this$tripId == null) {
                                if(other$tripId == null) {
                                    break label247;
                                }
                            } else if(this$tripId.equals(other$tripId)) {
                                break label247;
                            }

                            return false;
                        }

                        label240: {
                            Place this$from = this.getFrom();
                            Place other$from = other.getFrom();
                            if(this$from == null) {
                                if(other$from == null) {
                                    break label240;
                                }
                            } else if(this$from.equals(other$from)) {
                                break label240;
                            }

                            return false;
                        }

                        label233: {
                            Place this$to = this.getTo();
                            Place other$to = other.getTo();
                            if(this$to == null) {
                                if(other$to == null) {
                                    break label233;
                                }
                            } else if(this$to.equals(other$to)) {
                                break label233;
                            }

                            return false;
                        }

                        List this$stop = this.getStop();
                        List other$stop = other.getStop();
                        if(this$stop == null) {
                            if(other$stop != null) {
                                return false;
                            }
                        } else if(!this$stop.equals(other$stop)) {
                            return false;
                        }

                        label219: {
                            EncodedPolylineBean this$legGeometry = this.getLegGeometry();
                            EncodedPolylineBean other$legGeometry = other.getLegGeometry();
                            if(this$legGeometry == null) {
                                if(other$legGeometry == null) {
                                    break label219;
                                }
                            } else if(this$legGeometry.equals(other$legGeometry)) {
                                break label219;
                            }

                            return false;
                        }

                        label212: {
                            List this$walkSteps = this.getWalkSteps();
                            List other$walkSteps = other.getWalkSteps();
                            if(this$walkSteps == null) {
                                if(other$walkSteps == null) {
                                    break label212;
                                }
                            } else if(this$walkSteps.equals(other$walkSteps)) {
                                break label212;
                            }

                            return false;
                        }

                        List this$notes = this.getNotes();
                        List other$notes = other.getNotes();
                        if(this$notes == null) {
                            if(other$notes != null) {
                                return false;
                            }
                        } else if(!this$notes.equals(other$notes)) {
                            return false;
                        }

                        label191: {
                            String this$routeShortName = this.getRouteShortName();
                            String other$routeShortName = other.getRouteShortName();
                            if(this$routeShortName == null) {
                                if(other$routeShortName == null) {
                                    break label191;
                                }
                            } else if(this$routeShortName.equals(other$routeShortName)) {
                                break label191;
                            }

                            return false;
                        }

                        label184: {
                            String this$routeLongName = this.getRouteLongName();
                            String other$routeLongName = other.getRouteLongName();
                            if(this$routeLongName == null) {
                                if(other$routeLongName == null) {
                                    break label184;
                                }
                            } else if(this$routeLongName.equals(other$routeLongName)) {
                                break label184;
                            }

                            return false;
                        }

                        String this$boardRule = this.getBoardRule();
                        String other$boardRule = other.getBoardRule();
                        if(this$boardRule == null) {
                            if(other$boardRule != null) {
                                return false;
                            }
                        } else if(!this$boardRule.equals(other$boardRule)) {
                            return false;
                        }

                        label170: {
                            String this$alightRule = this.getAlightRule();
                            String other$alightRule = other.getAlightRule();
                            if(this$alightRule == null) {
                                if(other$alightRule == null) {
                                    break label170;
                                }
                            } else if(this$alightRule.equals(other$alightRule)) {
                                break label170;
                            }

                            return false;
                        }

                        Boolean this$rentedBike = this.getRentedBike();
                        Boolean other$rentedBike = other.getRentedBike();
                        if(this$rentedBike == null) {
                            if(other$rentedBike != null) {
                                return false;
                            }
                        } else if(!this$rentedBike.equals(other$rentedBike)) {
                            return false;
                        }

                        if(this.isBogusNonTransitLeg() != other.isBogusNonTransitLeg()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
    }

    public boolean canEqual(Object other) {
        return other instanceof org.opentripplanner.v092snapshot.api.model.Leg;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        long $startTime = this.getStartTime();
        int result1 = result * 31 + (int)($startTime >>> 32 ^ $startTime);
        long $endTime = this.getEndTime();
        result1 = result1 * 31 + (int)($endTime >>> 32 ^ $endTime);
        long $duration = this.getDuration();
        result1 = result1 * 31 + (int)($duration >>> 32 ^ $duration);
        Double $distance = this.getDistance();
        result1 = result1 * 31 + ($distance == null?0:$distance.hashCode());
        String $mode = this.getMode().toString();
        result1 = result1 * 31 + ($mode == null?0:$mode.hashCode());
        String $route = this.getRoute();
        result1 = result1 * 31 + ($route == null?0:$route.hashCode());
        String $agencyName = this.getAgencyName();
        result1 = result1 * 31 + ($agencyName == null?0:$agencyName.hashCode());
        String $agencyUrl = this.getAgencyUrl();
        result1 = result1 * 31 + ($agencyUrl == null?0:$agencyUrl.hashCode());
        result1 = result1 * 31 + this.getAgencyTimeZoneOffset();
        String $routeColor = this.getRouteColor();
        result1 = result1 * 31 + ($routeColor == null?0:$routeColor.hashCode());
        String $routeId = this.getRouteId();
        result1 = result1 * 31 + ($routeId == null?0:$routeId.hashCode());
        String $routeTextColor = this.getRouteTextColor();
        result1 = result1 * 31 + ($routeTextColor == null?0:$routeTextColor.hashCode());
        Boolean $interlineWithPreviousLeg = this.getInterlineWithPreviousLeg();
        result1 = result1 * 31 + ($interlineWithPreviousLeg == null?0:$interlineWithPreviousLeg.hashCode());
        String $tripShortName = this.getTripShortName();
        result1 = result1 * 31 + ($tripShortName == null?0:$tripShortName.hashCode());
        String $headsign = this.getHeadsign();
        result1 = result1 * 31 + ($headsign == null?0:$headsign.hashCode());
        String $agencyId = this.getAgencyId();
        result1 = result1 * 31 + ($agencyId == null?0:$agencyId.hashCode());
        String $tripId = this.getTripId();
        result1 = result1 * 31 + ($tripId == null?0:$tripId.hashCode());
        Place $from = this.getFrom();
        result1 = result1 * 31 + ($from == null?0:$from.hashCode());
        Place $to = this.getTo();
        result1 = result1 * 31 + ($to == null?0:$to.hashCode());
        List $stop = this.getStop();
        result1 = result1 * 31 + ($stop == null?0:$stop.hashCode());
        EncodedPolylineBean $legGeometry = this.getLegGeometry();
        result1 = result1 * 31 + ($legGeometry == null?0:$legGeometry.hashCode());
        List $walkSteps = this.getWalkSteps();
        result1 = result1 * 31 + ($walkSteps == null?0:$walkSteps.hashCode());
        List $notes = this.getNotes();
        result1 = result1 * 31 + ($notes == null?0:$notes.hashCode());
        String $routeShortName = this.getRouteShortName();
        result1 = result1 * 31 + ($routeShortName == null?0:$routeShortName.hashCode());
        String $routeLongName = this.getRouteLongName();
        result1 = result1 * 31 + ($routeLongName == null?0:$routeLongName.hashCode());
        String $boardRule = this.getBoardRule();
        result1 = result1 * 31 + ($boardRule == null?0:$boardRule.hashCode());
        String $alightRule = this.getAlightRule();
        result1 = result1 * 31 + ($alightRule == null?0:$alightRule.hashCode());
        Boolean $rentedBike = this.getRentedBike();
        result1 = result1 * 31 + ($rentedBike == null?0:$rentedBike.hashCode());
        result1 = result1 * 31 + (this.isBogusNonTransitLeg()?1231:1237);
        return result1;
    }

    public String toString() {
        return "Leg(startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", duration=" + this.getDuration() + ", distance=" + this.getDistance() + ", mode=" + this.getMode() + ", route=" + this.getRoute() + ", agencyName=" + this.getAgencyName() + ", agencyUrl=" + this.getAgencyUrl() + ", agencyTimeZoneOffset=" + this.getAgencyTimeZoneOffset() + ", routeColor=" + this.getRouteColor() + ", routeId=" + this.getRouteId() + ", routeTextColor=" + this.getRouteTextColor() + ", interlineWithPreviousLeg=" + this.getInterlineWithPreviousLeg() + ", tripShortName=" + this.getTripShortName() + ", headsign=" + this.getHeadsign() + ", agencyId=" + this.getAgencyId() + ", tripId=" + this.getTripId() + ", from=" + this.getFrom() + ", to=" + this.getTo() + ", stop=" + this.getStop() + ", legGeometry=" + this.getLegGeometry() + ", walkSteps=" + this.getWalkSteps() + ", notes=" + this.getNotes() + ", routeShortName=" + this.getRouteShortName() + ", routeLongName=" + this.getRouteLongName() + ", boardRule=" + this.getBoardRule() + ", alightRule=" + this.getAlightRule() + ", rentedBike=" + this.getRentedBike() + ", bogusNonTransitLeg=" + this.isBogusNonTransitLeg() + ")";
    }

    public List<Place> getStop() {
        return this.stop;
    }

    public void setStop(List<Place> stop) {
        this.stop = stop;
    }

    public List<WalkStep> getWalkSteps() {
        return this.walkSteps;
    }

    public void setWalkSteps(List<WalkStep> walkSteps) {
        this.walkSteps = walkSteps;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public boolean isBogusNonTransitLeg() {
        return this.bogusNonTransitLeg;
    }

    public void setBogusNonTransitLeg(boolean bogusNonTransitLeg) {
        this.bogusNonTransitLeg = bogusNonTransitLeg;
    }
}

