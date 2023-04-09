package com.example.weather.utils;

import android.os.SystemClock;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jeremy on 1/30/2018.
 * Utility class used to perform null and empty string checks and other generic functions.
 */
public class Utils {
    private static final String EMPTY = "";
    private static final String NULL = "null";

    // click control threshold
    private static final int CLICK_THRESHOLD = 350;
    private static long mLastClickTime;

    /**
     * Method is used to control clicks on views. Clicking views repeatedly and quickly will
     * sometime cause crashes when objects and views are not fully animated or instantiated.
     * This helper method helps minimize and control UI interaction and flow
     *
     * @return True if clicks have not occurred within 300ms window
     */
    public static boolean isViewClickable() {
        /*
         * @Note: Android queues button clicks so it doesn't matter how fast or slow
         * your onClick() executes, simultaneous clicks will still occur. Therefore solutions
         * such as disabling button clicks via flags or conditions statements will not work.
         * The best solution is to timestamp the click processes and return back clicks
         * that occur within a designated window (currently 250 ms)
         */
        long mCurrClickTimestamp = SystemClock.uptimeMillis();
        long mElapsedTimestamp = mCurrClickTimestamp - mLastClickTime;
        mLastClickTime = mCurrClickTimestamp;
        return !(mElapsedTimestamp <= CLICK_THRESHOLD);
    }

    /**
     * Method checks if String value is empty
     *
     * @param str String value to check if null or empty
     * @return True if String value is null or empty
     */
    public static boolean isStringEmpty(String str) {
        return str == null || str.length() == 0 || EMPTY.equals(str.trim()) || NULL.equals(str);
    }

    /**
     * Method is used to check if objects are null
     *
     * @param objectToCheck Object to check if null or empty
     * @param <T>           Generic data value
     * @return True if object is null or empty
     */
    public static <T> boolean checkIfNull(T objectToCheck) {
        return objectToCheck == null;
    }

    /**
     * Method is used to convert a Kelvin double value to a rounded Celsius integer
     *
     * @param kelvin The double input in Kelvin
     * @return The converted Celsius value as a rounded integer
     */
    public static int convertKelvinToCelsius(double kelvin) {
        return (int) Math.round(kelvin - 273.15);
    }

    /**
     * Method is used to convert a Kelvin double value to a rounded Fahrenheit integer
     *
     * @param kelvin The double input in Kelvin
     * @return The converted Fahrenheit value as a rounded integer
     */
    public static int convertKelvinToFahrenheit(double kelvin) {
        return (int) Math.round((kelvin - 273.15) * 1.8 + 32);
    }

    /**
     * Method is used to convert a long timestamp UTC into 12-hour time format local time
     *
     * @param timeInMillis The long timestamp UTC
     * @return The converted 12-hour time String local time
     */
    public static String convertTimeInMillisToTime(long timeInMillis) {
        Date date = new Date(timeInMillis);
        Format format = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
