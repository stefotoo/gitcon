package com.sample.android.gitcon.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    // constants
    public static final String BASIC_TAG = TimeUtil.class.getName();

    public static final int ONE_SECOND_IN_MILLIS = 1000;
    public static final int ONE_MINUTE_IN_MILLIS = 60 * ONE_SECOND_IN_MILLIS;
    public static final int ONE_HOUR_IN_MILLIS = 60 * ONE_MINUTE_IN_MILLIS;
    public static final int ONE_DAY_IN_MILLIS = 24 * ONE_HOUR_IN_MILLIS;

    public static final String DATE_PATTERN_WIDGET_LAST_UPDATE = "HH:mm dd/MM/yyyy";
    public static final String DATE_PATTERN_NEWS = "dd/MM/yyyy HH:mm";

    public static final long SYNCHRONIZATION_INTERVAL = 60 * 60 * 1000; // 1 hour in millis

    // methods
    public static String getFormattedDate(Calendar cal, String pattern) {
        return DateFormat.format(pattern, cal)
                .toString();
    }

    public static String getFormattedDate(long timeInMillis, String pattern) {
        return DateFormat.format(pattern, timeInMillis)
                .toString();
    }

    public static String getFormattedDate(Date date, String pattern) {
        return DateFormat.format(pattern, date)
                .toString();
    }

    public static String getPrettyDate(long millis, String todayStr) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(millis);

        Calendar today = Calendar.getInstance();

        if (isToday(date, today)) {
            return todayStr + ", " + getFormattedDate(date, "kk:mm");
        } else {
            String dateStr = "MMM d";

            if (date.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
                dateStr += ", yyyy";
            }

            return getFormattedDate(date, dateStr + ", kk:mm");
        }
    }

    public static String getMessagesFormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);

        if (isToday(calendar, today)) {
            return getFormattedDate(date, "hh:mm a");
        } else if (isYesterday(calendar, yesterday)) {
            return "Yesterday";
        } else if (isThisWeek(calendar, today)) {
            return DateFormat.format("E", calendar)
                    .toString();
        } else if (isThisYear(calendar, today)) {
            return getFormattedDate(calendar, "d MMM");
        } else {
            return getFormattedDate(calendar, "d MMM yyyy");
        }
    }

    public static boolean isToday(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isYesterday(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isThisWeek(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean isThisYear(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }
}
