package ru.nehodov.weatherforecast.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class WeatherUtil {

    /**
     * Method rounds temperature up to an integer and if temperature is positive adds plus sign
     *
     * @param tempBefore Text temperature before formatting
     * @return formatted temperature
     */
    public static String formatTemp(String tempBefore) {
        if (null == tempBefore || tempBefore.length() == 0
                || !tempBefore.matches("^-?\\d+\\.?(\\d+)?$")) { //filter for wrong formats
            return "";
        }
        try {
            double temp = Double.parseDouble(tempBefore);
            if (temp > -0.5 && temp < 0.5) {
                return "0";
            } else {
                return String.format(Locale.US, "%+.0f", temp);
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static String formatDate(String unixTime) {
        if (unixTime == null || unixTime.length() == 0 || !unixTime.matches("^\\d*$")) {
            return "";
        }
        Instant instant = Instant.ofEpochSecond(Long.parseLong(unixTime));
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        if (date.equals(now)) {
            return "Today";
        } else if (date.equals(now.plusDays(1))) {
            return "Tomorrow";
        } else {
            return String.format(Locale.US, "%02d %02d %s",
                    date.getDayOfMonth(),
                    date.getMonthValue(),
                    date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US));
        }
    }

    public static String formatTime(String unixTime) {
        if (null == unixTime || unixTime.length() == 0 || !unixTime.matches("^\\d*$")) {
            return "";
        } else {
            return new SimpleDateFormat("HH:mm", Locale.getDefault())
                    .format(new Date(Long.parseLong(unixTime) * 1000));
        }
    }
}
