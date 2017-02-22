package pl.cyrzan.prowadzpatryk.model.enums;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Patryk on 19.02.2017.
 */

public enum Product {
    HIGH_SPEED_TRAIN('I'), REGIONAL_TRAIN('R'), SUBURBAN_TRAIN('S'), SUBWAY('U'), TRAM('T'), BUS('B'), FERRY(
            'F'), CABLECAR('C'), ON_DEMAND('P');

    public static final char UNKNOWN = '?';
    public static final Set<Product> ALL = EnumSet.allOf(Product.class);

    public final char code;

    private Product(final char code) {
        this.code = code;
    }

    public static Product fromCode(final char code) {
        if (code == HIGH_SPEED_TRAIN.code)
            return HIGH_SPEED_TRAIN;
        else if (code == REGIONAL_TRAIN.code)
            return REGIONAL_TRAIN;
        else if (code == SUBURBAN_TRAIN.code)
            return SUBURBAN_TRAIN;
        else if (code == SUBWAY.code)
            return SUBWAY;
        else if (code == TRAM.code)
            return TRAM;
        else if (code == BUS.code)
            return BUS;
        else if (code == FERRY.code)
            return FERRY;
        else if (code == CABLECAR.code)
            return CABLECAR;
        else if (code == ON_DEMAND.code)
            return ON_DEMAND;
        else
            throw new IllegalArgumentException("unknown code: '" + code + "'");
    }

    public static Set<Product> fromCodes(final char[] codes) {
        if (codes == null)
            return null;

        final Set<Product> products = EnumSet.noneOf(Product.class);
        for (int i = 0; i < codes.length; i++)
            products.add(Product.fromCode(codes[i]));
        return products;
    }

    public static char[] toCodes(final Set<Product> products) {
        if (products == null)
            return null;

        final char[] codes = new char[products.size()];
        int i = 0;
        for (final Product product : products)
            codes[i++] = product.code;
        return codes;
    }
}