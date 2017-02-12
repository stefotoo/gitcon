package com.sample.android.gitcon.activities.abstracts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.models.eventbus.OnUserUpdatedEvent;
import com.sample.android.gitcon.preferences.AppPreferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class AActivity extends AppCompatActivity {

    // constants
    public static final String BASIC_TAG = AActivity.class.getName();

    // variables
    private EventBus mBus;
    protected AUser mLoggedUser;

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onDestroy() {
        if (mBus != null) {
            mBus.unregister(this);
        }

        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OnUserUpdatedEvent event) {
        if (event != null) {
            mLoggedUser = event.getUser();
            updateUiOnUserUpdate(event.getUser());
        }
    }

    private void init() {
        mLoggedUser = AppPreferences.getUser(this);
        mBus = EventBus.getDefault();
        mBus.register(this);
    }

    protected abstract void updateUiOnUserUpdate(AUser user);

}
