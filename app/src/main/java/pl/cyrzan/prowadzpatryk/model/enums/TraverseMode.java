package pl.cyrzan.prowadzpatryk.model.enums;

import pl.cyrzan.prowadzpatryk.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Patryk on 14.03.2017.
 */

public enum TraverseMode {
    WALK,
    TRAM,
    REGIONAL_TRAIN,
    BUS,
    TRANSIT,
    CABLE_CAR,
    RAIL;

    private TraverseMode() {
    }

    public static List<TraverseMode> getTransitModes(){
        return Arrays.asList(BUS, TRAM, REGIONAL_TRAIN, CABLE_CAR, RAIL);
    }

    public boolean isTransit() {
        return this == TRAM || this == BUS || this == TRANSIT || this == REGIONAL_TRAIN || this == CABLE_CAR || this == RAIL;
    }

    public boolean isWalk(){
        return this == WALK;
    }

    public boolean isBus(){
        return this == BUS;
    }

    public boolean isTram() {
        return this == TRAM;
    }

    public boolean isRegionalTrain(){
        return this == REGIONAL_TRAIN;
    }
}
