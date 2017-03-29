package pl.cyrzan.prowadzpatryk.model;

import org.opentripplanner.v092snapshot.util.model.EncodedPolylineBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patryk on 27.03.2017.
 */

public class LegShape {

    private EncodedPolylineBean bean;

    public LegShape(EncodedPolylineBean bean) {
        this.bean = bean;
    }

    public int getLength() {
        return bean.getLength();
    }

    public String getRawLevels() {
        return bean.getLevels();
    }

    public List<Integer> getLevels() {
        return decodeLevels(bean.getLevels(), bean.getLength());
    }

    public List<Location> getPoints() {
        return decodeLine(bean.getPoints(), bean.getLength());
    }

    public String getRawPoints() {
        return bean.getPoints();
    }

    /**
     * Decodes encoded levels according to:
     * http://code.google.com/apis/maps/documentation/polylinealgorithm.html
     *
     * @param encoded   The encoded string.
     * @param numPoints The number of points. This is purely used as a hint
     *                  to allocate memory; the function will always return the number
     *                  of points that are contained in the encoded string.
     * @return A list of levels from the encoded string.
     */
    public static List<Integer> decodeLevels(String encoded, int numPoints) {
        assert (numPoints >= 0);
        ArrayList<Integer> array = new ArrayList<Integer>(numPoints);

        final int len = encoded.length();
        int i = 0;
        while (i < len) {
            int shift = 0;
            int result = 0;

            int a, b;
            do {
                a = encoded.charAt(i);
                b = a - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
                ++i;
            } while (b >= 0x20);

            array.add(result);
        }

        return array;
    }

    /**
     * Decodes an encoded polyline into a list of points.
     * Adapted from http://georgelantz.com/files/polyline_decoder.rb
     * For the exact algorithm:
     * http://code.google.com/apis/maps/documentation/polylinealgorithm.html
     *
     * @param encoded   The encoded string.
     * @param numPoints The number of points. This is purely used as a hint
     *                  to allocate memory; the function will always return the number
     *                  of points that are contained in the encoded string.
     * @return A list of points from the encoded string.
     */
    public static List<Location> decodeLine(String encoded, int numPoints) {
        assert (numPoints >= 0);
        ArrayList<Location> array = new ArrayList<Location>(numPoints);

        final int len = encoded.length();
        int i = 0;
        int lat = 0, lon = 0;

        while (i < len) {
            int shift = 0;
            int result = 0;

            int a, b;
            do {
                a = encoded.charAt(i);
                b = a - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
                ++i;
            } while (b >= 0x20);

            final int dlat = ((result & 1) == 1 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                a = encoded.charAt(i);
                b = a - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
                ++i;
            } while (b >= 0x20);

            final int dlon = ((result & 1) == 1 ? ~(result >> 1) : (result >> 1));
            lon += dlon;

            // The polyline encodes in degrees * 1E5, we need decimal degrees
            array.add(Location.coord(lat / 1E5, lon/1E5));
        }

        return array;
    }
}
