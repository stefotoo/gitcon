package com.sample.android.gitcon.tasks;

import android.content.Context;

import com.sample.android.gitcon.R;
import com.sample.android.gitcon.models.abstracts.AUser;
import com.sample.android.gitcon.rest.RestClient;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;
import com.sample.android.gitcon.utils.Util;

public class GetUserDetailsApiTask extends SimpleTask<Void, AUser> {

    // variables
    private String mUsername;
    private Context mContext;
    private String mErrorMessage;

    // constructor
    public GetUserDetailsApiTask(Context context, String username, SimpleCallback<AUser> callback) {
        super(callback);
        this.mUsername = username;
        this.mContext = context;
    }

    @Override
    protected AUser doInBackground(Void... voids) {
        AUser user = null;

        if (!Util.hasConnection(mContext)) {
            mErrorMessage = mContext.getString(R.string.error_no_connection);
        } else {
            try {
                user = new RestClient().getAuthRestService().getUserDetails(mUsername);
            } catch (Exception e) {
                e.printStackTrace();
                mErrorMessage = mContext.getString(R.string.error_login_not_recognized);
            }

        }

        return user;
    }

    @Override
    protected String getErrorMessage() {
        return mErrorMessage;
    }
}
