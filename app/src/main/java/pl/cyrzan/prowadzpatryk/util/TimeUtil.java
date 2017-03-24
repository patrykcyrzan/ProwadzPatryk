package pl.cyrzan.prowadzpatryk.util;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Patryk on 20.03.2017.
 */

public final class TimeUtil {

    static public String getDuration(long duration) {
        long minutes = TimeUnit.SECONDS.toMinutes(duration);

        long m = minutes % 60;
        long h = minutes / 60;

        if(h==0){
            return Long.toString(m) + " min";
        } else {
            return Long.toString(h) + ":" + (m < 10 ? "0" : "") + Long.toString(m) + " godz.";
        }
    }

    static public String getDateStringFromMilis(long milliseconds){
        DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
        DateTime dateTime = new DateTime(milliseconds);

        return dtf.print(dateTime);
    }
}
