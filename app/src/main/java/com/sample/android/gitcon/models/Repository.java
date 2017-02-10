package com.sample.android.gitcon.models;

import com.google.gson.annotations.SerializedName;
import com.sample.android.gitcon.models.abstracts.ASugarRecord;

public class Repository extends ASugarRecord<Repository> {

    // variables
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("watchers_count")
    private int watchersCount;
    @SerializedName("forks_count")
    private int forksCount;

    // constructor
    public Repository() {}

    // methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }
}
