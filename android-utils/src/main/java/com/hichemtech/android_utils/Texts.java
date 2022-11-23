package com.hichemtech.android_utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

public abstract class Texts {
    public static Boolean copyStringToClipboard(String stringToCopy, Context context) {
        try {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied text", stringToCopy);
            clipboard.setPrimaryClip(clip);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean shareString(String shareIntentTitle, String stringToCopy, Context context) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, stringToCopy);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, shareIntentTitle);
            context.startActivity(shareIntent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}
