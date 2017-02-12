package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.sample.android.gitcon.models.Following;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.tasks.abstracts.ApiListTask;

import java.util.List;

public class GetUserFollowingApiTask extends ApiListTask<Following> {

    // constructor
    public GetUserFollowingApiTask(
            Context context,
            String username,
            boolean shouldUseLocalDb,
            SimpleCallback<List<Following>> callback) {
        super(context, username, shouldUseLocalDb, callback);
    }

    @Override
    protected List<Following> getObjectsFromApi() throws Exception {
        return new RestClient().getAuthRestService().getUserFollowing(mUsername);
    }

    @Override
    protected List<Following> getObjectsFromLocalDb() {
        return Following.listAll(getObjectClass());
    }

    @Override
    protected Class<Following> getObjectClass() {
        return Following.class;
    }

    @Override
    protected boolean shouldSaveInLocalDb() {
        return true;
    }
}
