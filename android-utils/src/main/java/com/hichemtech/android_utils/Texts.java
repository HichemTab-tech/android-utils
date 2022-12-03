package com.hichemtech.android_utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;

import java.util.Random;
import java.util.UUID;

@SuppressWarnings("unused")
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

    public static String generateUniqueId() {
        return generateUniqueId("");
    }

    public static String generateUniqueId(String... prefixes) {
        String string = UUID.randomUUID().toString();
        String prefix = "";
        for (String s : prefixes) {
            prefix = prefix.concat(replaceSpecialCharacter(s));
            if (!prefix.equals("")) {
                prefix = prefix.concat("_");
            }
        }
        string = prefix.concat(string);
        return string;
    }

    public static String replaceSpecialCharacter(String str) {
        return str.replaceAll("[;/:*?\"<>|&']","");
    }
}
