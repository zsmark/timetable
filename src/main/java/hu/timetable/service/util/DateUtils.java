package hu.timetable.service.util;

import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Created by BEAR on 2017. 06. 15..
 */
public class DateUtils {

    public static String formatDate(Date date){
        StringJoiner joiner = new StringJoiner(" ");
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.HOUR_OF_DAY) > 0) {
                joiner.add("" + cal.get(Calendar.HOUR_OF_DAY));
                joiner.add("óra");
            }
            if(cal.get(Calendar.MINUTE) > 0){
                joiner.add("" + cal.get(Calendar.MINUTE));
                joiner.add("perc");
            }
        }

        return joiner.toString();
    }
}
