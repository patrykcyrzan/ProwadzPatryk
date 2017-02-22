package pl.cyrzan.prowadzpatryk.ui.common.views.input;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.service.api.model.SuggestLocationResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Patryk on 19.02.2017.
 */

public class LocationInputAdapter extends RecyclerView.Adapter<LocationInputAdapter.LocationInputViewHolder> {

    private List<SuggestLocationResponse> suggestions = new ArrayList<>();
    private OnLocationInputAdapterActionListener onLocationInputAdapterActionListener;
    protected int maxSuggestionsCount = 5;
    private OnItemViewClickListener listener;

    public LocationInputAdapter(List<SuggestLocationResponse> suggestions, OnLocationInputAdapterActionListener onLocationInputAdapterActionListener){
        this.suggestions = suggestions;
        this.onLocationInputAdapterActionListener = onLocationInputAdapterActionListener;
    }

    @Override
    public LocationInputAdapter.LocationInputViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocationInputAdapter.LocationInputViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_input_item, parent, false));
    }

    @Override
    public void onBindViewHolder(LocationInputAdapter.LocationInputViewHolder holder, int position) {
        holder.text.setText(getSuggestions().get(position).toString());
    }

    /*public void addSuggestion(String r){
        if (maxSuggestionsCount <= 0)
            return;

        if (r == null)
            return;
        if (!suggestions.contains(r))
        {
            if (suggestions.size() >= maxSuggestionsCount) {
                suggestions.remove(maxSuggestionsCount - 1);
            }
            suggestions.add(0, r);
        }else {
            suggestions.remove(r);
            suggestions.add(0, r);
        }
        notifyDataSetChanged();
    }*/

    public void setSuggestions(List<SuggestLocationResponse> suggestions) {
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

    public List<SuggestLocationResponse> getSuggestions() {
        return suggestions;
    }

    public void setMaxSuggestionsCount(int maxSuggestionsCount) {
        this.maxSuggestionsCount = maxSuggestionsCount;
    }

    public int getMaxSuggestionsCount() {
        return maxSuggestionsCount;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class LocationInputViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text)
        TextView text;

        public LocationInputViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            text = (TextView) itemView.findViewById(R.id.text);
            //iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
            itemView.setOnClickListener(v -> {
                v.setTag(getSuggestions().get(getAdapterPosition()));
                listener.OnItemClickListener(getAdapterPosition(), v);
            });
        }



    }

    public interface OnItemViewClickListener{
        void OnItemClickListener(int position,View v);
        void OnItemDeleteListener(int position,View v);
    }
}
