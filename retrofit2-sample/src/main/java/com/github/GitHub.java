package com.github;

import retrofit2.Retrofit;
import retrofit2.Retrofit.*;

import auto.json.AutoJson;

import rx.Observable;
import android.support.annotation.Nullable;
import java.io.File;

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

    @POST("/user/edit")
    public abstract Observable<Contributor> updateUser(@Body Contributor user);

    @FormUrlEncoded
    @POST("/user/edit")
    public abstract Observable<Contributor> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    @Multipart
    @PUT("/user/photo")
    public abstract Observable<Contributor> updateUser(@Part(value = "photo", type = "") File photo, @Part(value = "description", type = "") String description);

    @Headers("Cache-Control: max-age=640000")
    @GET("/widget/list")
    public abstract Observable<Contributor> widgetList();

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}")
    public abstract Observable<Contributor> getUser(@Path("username") String username);

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit-Sample-App"
    })
    @GET("/user")
    public abstract Observable<Contributor> getUserWithAuthorization(@Header("Authorization") String authorization);

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit2"
    })
    @PUT("/user/starred/{owner}/{repo}")
    public abstract Observable<Contributor> star(@Query("access_token") String accessToken,
        @Path("owner") String owner,
        @Path("repo") String repo);

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit2"
    })
    @DELETE("/user/starred/{owner}/{repo}")
    public abstract Observable<Contributor> unstar(@Query("access_token") String accessToken,
        @Path("owner") String owner,
        @Path("repo") String repo);

    public static GitHub create() {
        return new Retrofit_GitHub();
    }
}
