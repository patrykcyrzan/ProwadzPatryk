package pl.cyrzan.prowadzpatryk.service.preferences.model;

import android.os.Parcel;
import android.os.Parcelable;

import pl.cyrzan.prowadzpatryk.model.Product;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Patryk on 20.03.2017.
 */

public class UserPreferences implements Parcelable {

    private List<Product> productList;

    public UserPreferences(){
        List<TraverseMode> traverseModeList = TraverseMode.getTransitModes();

        productList = new ArrayList<>();

        for(TraverseMode mode : traverseModeList){
            productList.add(new Product(mode, true));
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /*public boolean isAvaibleByMode(TraverseMode mode){
        productList.stream().filter(product -> product.getTraverseMode() == mode).forEach(product -> {
        });
        return false;
    }*/


    /*public boolean isProductWalk() {
        return productWalk;
    }

    public void setProductWalk(boolean productWalk) {
        this.productWalk = productWalk;
    }

    public boolean isProductTram() {
        return productTram;
    }

    public void setProductTram(boolean productTram) {
        this.productTram = productTram;
    }

    public boolean isProductRegionalTrain() {
        return productRegionalTrain;
    }

    public void setProductRegionalTrain(boolean productRegionalTrain) {
        this.productRegionalTrain = productRegionalTrain;
    }

    public boolean isProductBus() {
        return productBus;
    }

    public void setProductBus(boolean productBus) {
        this.productBus = productBus;
    }

    public boolean isProductCableCar() {
        return productCableCar;
    }

    public void setProductCableCar(boolean productCableCar) {
        this.productCableCar = productCableCar;
    }

    public boolean isProductRail() {
        return productRail;
    }

    public void setProductRail(boolean productRail) {
        this.productRail = productRail;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.productList);
    }

    protected UserPreferences(Parcel in) {
        this.productList = in.createTypedArrayList(Product.CREATOR);
    }

    public static final Creator<UserPreferences> CREATOR = new Creator<UserPreferences>() {
        @Override
        public UserPreferences createFromParcel(Parcel source) {
            return new UserPreferences(source);
        }

        @Override
        public UserPreferences[] newArray(int size) {
            return new UserPreferences[size];
        }
    };
}
