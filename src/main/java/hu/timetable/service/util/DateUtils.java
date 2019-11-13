package hu.timetable.service.util;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public class DateUtils {

    public static final int MINUTE = 60;

    public static String formatDate(int timeInMinutes) {
        StringJoiner joiner = new StringJoiner(" ");
        if (timeInMinutes > MINUTE) {
            joiner.add("" + timeInMinutes / MINUTE);
            joiner.add("óra");
        }
        joiner.add("" + timeInMinutes % MINUTE);
        joiner.add("perc");

        return joiner.toString();
    }

    public static LocalDateTime getLocalDateTimeFromTimeInMinutes(Integer timeInMinutes) {
        LocalDateTime time = LocalDateTime.now();
        if (timeInMinutes > MINUTE) {
            time = time.withHour(timeInMinutes / MINUTE);
        }
        return time.withMinute(timeInMinutes % MINUTE);
    }
}
