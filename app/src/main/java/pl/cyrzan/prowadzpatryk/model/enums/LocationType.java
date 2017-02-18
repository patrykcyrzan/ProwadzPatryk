package pl.cyrzan.prowadzpatryk.model.enums;

/**
 * Created by Patryk on 13.02.2017.
 */

public enum LocationType {
    /** Location can represent any of the below. Mainly meant for user input. */
    ANY,
    /** Location represents a station or stop. */
    STATION,
    /** Location represents a point of interest. */
    POI,
    /** Location represents a postal address. */
    ADDRESS,
    /** Location represents a just a plain coordinate, e.g. acquired by GPS. */
    COORD
}