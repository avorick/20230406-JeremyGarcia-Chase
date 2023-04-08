package com.example.weather.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by jeremy on 2/6/2018.
 * Utility class used to perform device operations. These include hiding keyboards,
 * and converting pixels to Density pixels.
 */
public class DeviceUtils {
    private static final long ANIM_DURATION_SHORT_200 = 200; // milliseconds
    private static InputMethodManager imm;

    /**
     * Method is used to show virtual keyboard
     *
     * @param context Interface to global information about an application environment
     */
    public static void showKeyboard(Context context) {
        if (Utils.checkIfNull(imm)) {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        // toggleSoftInput is not consistent when called multiple times back to back
        // The work around is to add a slight delay for consistently displaying soft keyboard
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }, ANIM_DURATION_SHORT_200);
    }

    /**
     * Method is used to hide virtual keyboard
     *
     * @param context Interface to global information about an application environment
     * @param binder  Base interface for a removable object, the core part of a lightweight remote
     *                procedure call mechanism designed for high performance when performing
     *                in-process and cross-process calls
     */
    public static void hideKeyboard(Context context, IBinder binder) {
        if (Utils.checkIfNull(imm)) {
            imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        imm.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * Method is used to convert dp to px
     *
     * @param px      The pixel value to convert to dp
     * @param context Interface to global information about an application environment
     * @return Converted dp value
     */
    public static float convertPixelToDp(Context context, final float px) {
        return !Utils.checkIfNull(px / context.getResources().getDisplayMetrics().density) ?
                (px / context.getResources().getDisplayMetrics().density) : 0f;
    }

    /**
     * Method is used to convert pixels to dp
     *
     * @param dp      The dp value to convert to pixel
     * @param context Interface to global information about an application environment
     * @return Converted pixel value
     */
    public static float convertDpToPixels(Context context, final float dp) {
        return !Utils.checkIfNull(dp * context.getResources().getDisplayMetrics().density) ?
                (dp * context.getResources().getDisplayMetrics().density) : 0f;
    }

    /**
     * Method is used to get the device width in pixels
     *
     * @return Return the current display metrics (Width) that are in effect for this resource object
     */
    public static int getDeviceWidthPx() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * Method is used to get the device height in pixels
     *
     * @return Return the current display metrics (Height) that are in effect for this resource object
     */
    public static int getDeviceHeightPx() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * Method is used to get the status bar height in pixels
     *
     * @return Return the current display metrics (Height) that are in effect for this resource object
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resource > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resource);
        }

        return statusBarHeight;
    }
}
