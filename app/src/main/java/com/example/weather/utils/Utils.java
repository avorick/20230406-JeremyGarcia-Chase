package com.example.weather.utils;

import android.os.SystemClock;

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

}
