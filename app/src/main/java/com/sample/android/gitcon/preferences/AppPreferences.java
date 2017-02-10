package com.sample.android.gitcon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sample.android.gitcon.models.User;

public class AppPreferences {

    // constants
    private static final String BASIC_TAG = AppPreferences.class.getName();

    private static final String KEY_USER = "user";

    // static methods
    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(BASIC_TAG, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return getPref(context).edit();
    }

    /**
     * Attention: This will clear all data!
     */
    private static void clearAll(Context context) {
        getEditor(context).clear().commit();
    }

    public static void logOut(Context context) {
        clearAll(context);
    }

    public static User getUser(Context context) {
        return new Gson().fromJson(getPref(context).getString(KEY_USER, null), User.class);
    }

    public static void setUser(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);

        editor.putString(KEY_USER, new Gson().toJson(user));
        editor.commit();
    }

    public static boolean hasUser(Context context) {
        return getPref(context).contains(KEY_USER);
    }
}
