package pl.cyrzan.prowadzpatryk.ui.common.views.input;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.model.enums.LocationType;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.service.db.dto.RecentLocs;
import pl.cyrzan.prowadzpatryk.ui.common.models.SuggestLocationsResult;
import pl.cyrzan.prowadzpatryk.ui.main.MainContract;
import pl.cyrzan.prowadzpatryk.ui.mapwithform.MapWithFormContract;
import pl.cyrzan.prowadzpatryk.ui.mapwithform.MapWithFormPresenter;
import pl.cyrzan.prowadzpatryk.util.MainUtil;
import pl.cyrzan.prowadzpatryk.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LocationInput extends RelativeLayout implements MapWithFormContract.LocationInputView, Animation.AnimationListener, View.OnFocusChangeListener,
                                                        OnLocationInputAdapterActionListener{

    private final static String TAG = "LocationInput";

    @BindView(R.id.statusButton)
    public ImageView status;
    @BindView(R.id.location)
    public EditText locationACTV;
    @BindView(R.id.clearButton)
    public ImageButton clearButton;
    @BindView(R.id.progress)
    public ProgressBar progressBar;
    @BindView(R.id.placeholderContainer)
    RelativeLayout placeHolderContainer;
    @BindView(R.id.mt_placeholder)
    TextView placeHolder;
    @BindView(R.id.inputContainer)
    LinearLayout inputContainer;

    protected OnLocationActionListener onLocationActionListener;
    OnLocationInputAdapterActionListener onLocationInputAdapterActionListener;

    static final int TYPING_THRESHOLD = 3;
    private boolean suggestionsVisible;
    private boolean searchEnabled;
    private Context context;
    private Location location;
    protected String hint;
    private CharSequence placeholderText;
    private LocationInputAdapter adapter;
    private float destiny;

    public LocationInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LocationInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LocationInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.location_view, this);
        ButterKnife.bind(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LocationView, 0, 0);
        boolean includeHome = a.getBoolean(R.styleable.LocationView_homeLocation, false);
        boolean includeFavs = a.getBoolean(R.styleable.LocationView_favLocation, false);
        placeholderText = a.getString(R.styleable.LocationView_placeholder);
        boolean showIcon = a.getBoolean(R.styleable.LocationView_showIcon, true);
        hint = a.getString(R.styleable.LocationView_hint);
        destiny = getResources().getDisplayMetrics().density;
        a.recycle();

        locationACTV.setHint(hint);
        locationACTV.setOnFocusChangeListener(this);

        if (!showIcon) status.setVisibility(View.GONE);

        if (placeholderText != null)
        {
            placeHolder.setText(placeholderText);
        }

        locationACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((count == 1 && before == 0) || (count == 0 && before == 1))
                    handleTextChanged(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (adapter == null) {
            adapter = createAdapter();
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.mt_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    protected LocationInputAdapter createAdapter(){
        return new LocationInputAdapter(getContext(), this, false, true);
    }

    public void setOnLocationInputActionListener(OnLocationActionListener onLocationActionListener) {
        this.onLocationActionListener = onLocationActionListener;
    }

    public void setText(String txt){
        locationACTV.setText(txt);
    }

    @OnClick(R.id.root)
    public void onRootClick(){
        if (!searchEnabled)
        {
            enableSearch();
        }
    }

    @OnClick(R.id.inputContainer)
    public void onInputContainerClick(){
        Log.i(TAG, "onInputContainerClick");
        if(adapter.getItemCount() > 0){
            if(getText().isEmpty()) {
                showSuggestionsList();
                Log.i(TAG, "onInputContainerClick2");
            }
        }
    }

    @OnClick(R.id.location)
    public void onLocationETClick(){
        Log.i(TAG, "onLocationETClick");
        if(adapter.getItemCount() > 0){
            Log.i(TAG, "onLocationETClick2");
            if(getText().isEmpty()) {
                showSuggestionsList();
                Log.i(TAG, "onLocationETClick3");
            }
        }
    }

    public void enableSearch(){
        adapter.notifyDataSetChanged();
        searchEnabled = true;
        Animation left_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_left);
        Animation left_out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out_left);
        left_in.setAnimationListener(this);
        placeHolderContainer.setVisibility(GONE);
        inputContainer.setVisibility(VISIBLE);
        inputContainer.startAnimation(left_in);
    }

    public void handleTextChanged(CharSequence s) {
        // show clear button
        if (s.length() > 0) {
            clearButton.setVisibility(View.VISIBLE);
            // clear location tag
            setLocation(null, null, false);

            if (s.length() >= TYPING_THRESHOLD) {
                sendRequest(getText());
                progressBar.setVisibility(View.VISIBLE);
            }
        } else {
            clearLocationAndShowDropDown();
        }
    }

    public void sendRequest(String text){
        onLocationActionListener.onTextChanged(text);
    }

    public void setLocation(Location loc, Drawable icon, boolean setText) {
        location = loc;

        if (setText) {
            if (loc != null) {
                locationACTV.setText(MainUtil.getLocationName(loc));
                hideSuggestionsList();
                clearButton.setVisibility(View.VISIBLE);
                cancelTask();
                progressBar.setVisibility(View.GONE);
            } else {
                locationACTV.setText(null);
                clearButton.setVisibility(View.GONE);
            }
        }

        if (icon != null) {
            status.setImageDrawable(icon);
        } else {
            status.setImageResource(R.drawable.ic_location);
        }
    }

    public Location getLocation(){
        return location;
    }

    public void clearLocation() {
        setLocation(null, null);
        /*if(getAdapter() != null) {
            getAdapter().resetSearchTerm();
        }*/
    }

    @OnClick(R.id.clearButton)
    public void onClearButtonClick() {
        clearLocationAndShowDropDown();
    }

    private void clearLocationAndShowDropDown() {
        clearLocation();
        cancelTask();
        clearSuggestions();
        hideSuggestionsList();
        locationACTV.requestFocus();
        reset();
        clearButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    protected void cancelTask() {
        onLocationActionListener.stopSuggestLocationsTask();
    }


    public void setLocation(Location loc, Drawable icon) {
        setLocation(loc, icon, true);
    }

    public void setLocation(@Nullable Location loc) {
        Drawable drawable = MainUtil.getDrawableForLocation(getContext(), loc);
        setLocation(loc, drawable, true);
    }

    public String getText() {
        if (locationACTV != null) {
            return locationACTV.getText().toString();
        } else {
            return null;
        }
    }

    private void animateSuggestions(int from, int to){
        suggestionsVisible = to > 0;
        final RelativeLayout last = (RelativeLayout) findViewById(R.id.last);
        final ViewGroup.LayoutParams lp = last.getLayoutParams();
        if (to == 0 && lp.height == 0)
            return;
        ValueAnimator animator = ValueAnimator.ofInt(from, to);
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lp.height = (int) animation.getAnimatedValue();
                last.setLayoutParams(lp);
            }
        });
        if (adapter.getItemCount() > 0)
            animator.start();
    }

    /**
     * For calculate the height change when item delete or add animation
     * false is retrurn the full height of item,
     * true is return the height of postion subtraction one
     * @param isSubtraction
     */
    private int getListHeight(boolean isSubtraction){
        if(!isSubtraction)
            return (int) (adapter.getListHeight() * destiny);
        return (int) (((adapter.getItemCount() - 1) * adapter.getSingleViewHeight()) * destiny);
    }

    public void disableSearch(){
        searchEnabled = false;
        Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_right);
        out.setAnimationListener(this);
        //searchIcon.setVisibility(VISIBLE);
        inputContainer.startAnimation(out);
        //searchIcon.startAnimation(in);

        if (placeholderText != null)
        {
            placeHolderContainer.setVisibility(VISIBLE);
            placeHolderContainer.startAnimation(in);
        }
        /*if (listenerExists())
            onSearchActionListener.onSearchStateChanged(false);*/
        if (suggestionsVisible) animateSuggestions(getListHeight(false), 0);
    }

    public void showSuggestionsList(){
        animateSuggestions(0, getListHeight(false));
    }

    public void hideSuggestionsList(){
        animateSuggestions(getListHeight(false), 0);
        clearSuggestions();
    }

    public void reset() {
        if(adapter != null) {
            adapter.reset();
        }
    }

    /*protected LocationInputAdapter getAdapter() {
        return (LocationInputAdapter) locationACTV.getAdapter();
    }*/

    @Override
    public void onSuggestLocationsResult(@Nullable List<SuggestLocationResponse> suggestLocations) {
        if (suggestLocations == null) return;
        List<WrapLocation> wrapLocationList = new ArrayList<>();
        for(SuggestLocationResponse suggest:suggestLocations){
            wrapLocationList.add(new WrapLocation(suggest));
        }
        progressBar.setVisibility(View.GONE);
        int startHeight = getListHeight(false);
        if(suggestLocations.size() > 0){
            adapter.setSuggestions(wrapLocationList);
            animateSuggestions(startHeight, getListHeight(false));
        } else {
            animateSuggestions(startHeight, 0);
        }

    }

    public void clearSuggestions(){
        Log.i(TAG, "clearSuggestons1");
        if(suggestionsVisible){
            hideSuggestionsList();
            Log.i(TAG, "clearSuggestons1");
        }
        adapter.clearSuggestions();
    }

    public LocationInputAdapter getAdapter(){
        if(adapter != null){
            return adapter;
        } else {
            return null;
        }
    }

    @Override
    public void onSuggestLocationsResultFail() {
        progressBar.setVisibility(View.GONE);
        adapter.reset();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (!searchEnabled){
            inputContainer.setVisibility(GONE);
            locationACTV.setText("");
        }else {
            locationACTV.requestFocus();
            if (!suggestionsVisible)
                showSuggestionsList();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode()== KeyEvent.KEYCODE_BACK && searchEnabled) {
            animateSuggestions(getListHeight(false), 0);
            disableSearch();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Log.i(TAG, "onFocusChange");
        if (hasFocus)
        {
            imm.showSoftInput(v, 0);
            Log.i(TAG, "onFocusChange1");
        }
        else
        {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            Log.i(TAG, "onFocusChange2");
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(locationACTV.getWindowToken(), 0);
    }

    @Override
    public void onItemClickListener(WrapLocation loc, View view) {
        Drawable icon = ((ImageView) view.findViewById(R.id.imageView)).getDrawable();
        setLocation(loc.getLocation(), icon);
        locationACTV.requestFocus();
        hideSoftKeyboard();
    }
}

