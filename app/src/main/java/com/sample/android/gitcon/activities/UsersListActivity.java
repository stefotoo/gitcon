package com.sample.android.gitcon.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sample.android.gitcon.R;
import com.sample.android.gitcon.adapters.RepositoriesAdapter;
import com.sample.android.gitcon.adapters.UserListAdapter;
import com.sample.android.gitcon.models.Follower;
import com.sample.android.gitcon.models.Following;
import com.sample.android.gitcon.models.User;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.preferences.AppPreferences;
import com.sample.android.gitcon.tasks.GetUserFollowersApiTask;
import com.sample.android.gitcon.tasks.GetUserFollowingApiTask;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;
import com.sample.android.gitcon.ui.DividerItemDecoration;
import com.sample.android.gitcon.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UsersListActivity
        extends AppCompatActivity
        implements UserListAdapter.UserClickListener {

    // constants
    public static final String BASIC_TAG = UsersListActivity.class.getName();

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_USERNAME = "username";

    public static final int FOLLOWERS_LIST = 1;
    public static final int FOLLOWING_LIST = 2;

    // variables
    private int mType;
    private String mUsername;
    private UserListAdapter mAdapter;

    // Ui
    @Bind(R.id.toolbar_activity_user_list)
    Toolbar toolbar;
    @Bind(R.id.rv_activity_user_list)
    RecyclerView rv;

    // get intent methods
    public static Intent getIntent(Context context, int type, String username) {
        Intent intent = new Intent(context, UsersListActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        intent.putExtra(EXTRA_USERNAME, username);

        return intent;
    }

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ButterKnife.bind(this);
        initExtras();
        initVariables();
        setupToolbar();
        setupUi();
        getUserList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initExtras() {
        mType = getIntent().getIntExtra(EXTRA_TYPE, 0);
        mUsername = getIntent().getStringExtra(EXTRA_USERNAME);
    }

    private void initVariables() {
        Picasso picasso = Picasso.with(this);
        mAdapter = new UserListAdapter(this, picasso, this);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle(mType == FOLLOWERS_LIST ?
                getString(R.string.title_followers) :
                getString(R.string.title_following));
    }

    private void setupUi() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this));
        rv.setAdapter(mAdapter);
    }

    private void getUserList() {
        switch (mType) {
            case FOLLOWERS_LIST : {
                getFollowers();
                break;
            }

            case FOLLOWING_LIST: {
                getFollowing();
                break;
            }
        }
    }

    private void getFollowing() {
        new GetUserFollowingApiTask(
                this,
                mUsername,
                new SimpleTask.SimpleCallback<List<Following>>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(List<Following> res, String errorMessage) {
                        if (Util.isListNotEmpty(res)) {
                            mAdapter.addData(res, true);
                        }
                    }
                }).execute();
    }

    private void getFollowers() {
        new GetUserFollowersApiTask(
                this,
                mUsername,
                new SimpleTask.SimpleCallback<List<Follower>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(List<Follower> res, String errorMessage) {
                if (Util.isListNotEmpty(res)) {
                    mAdapter.addData(res, true);
                }
            }
        }).execute();
    }

    @Override
    public void onUserClick(AUser user) {
        startActivity(UserDetailsActivity.getIntent(this, user));
    }
}
