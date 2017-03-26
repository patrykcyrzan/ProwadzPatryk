package pl.cyrzan.prowadzpatryk.ui.common.views.input;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.GPS;
import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.RECENT;

/**
 * Created by Patryk on 19.02.2017.
 */

public class LocationInputAdapter extends RecyclerView.Adapter<LocationInputAdapter.LocationInputViewHolder> {

    private static final String TAG = "LocationInputAdapter";

    private List<WrapLocation> suggestions = new ArrayList<>();
    private List<WrapLocation> optionalLocations = new ArrayList<>();
    private List<WrapLocation> recentLocations = new ArrayList<>();
    private OnLocationInputAdapterActionListener onLocationInputAdapterActionListener;
    protected int maxSuggestionsCount = 5;
    private final boolean includeGPS;
    private final boolean includeRecentLocs;
    private Context context;

    public LocationInputAdapter(Context context,
                                OnLocationInputAdapterActionListener onLocationInputAdapterActionListener,
                                boolean includeGPS,
                                boolean includeRecentLocs){
        this.context = context;
        this.onLocationInputAdapterActionListener = onLocationInputAdapterActionListener;
        this.includeGPS = includeGPS;
        this.includeRecentLocs = includeRecentLocs;

        getOptionalLocations();
        addOptionalLocationsToDropDown();
    }

    @Override
    public LocationInputAdapter.LocationInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationInputAdapter.LocationInputViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_input_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LocationInputAdapter.LocationInputViewHolder holder, int position) {
        WrapLocation wrapLocation = getSuggestLocationAtPosition(position);

        if(wrapLocation == null || wrapLocation.getLocation() == null) return;

        if(wrapLocation.getType() == GPS){
            holder.text.setText(context.getString(R.string.location_gps));
        } else if(wrapLocation.getType() == RECENT) {
            holder.text.setText(wrapLocation.getName());
        } else {
            holder.text.setText(wrapLocation.getName());
        }

        holder.imageView.setImageDrawable(MainUtil.getDrawableForLocation(context, null, wrapLocation, false));
    }

    public int getSingleViewHeight() {
        return 50;
    }

    public int getListHeight(){
        return getItemCount() * getSingleViewHeight();
    }

    public WrapLocation getSuggestLocationAtPosition(int position){
        return suggestions.get(position);
    }

    public void setSuggestions(List<WrapLocation> suggestions) {
        this.suggestions = suggestions;
        notifyDataSetChanged();
    }

    public void clearSuggestions() {
        suggestions.clear();
        notifyDataSetChanged();
    }

    private void resetOptionalLocations() {
        optionalLocations.clear();
        getOptionalLocations();
    }

    private void addOptionalLocationsToDropDown(){
        this.suggestions = optionalLocations;
        this.notifyDataSetChanged();
    }

    public void setRecentLocations(List<WrapLocation> recentLocations){
        this.recentLocations = recentLocations;
        resetOptionalLocations();
        notifyDataSetChanged();
    }

    private List<WrapLocation> getOptionalLocations(){

        if(includeGPS) {
            optionalLocations.add(new WrapLocation(GPS));
        }
        if(includeRecentLocs){
            optionalLocations.addAll(recentLocations);
        }

        return optionalLocations;
    }

    void reset() {
        suggestions.clear();
        optionalLocations.clear();

        getOptionalLocations();
        addOptionalLocationsToDropDown();
    }


    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    class LocationInputViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.imageView)
        ImageView imageView;

        private View view;

        public LocationInputViewHolder(final View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.text)
        public void onItemClick(){
            WrapLocation location = getSuggestLocationAtPosition(getAdapterPosition());
            onLocationInputAdapterActionListener.onItemClickListener(location, view);
        }

    }
}
