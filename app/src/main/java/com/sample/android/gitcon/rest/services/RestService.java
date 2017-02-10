package com.sample.android.gitcon.rest.services;

import com.sample.android.gitcon.models.Follower;
import com.sample.android.gitcon.models.Following;
import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.models.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface RestService {

    @GET("/users/{username}")
    User getUserDetails(@Path("username") String username);

    @GET("/users/{username}/repos")
    List<Repository> getUserRepos(@Path("username") String username);

    @GET("/users/{username}/followers")
    List<Follower> getUserFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    List<Following> getUserFollowing(@Path("username") String username);

}