package pl.cyrzan.prowadzpatryk.ui.trips;

import com.google.android.flexbox.FlexboxLayout;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Leg;
import pl.cyrzan.prowadzpatryk.model.ListTrip;
import pl.cyrzan.prowadzpatryk.model.enums.TraverseMode;
import pl.cyrzan.prowadzpatryk.ui.common.views.LineView;
import pl.cyrzan.prowadzpatryk.util.MainUtil;
import pl.cyrzan.prowadzpatryk.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Patryk on 14.03.2017.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripHolder> {

    private static final String TAG = "TripAdapter";

    private Context context;
    private List<ListTrip> trips;
    private final boolean showLineName;
    private OnTripActionListener listener;


    public TripAdapter(List<ListTrip> trips, Context context, boolean showLineName){
        this.context = context;
        this.trips = trips;
        this.showLineName = showLineName;
    }

    @Override
    public int getItemViewType(int position) {
        return trips.get(position).getItinerary().legs.size();
    }

    @Override
    public TripHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.trip, parent, false);

        return new TripHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(TripHolder holder, int position) {
        ListTrip trip = trips.get(holder.getAdapterPosition());

        expandTrip(holder, !trip.isExpanded());

        holder.linesFirstLeg.removeAllViews();

        int i = 0;
        for(final Leg leg : trip.getItinerary().legs) {
            if(i == 0) {
                // first leg
                bindLeg(context, holder.legs.get(i), leg, false, holder.linesFirstLeg, true, false, trip);
                holder.departureFirstLeg.setText(TimeUtil.getDateStringFromMilis(leg.getStartTime()));
                holder.leaveIn.setText(TimeUtil.getLeavInTime(leg.getStartTime()));
            }
            else if(i == (trip.getItinerary().legs.size()-1)) {
                // last leg
                bindLeg(context, holder.legs.get(i), leg, false, holder.linesFirstLeg, false, true, trip);
                holder.arrivalFirstLeg.setText(TimeUtil.getDateStringFromMilis(leg.getEndTime()));
            }
            else
                bindLeg(context, holder.legs.get(i), leg, false, holder.linesFirstLeg, false, false, trip);
            i += 1;
        }

        holder.legs.get(trip.getItinerary().legs.size() - 1).divider.setVisibility(View.GONE);

        holder.changesFirstLeg.setText(String.valueOf(trip.getItinerary().transfers));
        holder.durationFirstLeg.setText(TimeUtil.getDuration(trip.getItinerary().duration));

        if(trip.getItinerary().legs.size() == 1) {
            holder.expandImage.setVisibility(View.GONE);
        } else {
            holder.expandImage.setOnClickListener(view -> {
                expandTrip(holder, trip.isExpanded());
                trip.setExpanded(!trip.isExpanded());
            }
            );
        }
    }

    public void setListener(OnTripActionListener listener){
        this.listener = listener;
    }

    public void addAll(List<ListTrip> trips) {
        Log.i(TAG, " ada "+trips.size());
        this.trips = trips;
        notifyDataSetChanged();
    }

    private void bindLeg(Context context, final LegHolder leg_holder, Leg leg, boolean detail, FlexboxLayout lines, boolean isFirst, boolean isLast, ListTrip trip) {
        // Locations
        if(isFirst){
            leg_holder.departureLocation.setText(trip.getFrom().uniqueShortName());
            leg_holder.arrivalLocation.setText(leg.getTo().name);
            leg_holder.tripShapeView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.start));
            leg_holder.tripShapeView.setScaleType(ImageView.ScaleType.FIT_END);
        } else if (isLast){
            leg_holder.departureLocation.setText(leg.getFrom().name);
            leg_holder.arrivalLocation.setText(trip.getTo().uniqueShortName());
            leg_holder.tripShapeView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.end));
            leg_holder.tripShapeView.setScaleType(ImageView.ScaleType.FIT_START);
        } else  {
            leg_holder.departureLocation.setText(leg.getFrom().name);
            leg_holder.arrivalLocation.setText(leg.getTo().name);
            leg_holder.tripShapeView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.middle));
            leg_holder.tripShapeView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if(leg.getMode() != TraverseMode.WALK) {
            leg_holder.tripShapeView.setColorFilter(MainUtil.getColorForProduct(context, leg.getMode()));
        }

        leg_holder.durationDetail.setText(TimeUtil.getDuration(leg.getDuration()));

        if(leg.getStartTime() != 0L){
            leg_holder.departureTime.setText(TimeUtil.getDateStringFromMilis(leg.getStartTime()));
            leg_holder.arrivalTime.setText(TimeUtil.getDateStringFromMilis(leg.getEndTime()));
        }

        leg_holder.lineDetail.removeViewAt(0);
        addLineBox(leg_holder.lineDetail, leg, 0);
        if(lines != null) addLineBox(lines, leg, lines.getChildCount());
    }

    private void addLineBox(ViewGroup lineLayout, Leg leg, int index){
        LineView lineView = new LineView(context, null);
        lineView.setLeg(leg);

        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 5, 5);
        lineView.setLayoutParams(lp);

        lineLayout.addView(lineView, index);
    }

    private void expandTrip(final TripHolder ui, boolean expand) {
        Drawable icon;
        int state, ostate;

        if(expand) {
            icon = MainUtil.getTintedDrawable(context, R.drawable.ic_action_navigation_unfold_more);
            state = View.GONE;
            ostate = View.VISIBLE;
        }
        else {
            icon = MainUtil.getTintedDrawable(context, R.drawable.ic_action_navigation_unfold_less);
            state = View.VISIBLE;
            ostate = View.GONE;
        }
        ui.expandImage.setImageDrawable(icon);

        ui.firstLegLayout.setVisibility(ostate);

        if(ui.legs.size() <= 1) {
            ui.legs.get(0).info.setVisibility(state);
            return;
        }

        int i = 0;
        for(LegHolder leg : ui.legs) {
            if(i == 0) {
                // first leg
                //leg.arrival.setVisibility(state);
                leg.departureAndArrival.setVisibility(state);
                leg.info.setVisibility(state);
                leg.tripShapeView.setVisibility(state);
                leg.divider.setVisibility(state);
            } else if(i == ui.legs.size() - 1) {
                // last leg
                leg.departureAndArrival.setVisibility(state);
                //leg.arrival.setVisibility(state);
                leg.info.setVisibility(state);
                leg.tripShapeView.setVisibility(state);
            } else {
                // all middle legs
                //leg.arrival.setVisibility(state);
                leg.info.setVisibility(state);
                leg.departureAndArrival.setVisibility(state);
                leg.tripShapeView.setVisibility(state);
                leg.divider.setVisibility(state);
            }
            i += 1;
        }
    }

    @Override
    public int getItemCount() {
        return trips == null ? 0 : trips.size();
    }

    static class BaseTripHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardView)
        CardView card;
        @BindView(R.id.legsViewLayout)
        ViewGroup legsViewLayout;
        public List<LegHolder> legs;

        public BaseTripHolder(View itemView, int size) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            legs = new ArrayList<>();

            for(int i = 0; i < size; i++) {
                ViewGroup legView = (ViewGroup) LayoutInflater.from(itemView.getContext()).inflate(R.layout.leg, legsViewLayout, false);
                legsViewLayout.addView(legView);

                LegHolder legHolder = new LegHolder(legView);
                legs.add(legHolder);
            }
        }
    }

    class TripHolder extends BaseTripHolder {

        @BindView(R.id.expandView)
        ImageView expandImage;
        @BindView(R.id.mapView)
        ImageView mapImage;
        @BindView(R.id.lineLayout)
        FlexboxLayout linesFirstLeg;
        @BindView(R.id.changesView)
        TextView changesFirstLeg;
        @BindView(R.id.durationView)
        TextView durationFirstLeg;
        @BindView(R.id.leaveInTimeView)
        TextView leaveIn;
        @BindView(R.id.departureTimeViewFirstLeg)
        TextView departureFirstLeg;
        @BindView(R.id.arrivalTimeViewFirstLeg)
        TextView arrivalFirstLeg;
        @BindView(R.id.firstLegLayout)
        LinearLayout firstLegLayout;


        public TripHolder(View itemView, int size) {
            super(itemView, size);
            ButterKnife.bind(this, itemView);

            LayoutTransition transition = new LayoutTransition();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                transition.enableTransitionType(LayoutTransition.CHANGING);
                transition.enableTransitionType(LayoutTransition.APPEARING);
                transition.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
                transition.enableTransitionType(LayoutTransition.DISAPPEARING);
                transition.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            }
            legsViewLayout.setLayoutTransition(transition);
        }

        @OnClick(R.id.mapView)
        public void onMapClick(){
            listener.onShowMapClickAction(trips.get(getAdapterPosition()));
        }
    }

    static class LegHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.departureAndArrivalLayout)
        ViewGroup departureAndArrival;
        @BindView(R.id.departureTimeView)
        TextView departureTime;
        @BindView(R.id.departureLocationView)
        TextView departureLocation;

        /*@BindView(R.id.arrivalLayout)
        ViewGroup arrival;*/
        @BindView(R.id.arrivalTimeView)
        TextView arrivalTime;
        @BindView(R.id.arrivalLocationView)
        TextView arrivalLocation;

        @BindView(R.id.infoView)
        ViewGroup info;
        @BindView(R.id.lineDetailView)
        ViewGroup lineDetail;
        @BindView(R.id.durationDetailView)
        TextView durationDetail;

        @BindView(R.id.trip_shape)
        ImageView tripShapeView;
        @BindView(R.id.border)
        View divider;


        public LegHolder(ViewGroup itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnTripActionListener {
        void onShowMapClickAction(ListTrip trip);
    }
}
