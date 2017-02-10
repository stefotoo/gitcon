package com.sample.android.gitcon.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.android.gitcon.rest.services.RestService;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    // constants
    public static String API_URL = "https://api.github.com";


    // variables
    private RestAdapter restAdapter;
    private RestService authRestService;

    // constructors
    public RestClient() {
        Gson gson = new GsonBuilder().create();

        restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    public RestService getAuthRestService() {
        if (authRestService == null) {
            authRestService = restAdapter.create(RestService.class);
        }

        return authRestService;
    }
}