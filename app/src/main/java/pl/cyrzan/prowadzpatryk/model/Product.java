package pl.cyrzan.prowadzpatryk.model;

import android.os.Parcel;
import android.os.Parcelable;

import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;

/**
 * Created by Patryk on 21.03.2017.
 */

public class Product implements Parcelable {

    private TraverseMode traverseMode;
    private boolean isAvailable;

    public Product(TraverseMode traverseMode, boolean isAvailable) {
        this.traverseMode = traverseMode;
        this.isAvailable = isAvailable;
    }

    public TraverseMode getTraverseMode() {
        return traverseMode;
    }

    public void setTraverseMode(TraverseMode traverseMode) {
        this.traverseMode = traverseMode;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.traverseMode == null ? -1 : this.traverseMode.ordinal());
        dest.writeByte(this.isAvailable ? (byte) 1 : (byte) 0);
    }

    protected Product(Parcel in) {
        int tmpTraverseMode = in.readInt();
        this.traverseMode = tmpTraverseMode == -1 ? null : TraverseMode.values()[tmpTraverseMode];
        this.isAvailable = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
