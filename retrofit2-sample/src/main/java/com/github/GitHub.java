package com.github;

import retrofit.Retrofit;
import retrofit.Retrofit.*;

import auto.json.AutoJson;

import rx.Observable;
import android.support.annotation.Nullable;

@Retrofit("https://api.github.com")
public abstract class GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Observable<Contributor> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("https://api.github.com/repos/{owner}/{repo}/contributors")
    public abstract Observable<Contributor> contributorsWithoutBaseUrl(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("{url}")
    public abstract Observable<Contributor> contributorsDynamic(@Path("url") String url);

    public static GitHub create() {
        return new Retrofit_GitHub();
    }
}
