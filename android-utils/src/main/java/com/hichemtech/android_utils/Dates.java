package com.hichemtech.android_utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("unused")
public abstract class Dates {

    public static final Locale APP_LOCAL = Locale.ROOT;
    public static final SimpleDateFormat phpDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Dates.APP_LOCAL);

    public static String getCurrentDate() {
        Date currentTime = Calendar.getInstance().getTime();
        return getStringFromDate(currentTime);
    }

    public static String getCurrentDate(SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date());
    }

    public static Date getCurrentDateAsDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date editDate(Date date_, int yearAdded, int monthAdded, int dayAdded) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Dates.APP_LOCAL);
        Calendar today = Calendar.getInstance();
        today.clear();
        today.setTime(date_);
        Date date;
        today.add(Calendar.DATE, dayAdded);
        today.add(Calendar.YEAR, yearAdded);
        today.add(Calendar.MONTH, yearAdded);
        date = today.getTime();
        return date;
    }

    public static String getCurrentDateAndTime() {
        return getCurrentDate(phpDateFormat);
    }

    public static String getStringFromDate(Date date) {
        try {
            return DateFormat.format("yyyy-MM-dd", date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromTime(Date date) {
        try {
            return DateFormat.format("HH:ii", date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromTimestamp(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.ROOT);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format("yyyy-MM-dd", cal).toString();
    }

    public static Date nextOrPreviousDay(Date date, int added)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, added); //minus number would decrement the days
        return cal.getTime();
    }

    @Deprecated
    public static int[] getHoursAndMinutesFromTimeString(String timeString) {
        if (timeString.length() == 5) {
            if (timeString.contains(":")) {
                try {
                    List<String> timeStringParts = Arrays.asList(timeString.split(":"));
                    int h, m;
                    try {
                        h = Integer.parseInt(timeStringParts.get(0));
                        m = Integer.parseInt(timeStringParts.get(1));
                        return new int[]{h, m};
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new int[]{-1};
    }

    public static String getTimeAgoStringFromDateString(String dateStr, Context context) {
        Date date = null;
        if (dateStr == null) return context.getString(R.string.err_unknown_null_value_display);
        try {
            date = phpDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return context.getString(R.string.err_unknown_null_value_display);
        return (String) DateUtils.getRelativeTimeSpanString(date.getTime() , Calendar.getInstance().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS);
    }
}
