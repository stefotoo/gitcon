package com.sample.android.gitcon;

import android.app.Application;

import com.orm.SugarContext;

public class GitconApp extends Application {

    // methods
    @Override
    public void onCreate() {
        super.onCreate();

        initDb();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }

    private void initDb() {
        SugarContext.init(this);
    }
}
