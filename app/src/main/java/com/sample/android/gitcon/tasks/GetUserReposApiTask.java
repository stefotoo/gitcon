package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.tasks.abstracts.ApiListTask;

import java.util.List;

public class GetUserReposApiTask extends ApiListTask<Repository> {

    public GetUserReposApiTask(Context context, String username, SimpleCallback<List<Repository>> callback) {
        super(context, username, callback);
    }

    @Override
    protected List<Repository> getObjectsFromApi() throws Exception {
        return new RestClient().getAuthRestService().getUserRepos(mUsername);
    }

    @Override
    protected List<Repository> getObjectsFromLocalDb() {
        return Repository.listAll(getObjectClass());
    }

    @Override
    protected Class<Repository> getObjectClass() {
        return Repository.class;
    }

    @Override
    protected boolean shouldSaveInLocalDb() {
        return true;
    }
}
