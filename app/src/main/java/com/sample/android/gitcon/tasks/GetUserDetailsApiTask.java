package com.sample.android.gitcon.tasks;

import com.sample.android.gitcon.models.User;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;

public class GetUserDetailsApiTask extends SimpleTask<Void, User> {

    // TODO

    // constructor
    public GetUserDetailsApiTask(SimpleCallback<User> callback) {
        super(callback);
    }

    @Override
    protected User doInBackground(Void... voids) {
        return null;
    }
}
