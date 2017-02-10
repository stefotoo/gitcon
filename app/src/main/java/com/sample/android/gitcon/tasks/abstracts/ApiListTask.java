package com.sample.android.gitcon.tasks.abstracts;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.SugarTransactionHelper;
import com.sample.android.gitcon.models.abstracts.ASugarRecord;

import java.util.List;

public abstract class ApiListTask<E extends ASugarRecord> extends SimpleTask<Void, List<E>> {

    // variables
    protected Context mContext;
    protected String mUsername;

    // constructors
    public ApiListTask(Context context, String username, SimpleCallback<List<E>> callback) {
        super(callback);
        this.mContext = context;
        this.mUsername = username;
    }

    // methods
    @Override
    protected List<E> doInBackground(Void... params) {
        try {
            final List<E> newObjects = getObjectsFromApi();
            if (newObjects == null) {
                return getObjectsFromLocalDb();
            } else {
                if (shouldSaveInLocalDb()) {
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
            return getObjectsFromLocalDb();
        }
    }

    // abstract methods
    protected abstract List<E> getObjectsFromApi() throws Exception;
    protected abstract List<E> getObjectsFromLocalDb();
    protected abstract Class<E> getObjectClass();
    protected abstract boolean shouldSaveInLocalDb();
}
