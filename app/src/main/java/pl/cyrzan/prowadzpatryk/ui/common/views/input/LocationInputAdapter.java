package pl.cyrzan.prowadzpatryk.ui.common.views.input;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Location;
import pl.cyrzan.prowadzpatryk.model.WrapLocation;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;
import pl.cyrzan.prowadzpatryk.util.MainUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static pl.cyrzan.prowadzpatryk.model.WrapLocation.WrapType.GPS;

/**
 * Created by Patryk on 19.02.2017.
 */

public class LocationInputAdapter extends RecyclerView.Adapter<LocationInputAdapter.LocationInputViewHolder> {

    private List<WrapLocation> suggestions = new ArrayList<>();
    private List<WrapLocation> optionalLocations = new ArrayList<>();
    private OnLocationInputAdapterActionListener onLocationInputAdapterActionListener;
    protected int maxSuggestionsCount = 5;
    private final boolean includeGPS;
    private Context context;

    public LocationInputAdapter(Context context,
                                OnLocationInputAdapterActionListener onLocationInputAdapterActionListener,
                                boolean includeGPS){
        this.context = context;
        this.onLocationInputAdapterActionListener = onLocationInputAdapterActionListener;
        this.includeGPS = includeGPS;

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

    public void deleteSuggestion(int postion, String r){
        if(r == null)
            return;
        //delete item with animation
        if(suggestions.contains(r))
        {
            this.notifyItemRemoved(postion);
            suggestions.remove(r);
        }
    }

    public List<WrapLocation> getSuggestions() {
        return suggestions;
    }

    public void setMaxSuggestionsCount(int maxSuggestionsCount) {
        this.maxSuggestionsCount = maxSuggestionsCount;
    }

    public int getMaxSuggestionsCount() {
        return maxSuggestionsCount;
    }

    private void resetOptionalLocations() {
        optionalLocations.clear();
        getOptionalLocations();
    }

    void resetDropDownLocations() {
        clearSuggestions();
        addOptionalLocationsToDropDown();
    }

    private void addOptionalLocationsToDropDown(){
        this.suggestions = optionalLocations;
        notifyDataSetChanged();
    }

    private List<WrapLocation> getOptionalLocations(){

        if(includeGPS) {
            optionalLocations.add(new WrapLocation(GPS));
        }

        return optionalLocations;
    }

    void reset() {
        //resetSearchTerm();
        resetOptionalLocations();
        resetDropDownLocations();
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

            /*text = (TextView) itemView.findViewById(R.id.text);
            //iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(v -> {
                v.setTag(getSuggestions().get(getAdapterPosition()));
                listener.OnItemClickListener(getAdapterPosition(), v);
            });*/
        }

        @OnClick(R.id.text)
        public void onItemClick(){
            WrapLocation location = getSuggestLocationAtPosition(getAdapterPosition());
            onLocationInputAdapterActionListener.OnItemClickListener(location, view);
        }

    }
}
