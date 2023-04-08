package com.example.weather.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.weather.R;

/**
 * Created by jeremy on 1/30/2018.
 * Utility class used to display a progress dialog that prevents the user from performing actions
 * while the dialog is showing.
 */
public class DialogUtils {
    private static ProgressDialog mProgressDialog; // display during processing requests/responses

    /**
     * Method is used to dismiss progress dialog
     */
    public static void dismissProgressDialog() {
        try {
            if (!Utils.checkIfNull(mProgressDialog) && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (@NonNull IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is used to display progress dialog. Call when processing requests/responses
     *
     * @param context Interface to global information about an application environment
     */
    public static void showProgressDialog(@NonNull Context context) {
        if (((Activity) context).isFinishing() || (!Utils.checkIfNull(mProgressDialog) && mProgressDialog.isShowing())) {
            return;
        }

        try {
            mProgressDialog = ProgressDialog.show(context, null, null, true, false);
            if (!Utils.checkIfNull(mProgressDialog.getWindow())) {
                mProgressDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context, R.color.transparent));
                mProgressDialog.setContentView(R.layout.dialog_progress);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}
