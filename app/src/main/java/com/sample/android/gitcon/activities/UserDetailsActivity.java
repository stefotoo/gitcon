package com.sample.android.gitcon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.sample.android.gitcon.R;
import com.sample.android.gitcon.activities.abstracts.AActivity;
import com.sample.android.gitcon.adapters.RepositoriesAdapter;
import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.tasks.GetUserDetailsApiTask;
import com.sample.android.gitcon.tasks.GetUserReposApiTask;
import com.sample.android.gitcon.tasks.LogOutTask;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;
import com.sample.android.gitcon.ui.DividerItemDecoration;
import com.sample.android.gitcon.ui.GitconProgressDialog;
import com.sample.android.gitcon.utils.CompatibilityUtil;
import com.sample.android.gitcon.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserDetailsActivity
        extends AActivity
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

    private GitconProgressDialog pd;

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
        setupUi();
        getRepositories();
    }

    @Override
    protected void updateUiOnUserUpdate(AUser user) {
        if (mUser != null && mUser.getLogin().equals(user.getLogin())) {
            if (mAdapter != null) {
                mUser = user;
                mAdapter.updateUser(user, true);

                if (Util.isStringNotNull(mUser.getName())) {
                    getSupportActionBar().setTitle(mUser.getName());
                }
            }
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

    @Override
    public void onBackPressed() {
        if (CompatibilityUtil.hasLollipopApi()) {
            finishAfterTransition();
        } else {
            finish();
        }

        super.onBackPressed();
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
        pd = new GitconProgressDialog(this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (mUser != null && Util.isStringNotNull(mUser.getName())) {
            getSupportActionBar().setTitle(mUser.getName());
        }
    }

    private void setupUi() {
        // RecyclerView
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this));
        rv.setAdapter(mAdapter);
        // ProgressDialog
        pd.setMessage(getString(R.string.dialog_loading));
    }

    private void getRepositories() {
        new GetUserReposApiTask(
                this,
                mUser.getLogin(),
                Util.isStringNull(mUserAsJson) || mUserAsJson.equals("null"),
                new SimpleTask.SimpleCallback<List<Repository>>() {
            @Override
            public void onStart() {
                pd.show();
            }

            @Override
            public void onComplete(List<Repository> res, String errorMessage) {
                if (Util.isListNotEmpty(res)) {
                    mAdapter.addInfo(new Repository(getString(R.string.tv_repositories_count, res.size())), false);
                    mAdapter.addData(res, true);
                } else {
                    mAdapter.addInfo(new Repository(getString(R.string.empty_no_data_available)), true);
                }

                if (Util.isStringNotNull(mUserAsJson) &&
                        !mUserAsJson.equals("null")) {
                    executeUserDetailsRequest();
                } else {
                    pd.dismiss();
                }
            }
        }).execute();
    }

    private void executeUserDetailsRequest() {
        new GetUserDetailsApiTask(
                this,
                mUser.getLogin(),
                new SimpleTask.SimpleCallback<AUser>() {
                    @Override
                    public void onStart() {
                        // Do nothing. Progress dialog is already showing.
                    }

                    @Override
                    public void onComplete(AUser res, String errorMessage) {
                        pd.dismiss();

                        if (res != null) {
                            mUser = res;
                            mAdapter.updateUser(mUser, true);
                        }
                    }
                }).execute();
    }

    private void logOut() {
        new LogOutTask(this, new SimpleTask.SimpleCallback<Void>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(Void res, String errorMessage) {
                startActivity(LoginActivity.getIntent(UserDetailsActivity.this));
                overridePendingTransition(0,0);
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
