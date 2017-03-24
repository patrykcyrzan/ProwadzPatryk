package pl.cyrzan.prowadzpatryk.ui.trips;

import com.google.android.flexbox.FlexboxLayout;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import pl.cyrzan.prowadzpatryk.R;
import pl.cyrzan.prowadzpatryk.model.Leg;
import pl.cyrzan.prowadzpatryk.model.ListTrip;
import pl.cyrzan.prowadzpatryk.model.Response;
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

        holder.lines.removeAllViews();

        int i = 0;
        for(final Leg leg : trip.getItinerary().legs) {
            if(i == 0)
                bindLeg(context, holder.legs.get(i), leg, false, holder.lines, true, false, trip);
            else if(i == (trip.getItinerary().legs.size()-1))
                bindLeg(context, holder.legs.get(i), leg, false, holder.lines, false, true, trip);
            else
                bindLeg(context, holder.legs.get(i), leg, false, holder.lines, false, false, trip);
            i += 1;
        }

        holder.legs.get(trip.getItinerary().legs.size() - 1).divider.setVisibility(View.GONE);

        /*if(trip.getItinerary().transfers != null && trip.getItinerary().transfers > 1) {

            holder.changes.setText(String.valueOf(trip.getItinerary().transfers));
            holder.changes.setVisibility(View.VISIBLE);
        } else {
            holder.changes.setVisibility(View.GONE);
        }*/

        holder.changes.setText(String.valueOf(trip.getItinerary().transfers));
        holder.duration.setText(TimeUtil.getDuration(trip.getItinerary().duration));

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
        } else if (isLast){
            leg_holder.departureLocation.setText(leg.getFrom().name);
            leg_holder.arrivalLocation.setText(trip.getTo().uniqueShortName());
        } else  {
            leg_holder.departureLocation.setText(leg.getFrom().name);
            leg_holder.arrivalLocation.setText(leg.getTo().name);
        }

        leg_holder.departureLocation.setBackgroundResource(android.R.color.transparent);
        leg_holder.arrivalLocation.setBackgroundResource(android.R.color.transparent);

        leg_holder.duration.setText(TimeUtil.getDuration(leg.getDuration()));

        //Log.i(TAG, "TIME: "+TimeUtils.string2Time(context, leg.getStartTime()));

        if(leg.getStartTime() != 0L){
            leg_holder.departureTime.setText(TimeUtil.getDateStringFromMilis(leg.getStartTime()));
            leg_holder.arrivalTime.setText(TimeUtil.getDateStringFromMilis(leg.getEndTime()));
        }

        leg_holder.line.removeViewAt(0);
        addLineBox(leg_holder.line, leg, 0);
        if(lines != null) addLineBox(lines, leg, lines.getChildCount());
    }

    private void addLineBox(ViewGroup lineLayout, Leg leg, int index){
        LineView lineView = new LineView(context, null);
        lineView.setLeg(leg);

        FlexboxLayout.LayoutParams lp = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 15, 5);
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

        ui.lineView.setVisibility(ostate);

        if(ui.legs.size() <= 1) {
            ui.legs.get(0).info.setVisibility(state);

            return;
        }

        int i = 0;
        for(LegHolder leg : ui.legs) {
            if(i == 0) {
                // first leg
                leg.arrival.setVisibility(state);
                leg.info.setVisibility(state);
                leg.divider.setVisibility(state);
            } else if(i == ui.legs.size() - 1) {
                // last leg
                leg.departure.setVisibility(state);
                leg.info.setVisibility(state);
            } else {
                // all middle legs
                leg.arrival.setVisibility(state);
                leg.info.setVisibility(state);
                leg.departure.setVisibility(state);
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
        @BindView(R.id.legsView)
        ViewGroup legsView;
        public List<LegHolder> legs;

        public BaseTripHolder(View itemView, int size) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            legs = new ArrayList<>();

            for(int i = 0; i < size; i++) {
                ViewGroup legView = (ViewGroup) LayoutInflater.from(itemView.getContext()).inflate(R.layout.leg, legsView, false);
                legsView.addView(legView);

                LegHolder legHolder = new LegHolder(legView);
                legs.add(legHolder);
            }
        }
    }

    static class TripHolder extends BaseTripHolder {

        @BindView(R.id.firstLegView)
        TableLayout firstLeg;
        @BindView(R.id.expandView)
        ImageView expandImage;
        @BindView(R.id.mapView)
        ImageView mapImage;
        private FlexboxLayout lines;
        private TextView changes;
        private TextView duration;

        private LinearLayout lineView;

        public TripHolder(View itemView, int size) {
            super(itemView, size-1);
            ButterKnife.bind(this, itemView);

            LayoutTransition transition = new LayoutTransition();
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                transition.enableTransitionType(LayoutTransition.CHANGING);
                transition.enableTransitionType(LayoutTransition.APPEARING);
                transition.enableTransitionType(LayoutTransition.CHANGE_APPEARING);
                transition.enableTransitionType(LayoutTransition.DISAPPEARING);
                transition.enableTransitionType(LayoutTransition.CHANGE_DISAPPEARING);
            }
            legsView.setLayoutTransition(transition);

            LegHolder firstLegHolder = new LegHolder(firstLeg);
            legs.add(0, firstLegHolder);

            lineView = (LinearLayout) LayoutInflater.from(itemView.getContext()).inflate(R.layout.line, firstLeg, false);
            lines = (FlexboxLayout) lineView.findViewById(R.id.lineLayout);
            changes = (TextView) lineView.findViewById(R.id.changesView);
            duration = (TextView) lineView.findViewById(R.id.durationView);

            firstLeg.addView(lineView, 1);
        }
    }

    static class LegHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.departureView)
        ViewGroup departure;
        @BindView(R.id.departureTimeView)
        TextView departureTime;
        @BindView(R.id.departureLocationView)
        TextView departureLocation;

        @BindView(R.id.arrivalView)
        ViewGroup arrival;
        @BindView(R.id.arrivalTimeView)
        TextView arrivalTime;
        @BindView(R.id.arrivalLocationView)
        TextView arrivalLocation;

        @BindView(R.id.infoView)
        ViewGroup info;
        @BindView(R.id.lineView)
        ViewGroup line;
        @BindView(R.id.lineDestinationView)
        TextView lineDestination;
        @BindView(R.id.durationView)
        TextView duration;
        @BindView(R.id.dividerView)
        View divider;


        public LegHolder(ViewGroup itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
