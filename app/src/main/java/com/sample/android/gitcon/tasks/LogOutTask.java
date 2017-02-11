package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.orm.SugarContext;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;

public class LogOutTask extends SimpleTask<Void, Void> {

    // variables
    private Context mContext;

    // constructors
    public LogOutTask(Context context, SimpleTask.SimpleCallback<Void> callback) {
        super(callback);

        this.mContext = context;
    }

    // methods
    @Override
    protected Void doInBackground(Void... params) {
        mContext.deleteDatabase("sugar.db");
        SugarContext.init(mContext);
        AppPreferences.logOut(mContext);

        return null;
    }

    @Override
    protected String getErrorMessage() {
        return null;
    }
}
