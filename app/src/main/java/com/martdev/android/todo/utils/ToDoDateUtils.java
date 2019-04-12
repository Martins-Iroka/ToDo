package com.martdev.android.todo.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class ToDoDateUtils {

    private static long elapsedDaysSinceEpoch(long date) {
        return TimeUnit.MILLISECONDS.toDays(date);
    }

    public static String getReadableDate(long datePicker) {

        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int day = ca.get(Calendar.DAY_OF_MONTH);

        Date date = new GregorianCalendar(year, month, day).getTime();

        long currentDate = elapsedDaysSinceEpoch(date.getTime());

        long daysAfterToday = elapsedDaysSinceEpoch(datePicker);

        int actualDate = (int) (daysAfterToday - currentDate);

        switch (actualDate) {
            case 0:
                return "Today";
            case 1:
                return "Tomorrow";
            default:
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d, MMMM, yyyy", Locale.getDefault());
                return dateFormat.format(datePicker);
        }
    }
}
