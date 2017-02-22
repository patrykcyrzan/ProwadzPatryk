package pl.cyrzan.prowadzpatryk.ui.common.views.input;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.ui.common.models.SuggestLocationsResult;
import pl.cyrzan.prowadzpatryk.ui.main.MainContract;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationInput extends LinearLayout implements MainContract.LocationInput {

    private final static String TAG = "LocationInput";

    @BindView(R.id.statusButton)
    ImageView status;
    @BindView(R.id.location)
    AutoCompleteTextView locationACTV;
    @BindView(R.id.clearButton)
    ImageButton clearButton;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    OnLocationInputActionListener onLocationInputActionListener;

    static final int TYPING_THRESHOLD = 3;

    private Context context;
    private Location location;
    protected String hint;

    public LocationInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LocationInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocationInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.location_view, this);
        ButterKnife.bind(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LocationView, 0, 0);
        boolean includeHome = a.getBoolean(R.styleable.LocationView_homeLocation, false);
        boolean includeFavs = a.getBoolean(R.styleable.LocationView_favLocation, false);
        boolean showIcon = a.getBoolean(R.styleable.LocationView_showIcon, true);
        hint = a.getString(R.styleable.LocationView_hint);
        a.recycle();

        setOrientation(LinearLayout.HORIZONTAL);

        locationACTV.setHint(hint);

        if (!showIcon) status.setVisibility(View.GONE);

        locationACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if((count == 1 && before == 0) || (count == 0 && before == 1)) handleTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setOnLocationInputActionListener (OnLocationInputActionListener onLocationInputActionListener) {
        this.onLocationInputActionListener = onLocationInputActionListener;
    }

    public void handleTextChanged(CharSequence s) {
        // show clear button
        if(s.length() > 0) {
            clearButton.setVisibility(View.VISIBLE);
            // clear location tag
            //setLocation(null, null, false);

            if(s.length() >= TYPING_THRESHOLD) {
                Log.i(TAG, "handleTextChanged length>=typing_threshold");
                onLocationInputActionListener.onTextChanged(getText());
            }
        } else {
            //clearLocationAndShowDropDown();
        }
    }

    public void setLocation(Location loc, Drawable icon, boolean setText) {
        location = loc;

        if(setText) {
            if(loc != null) {
                locationACTV.setText(MainUtil.getLocationName(loc));
                locationACTV.dismissDropDown();
                clearButton.setVisibility(View.VISIBLE);
                //stopSuggestLocationsTask();
            } else {
                locationACTV.setText(null);
                clearButton.setVisibility(View.GONE);
            }
        }

        if(icon != null) {
            status.setImageDrawable(icon);
        } else {
            status.setImageResource(R.drawable.ic_location);
        }
    }

    public void clearLocation() {
        setLocation(null, null);
        /*if(getAdapter() != null) {
            getAdapter().resetSearchTerm();
        }*/
    }

    @OnClick(R.id.clearButton)
    public void onClearButtonClick(){
        clearLocationAndShowDropDown();
    }

    private void clearLocationAndShowDropDown() {
        clearLocation();
        clearButton.setVisibility(View.GONE);
        if (isShown()) {
            locationACTV.requestFocus();
            locationACTV.showDropDown();
        }
    }

    public void setLocation(Location loc, Drawable icon) {
        setLocation(loc, icon, true);
    }

    public void setLocation(@Nullable Location loc) {
        Drawable drawable = MainUtil.getDrawableForLocation(getContext(), loc);
        setLocation(loc, drawable, true);
    }

    public String getText() {
        if(locationACTV != null) {
            Log.i(TAG, "getText locationACTV != null");
            Log.i(TAG, "getText locationACTV != null "+locationACTV.getText().toString());
            return locationACTV.getText().toString();
        } else {
            Log.i(TAG, "getText locationACTV == null");
            return null;
        }
    }

    @Override
    public void onSuggestLocationsResult(@Nullable List<SuggestLocationResponse> suggestLocations) {
        progressBar.setVisibility(View.GONE);

        if(suggestLocations == null) return;


    }
}

