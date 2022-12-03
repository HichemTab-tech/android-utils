package com.hichemtech.android_utils;

import android.content.Context;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

@SuppressWarnings("unused")
public abstract class Alerts {
    public static void showErrorCausedByVariables(ViewGroup containerView, Context context) {
        showSimpleAlert(context.getString(R.string.errorText_UnkonwnError), containerView, context);
    }

    public static void showSimpleAlert(String err, ViewGroup containerView, Context context) {
        Snackbar.make(containerView, err, Integer.parseInt(context.getString(R.string.timeout_snackbar_error)))
                .show();
    }

    public static void showAlertSuccessQuick(String err, ViewGroup containerView, Context context) {
        Snackbar.make(containerView, err, Integer.parseInt(context.getString(R.string.timeout_snackbar_small_success)))
                .setBackgroundTint(context.getResources().getColor(R.color.colorSuccess))
                .show();
    }

    public static void showAlertErrorQuick(String err, ViewGroup containerView, Context context) {
        Snackbar.make(containerView, err, Integer.parseInt(context.getString(R.string.timeout_snackbar_small_success)))
                .setBackgroundTint(context.getResources().getColor(R.color.colorDanger))
                .show();
    }

    public static void showAlertReCaptchaRequired(ViewGroup containerView, Context context) {
        Snackbar.make(containerView, context.getString(R.string.errorText_ReCAPTCHARequired), Integer.parseInt(context.getString(R.string.timeout_snackbar_small_success)))
                .setBackgroundTint(context.getResources().getColor(R.color.colorDanger))
                .show();
    }

    public static void showAlertReCaptchaError(ViewGroup containerView, Context context) {
        Snackbar.make(containerView, context.getString(R.string.errorText_ReCAPTCHAFailed), Integer.parseInt(context.getString(R.string.timeout_snackbar_small_success)))
                .setBackgroundTint(context.getResources().getColor(R.color.colorDanger))
                .show();
    }

    public static void showAlertNoAppCanOpenFile(ViewGroup containerView, Context context) {
        Snackbar.make(containerView, context.getString(R.string.errorText_noAppCanOpenFile), Integer.parseInt(context.getString(R.string.timeout_snackbar_error)))
                .setBackgroundTint(context.getResources().getColor(R.color.colorWarning))
                .show();
    }

    public static void showAlertComingSoon(ViewGroup containerView, Context context) {
        showSimpleAlert(context.getString(R.string.errorText_featureComingSoon), containerView, context);
    }
}
