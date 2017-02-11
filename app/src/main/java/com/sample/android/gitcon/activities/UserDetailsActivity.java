package com.sample.android.gitcon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.sample.android.gitcon.R;
import com.sample.android.gitcon.adapters.RepositoriesAdapter;
import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.models.User;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.tasks.GetUserDetailsApiTask;
import com.sample.android.gitcon.tasks.GetUserReposApiTask;
import com.sample.android.gitcon.tasks.LogOutTask;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;
import com.sample.android.gitcon.ui.DividerItemDecoration;
import com.sample.android.gitcon.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailsActivity
        extends AppCompatActivity
        implements RepositoriesAdapter.FollowingFollowersClickListener {

    // constants
    public static final String BASIC_TAG = UserDetailsActivity.class.getName();

    public static final String EXTRA_USER = "user";

    // variables
    private String mUserAsJson;
    private AUser mUser;
    private RepositoriesAdapter mAdapter;

    // Ui
    @Bind(R.id.toolbar_activity_user_details)
    Toolbar toolbar;
    @Bind(R.id.rv_activity_user_details)
    RecyclerView rv;

    // get intent methods
    public static Intent getIntent(Context context, AUser user) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(EXTRA_USER, new Gson().toJson(user));

        return intent;
    }

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        ButterKnife.bind(this);
        initExtras();
        initVariables();
        setupToolbar();
        setupRecyclerView();
        getRepositories();

        if (Util.isStringNotNull(mUserAsJson) && !mUserAsJson.equals("null")) {
            executeUserDetailsRequest();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.item_logout:
                logOut();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initExtras() {
        mUserAsJson = getIntent().getStringExtra(EXTRA_USER);
    }

    private void initVariables() {
        mUser = (Util.isStringNotNull(mUserAsJson) && !mUserAsJson.equals("null")) ?
                new Gson().fromJson(mUserAsJson, AUser.class) :
                AppPreferences.getUser(this);
        Picasso picasso = Picasso.with(this);
        mAdapter = new RepositoriesAdapter(this, picasso, mUser, this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (mUser != null && Util.isStringNotNull(mUser.getName())) {
            getSupportActionBar().setTitle(mUser.getName());
        }
    }

    private void setupRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this));
        rv.setAdapter(mAdapter);
    }

    private void getRepositories() {
        new GetUserReposApiTask(
                this,
                mUser.getLogin(),
                new SimpleTask.SimpleCallback<List<Repository>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(List<Repository> res, String errorMessage) {
                if (Util.isListNotEmpty(res)) {
                    mAdapter.addData(res, true);
                }
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void executeUserDetailsRequest() {
        new GetUserDetailsApiTask(
                this,
                mUser.getLogin(),
                new SimpleTask.SimpleCallback<AUser>() {
                    @Override
                    public void onStart() {
                        // TODO
                    }

                    @Override
                    public void onComplete(AUser res, String errorMessage) {
                        if (res != null) {
                            mUser = res;
                            mAdapter.updateUser(mUser, true);
                        }
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void logOut() {
        new LogOutTask(this, new SimpleTask.SimpleCallback<Void>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(Void res, String errorMessage) {
                startActivity(LoginActivity.getIntent(UserDetailsActivity.this));
                finish();
            }
        }).execute();
    }

    @Override
    public void onFollowersClick() {
        startActivity(UsersListActivity.getIntent(
                this,
                UsersListActivity.FOLLOWERS_LIST,
                mUser.getLogin()));
    }

    @Override
    public void onFollowingClick() {
        startActivity(UsersListActivity.getIntent(
                this,
                UsersListActivity.FOLLOWING_LIST,
                mUser.getLogin()));
    }
}
