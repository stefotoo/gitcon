package com.sample.android.gitcon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.services.SynchronizeUserService;

public class LoadActivity extends AppCompatActivity {

    // constants
    private static final String BASIC_TAG = LoadActivity.class.getName();

    // get intent methods
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoadActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncUser();
        goToNextScreen();
        overridePendingTransition(0,0);
        finish();
    }

    private void syncUser() {
        startService(SynchronizeUserService.getIntent(this));
    }

    private void goToNextScreen() {
        if (AppPreferences.hasUser(this)) {
            goToUserDetailsActivity();
        } else {
            goToLoginActivity();
        }
    }

    private void goToLoginActivity() {
        startActivity(LoginActivity.getIntent(this));
    }

    private void goToUserDetailsActivity() {
        startActivity(UserDetailsActivity.getIntent(this, null));
    }
}
