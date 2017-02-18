package pl.cyrzan.prowadzpatryk.api.model;

/**
 * Created by Patryk on 08.01.2017.
 */

public class SuggestLocationResponse {

    private String description;
    private String id;
    private Double lng;
    private Double lat;

    public Double getLat() {
        return lat;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getLng() {
        return lng;
    }

}
