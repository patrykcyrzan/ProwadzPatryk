package pl.cyrzan.prowadzpatryk.ui.common.views.inputWithGps;

/**
 * Created by Patryk on 05.03.2017.
 */

public interface OnLocationGpsInputActionListener {
    void onTextChangedGps(String autocompleteText);
    void stopSuggestLocationsTaskGps();
}
