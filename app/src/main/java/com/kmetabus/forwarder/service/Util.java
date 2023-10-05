package com.kmetabus.forwarder.service;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Util {

    public static void alert(Context context, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

}
