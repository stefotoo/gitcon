package com.sample.android.gitcon.models.abstracts;

import com.google.common.collect.Lists;
import com.orm.SugarRecord;
import com.sample.android.gitcon.tasks.abstracts.SimpleTask;

import java.util.List;

public abstract class ASugarRecord<T> extends SugarRecord {

    // static db methods
    protected static <T> List<T> findByIds(Class<T> clazz, String table, List<Long> ids) {
        String[] argsArray = new String[ids.size()];
        StringBuilder queryString = new StringBuilder("id IN (");

        for (int i = 0, z = ids.size(); i < z; i++) {
            queryString.append("?").append(i == z - 1 ? ")" : ",");
            argsArray[i] = String.valueOf(ids.get(i));
        }

        return findWithQuery(clazz,
                "Select * from " + table + " where " + queryString.toString(),
                argsArray);
    }

    protected static <T> void listAll(final Class<T> clazz,
                                      SimpleTask.SimpleCallback<List<T>> callback) {
        new SimpleTask<Void, List<T>>(callback) {

            @Override
            protected List<T> doInBackground(Void... params) {
                return listAll(clazz);
            }

        }.execute();
    }

    protected static <T> List<T> findAllObjectsSortedByCreatedAt(Class<T> clazz) {
        return find(clazz, null, null, null, "CREATED_AT", null);
    }

    protected static <T> void findAllObjectsSortedByCreatedAt(final Class<T> clazz,
                                                              SimpleTask.SimpleCallback<List<T>> callback) {
        new SimpleTask<Void, List<T>>(callback) {

            @Override
            protected List<T> doInBackground(Void... params) {
                return findAllObjectsSortedByCreatedAt(clazz);
            }

        }.execute();
    }

    protected static <T> T findObjectById(Class<T> clazz, Long id) {
        return findById(clazz, id);
    }

    protected static <T> void findObjectById(final Class<T> clazz,
                                             final Long id,
                                             SimpleTask.SimpleCallback<T> callback) {
        new SimpleTask<Void, T>(callback) {

            @Override
            protected T doInBackground(Void... params) {
                return findObjectById(clazz, id);
            }

        }.execute();
    }

    protected static void deleteObjectsByIds(String table, Iterable<Long> ids) {
        List<Long> idList = Lists.newArrayList(ids);
        String[] argsArray = new String[idList.size()];
        StringBuilder queryString = new StringBuilder("id IN (");

        for (int i = 0; i < idList.size(); i++) {
            queryString.append("?").append(i == idList.size() - 1 ? ")" : ",");
            argsArray[i] = String.valueOf(idList.get(i));
        }

        executeQuery("DELETE from " + table + " where " + queryString.toString(), argsArray);
    }

    protected static <T> List<T> findAllObjectsIds(Class<T> clazz, String table) {
        return findWithQuery(clazz, "Select ID from " + table);
    }

    protected static <T> void findAllObjectsIds(final Class<T> clazz,
                                                final String table,
                                                SimpleTask.SimpleCallback<List<T>> callback) {
        new SimpleTask<Void, List<T>>(callback) {

            @Override
            protected List<T> doInBackground(Void... params) {
                return findAllObjectsIds(clazz, table);
            }

        }.execute();
    }

    // public methods
    public void saveAsync(SimpleTask.SimpleCallback<Long> callback) {
        new SimpleTask<Void, Long>(callback) {

            @Override
            protected Long doInBackground(Void... params) {
                return save();
            }
        }.execute();
    }

    public void deleteAsync(SimpleTask.SimpleCallback<Boolean> callback) {
        new SimpleTask<Void, Boolean>(callback) {

            @Override
            protected Boolean doInBackground(Void... params) {
                return delete();
            }

        }.execute();
    }
}