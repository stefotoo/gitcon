package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.sample.android.gitcon.models.Follower;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.tasks.abstracts.ApiListTask;

import java.util.List;

public class GetUserFollowersApiTask extends ApiListTask<Follower> {

    // constructor
    public GetUserFollowersApiTask(Context context, String username, SimpleCallback<List<Follower>> callback) {
        super(context, username, callback);
    }

    // methods
    @Override
    protected List<Follower> getObjectsFromApi() throws Exception {
        return new RestClient().getAuthRestService().getUserFollowers(mUsername);
    }

    @Override
    protected List<Follower> getObjectsFromLocalDb() {
        return Follower.listAll(getObjectClass());
    }

    @Override
    protected Class<Follower> getObjectClass() {
        return Follower.class;
    }

    @Override
    protected boolean shouldSaveInLocalDb() {
        return true;
    }
}
