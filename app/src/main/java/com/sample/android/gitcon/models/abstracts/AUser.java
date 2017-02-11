package com.sample.android.gitcon.models.abstracts;

import com.google.gson.annotations.SerializedName;

public class AUser extends ASugarRecord {

    // variables
    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("bio")
    private String bio;
    @SerializedName("location")
    private String location;
    @SerializedName("followers")
    private int followers;
    @SerializedName("following")
    private int following;
    @SerializedName("public_repos")
    private int publicReposCount;

    // getters and setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getPublicReposCount() {
        return publicReposCount;
    }

    public void setPublicReposCount(int publicReposCount) {
        this.publicReposCount = publicReposCount;
    }
}
