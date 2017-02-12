package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.tasks.abstracts.ApiListTask;
import com.sample.android.gitcon.utils.Util;

import java.util.Collections;
import java.util.List;

public class GetUserReposApiTask extends ApiListTask<Repository> {

    // constructor
    public GetUserReposApiTask(
            Context context,
            String username,
            boolean shouldUseLocalDb,
            SimpleCallback<List<Repository>> callback) {
        super(context, username, shouldUseLocalDb, callback);
    }

    // methods
    @Override
    protected List<Repository> getObjectsFromApi() throws Exception {
        List<Repository> repositories =
                new RestClient().getAuthRestService().getUserRepos(mUsername);
        if (Util.isListNotEmpty(repositories)) {
            Collections.sort(repositories);
        }

        return repositories;
    }

    @Override
    protected List<Repository> getObjectsFromLocalDb() {
        List<Repository> repositories = Repository.listAll(getObjectClass());
        if (Util.isListNotEmpty(repositories)) {
            Collections.sort(repositories);
        }

        return repositories;
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
