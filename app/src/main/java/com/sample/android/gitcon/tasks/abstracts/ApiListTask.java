package com.sample.android.gitcon.tasks.abstracts;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.SugarTransactionHelper;
import com.sample.android.gitcon.models.abstracts.ASugarRecord;

import java.util.List;

public abstract class ApiListTask<E extends ASugarRecord> extends SimpleTask<Void, List<E>> {

    // variables
    private Context mContext;
    private boolean mShouldUseLocalDb;
    protected String mUsername;

    // constructors
    public ApiListTask(Context context, String username, boolean shouldUseLocalDb, SimpleCallback<List<E>> callback) {
        super(callback);
        this.mContext = context;
        this.mUsername = username;
        this.mShouldUseLocalDb = shouldUseLocalDb;
    }

    // methods
    @Override
    protected List<E> doInBackground(Void... params) {
        try {
            final List<E> newObjects = getObjectsFromApi();
            if (newObjects == null && mShouldUseLocalDb) {
                return getObjectsFromLocalDb();
            } else {
                if (mShouldUseLocalDb && shouldSaveInLocalDb()) {
                    SugarRecord.deleteAll(getObjectClass());
                    SugarTransactionHelper.doInTransaction(
                            new SugarTransactionHelper.Callback() {

                                @Override
                                public void manipulateInTransaction() {
                                    for (E object : newObjects) {
                                        object.save();
                                    }
                                }
                            }
                    );
                }
            }

            return newObjects;
        } catch (Exception e) {
            e.printStackTrace();
            return mShouldUseLocalDb ? getObjectsFromLocalDb() : null;
        }
    }

    @Override
    protected String getErrorMessage() {
        return null;
    }

    // abstract methods
    protected abstract List<E> getObjectsFromApi() throws Exception;
    protected abstract List<E> getObjectsFromLocalDb();
    protected abstract Class<E> getObjectClass();
    protected abstract boolean shouldSaveInLocalDb();
}
