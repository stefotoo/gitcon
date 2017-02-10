package com.sample.android.gitcon.rest.services;

import com.sample.android.gitcon.models.Repository;
import com.sample.android.gitcon.models.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface RestService {

    @GET("/users/{username}")
    User getUserDetails(@Path("username") String userName);

    @GET("/users/{username}/repos")
    List<Repository> getUserRepos(@Path("username") String userName);

    @GET("/users/{username}/followers")
    List<User> getUserFollowers(@Path("username") String userName);

    @GET("/users/{username}/following")
    List<User> getUserFollowing(@Path("username") String userName);

}