package com.sample.android.gitcon.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.models.eventbus.OnUserUpdatedEvent;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.utils.Util;

import org.greenrobot.eventbus.EventBus;

public class SynchronizeUserService extends IntentService {

    // constants
    public static final String BASIC_TAG = SynchronizeUserService.class.getName();

    // get intent methods
    public static Intent getIntent(Context context) {
        return new Intent(context, SynchronizeUserService.class);
    }

    // constructor
    public SynchronizeUserService() {
        super(BASIC_TAG);
    }

    // methods
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!AppPreferences.hasUser(this) || !Util.hasConnection(this)) {
            return;
        }

        AUser currUser = AppPreferences.getUser(this);

        try {
            AUser user = new RestClient().getAuthRestService().getUserDetails(currUser.getLogin());

            if (!currUser.equals(user)) {
                AppPreferences.setUser(this, user);
                EventBus.getDefault().post(new OnUserUpdatedEvent(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
