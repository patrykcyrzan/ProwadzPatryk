package pl.cyrzan.prowadzpatryk.ui.common.views.dateandtimeview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import pl.cyrzan.prowadzpatryk.R;

import java.text.DateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.text.format.DateFormat.getDateFormat;
import static android.text.format.DateFormat.getTimeFormat;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Patryk on 09.03.2017.
 */

public class TimeAndDateView extends LinearLayout implements OnTimeClickListener, OnDateClickListener{

    private final static String TAG = "TimeAndDateView";

    private static final String DATE = "date";
    private static final String NOW = "now";
    private static final String TODAY = "today";
    private Calendar calendar;
    private boolean now = true, today = true;

    @BindView(R.id.date_line_layout)
    LinearLayout dateLine;
    @BindView(R.id.time_line_layout)
    LinearLayout timeLine;
    @BindView(R.id.time_text)
    TextView time;
    @BindView(R.id.date_text)
    TextView date;

    public TimeAndDateView(Context context, AttributeSet attr){
        super(context, attr);

        if(!isInEditMode()) {
            setOrientation(LinearLayout.HORIZONTAL);
            init(context, attr);
        }

    }

    private void init(Context context, AttributeSet attr){
        View.inflate(context, R.layout.time_and_date_card, this);
        ButterKnife.bind(this);

        reset();
    }

    private void resetTime() {
        LocalDateTime localDateTime = new LocalDateTime();

        calendar.set(HOUR_OF_DAY, localDateTime.getHourOfDay());
        calendar.set(MINUTE, localDateTime.getMinuteOfHour());

        now = true;
        time.setText(getTimeString());
    }

    private void resetDate() {
        LocalDateTime localDateTime = new LocalDateTime();

        calendar.set(YEAR, localDateTime.getYear());
        calendar.set(MONTH, localDateTime.getMonthOfYear());
        calendar.set(DAY_OF_MONTH, localDateTime.getDayOfMonth());

        today = true;
        date.setText(getDateString());
    }

    public void reset() {
        calendar = Calendar.getInstance();
        now = true;
        today = true;
        updateTexts();
    }

    private void updateTexts() {
        time.setText(getTimeString());
        date.setText(getDateString());
    }

    private String getTimeString() {
        if (now) {
            return getContext().getString(R.string.now);
        }
        LocalTime time = new LocalTime(calendar);
        return time.toString("HH:mm");
    }

    private String getDateString() {
        if(today) {
            return getContext().getString(R.string.today);
        }
        LocalDate date = new LocalDate(calendar);
        return date.toString();
    }

    public DateTime getDateTime(){
        return new DateTime(calendar);
    }

    @OnClick(R.id.time_line_layout)
    public void onTimeLineClick(){
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnTimeSetListener(this);
        if(now) resetTime();
        newFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(),"timePicker");
    }

    @OnClick(R.id.date_line_layout)
    public void onDateLineClick(){
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSetListener(this);
        if(now) resetDate();
        newFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void resetTimeFromDialog() {
        reset();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        calendar.set(HOUR_OF_DAY, hourOfDay);
        calendar.set(MINUTE, minute);

        Calendar c1 = Calendar.getInstance();
        c1.set(HOUR_OF_DAY, hourOfDay);
        c1.set(MINUTE, minute);
        Calendar c2 = Calendar.getInstance();
        now = MILLISECONDS.toMinutes(Math.abs(c1.getTimeInMillis() - c2.getTimeInMillis())) < 10;

        updateTexts();
    }

    @Override
    public void resetDateFromDialog() {
        resetDate();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendar.set(YEAR, year);
        calendar.set(MONTH, month);
        calendar.set(DAY_OF_MONTH, day);

        Calendar c = Calendar.getInstance();
        today = c.get(YEAR) == year && c.get(MONTH) == month && c.get(DAY_OF_MONTH) == day;
        updateTexts();
    }

    public static class TimePickerFragment extends DialogFragment {

        OnTimeClickListener listener;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LocalDateTime localDateTime = new LocalDateTime();

            int hour = localDateTime.getHourOfDay();
            int minute = localDateTime.getMinuteOfHour();
            TimePickerDialog tpd = new TimePickerDialog(getActivity(), listener, hour, minute,
                    android.text.format.DateFormat.is24HourFormat(getActivity()));
            tpd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), tpd);
            tpd.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.now), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.resetTimeFromDialog();
                }
            });
            tpd.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), tpd);
            return tpd;
        }

        public void setOnTimeSetListener(OnTimeClickListener listener) {
            this.listener = listener;
        }
    }

    public static class DatePickerFragment extends DialogFragment {

        OnDateClickListener listener;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LocalDateTime localDateTime = new LocalDateTime();

            int year = localDateTime.getYear();
            int month = localDateTime.getMonthOfYear();
            int day = localDateTime.getDayOfMonth();
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), listener, year, month, day);
            dpd.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), dpd);
            dpd.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.now), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.resetDateFromDialog();
                }
            });
            dpd.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), dpd);
            return dpd;
        }

        public void setOnDateSetListener(OnDateClickListener listener) {
            this.listener = listener;
        }
    }
}

interface OnTimeClickListener extends TimePickerDialog.OnTimeSetListener {
    public void resetTimeFromDialog();
}

interface OnDateClickListener extends DatePickerDialog.OnDateSetListener {
    public void resetDateFromDialog();
}
