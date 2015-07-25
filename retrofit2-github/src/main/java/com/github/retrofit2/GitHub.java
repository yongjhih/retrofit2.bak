/*
 * Copyright (C) 2015 8tory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.retrofit2;

import retrofit.http.*;
import retrofit.http.Retrofit.RetryHeaders;
import retrofit.http.Retrofit.ErrorHandler;
import retrofit.http.Retrofit.RequestInterceptor;
import retrofit.http.Retrofit.LogLevel;
import retrofit.http.Retrofit.Bindable;
import retrofit.http.Retrofit.QueryBinding;

import rx.Observable;
import java.io.File;

import retrofit.converter.*;
import java.util.List;
import rx.functions.*;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;
import com.github.mobile.model.*;
import retrofit.client.Response;
import retrofit.Callback;

@Retrofit("https://api.github.com")
@retrofit.http.Retrofit.Headers({ // optional
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit2"
})
@RetryHeaders( // optional
    value = "Cache-Control: max-age=640000",
    exceptions = retrofit2.RequestException.class
)
@Retrofit.Converter(GsonConverter.class) // optional
//@Retrofit.Converter(LoganSquareConverter.class) // default
@LogLevel(retrofit.RestAdapter.LogLevel.FULL) // optional
@RequestInterceptor(GitHubRequestInterceptor.class)
@ErrorHandler(GitHubErrorHandler.class)
public abstract class GitHub {
    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Observable<List<Contributor>> contributorList(
            @Path("owner") String owner,
            @Path("repo") String repo);

    public Observable<Contributor> contributors(
            String owner,
            String repo) {
        return contributorList(owner, repo).flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
            @Override public Observable<Contributor> call(List<Contributor> list) {
                return Observable.from(list);
            }
        });
    }

    @GET("https://api.github.com/repos/{owner}/{repo}/contributors")
    public abstract Observable<List<Contributor>> contributorListWithoutBaseUrl(
            @Path("owner") String owner,
            @Path("repo") String repo);

    public Observable<Contributor> contributorsWithoutBaseUrl(
            String owner,
            String repo) {
        return contributorListWithoutBaseUrl(owner, repo).flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
            @Override public Observable<Contributor> call(List<Contributor> list) {
                return Observable.from(list);
            }
        });
    }

    @GET("{url}")
    public abstract Observable<List<Contributor>> contributorListDynamic(@Path("url") String url);

    public Observable<Contributor> contributorsDynamic(String url) {
        return contributorListDynamic(url).flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
            @Override public Observable<Contributor> call(List<Contributor> list) {
                return Observable.from(list);
            }
        });
    }

    @POST("/user/edit")
    public abstract Observable<Contributor> updateUser(@Body Contributor user);

    @FormUrlEncoded
    @POST("/user/edit")
    public abstract Observable<Contributor> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    @Multipart
    @PUT("/user/photo")
    public abstract Observable<Contributor> updateUser(@retrofit.http.Retrofit.Part(value = "photo", mimeType = "image/png") File photo, @Part("description") String description);
    @Multipart
    @PUT("/user/photo")
    public abstract Observable<Contributor> updateUserWithTypedFile(@Part("photo") TypedFile photo, @Part("description") String description);
    @Multipart
    @PUT("/user/photo")
    public abstract Observable<Contributor> updateUserWithTypedStringAnootation(@Part("photo") TypedFile photo, @retrofit.http.Retrofit.Part(value = "description", mimeType = "application/json") String description);
    @Multipart
    @PUT("/user/photo")
    public abstract Observable<Contributor> updateUserWithTypedString(@Part("photo") TypedFile photo, @Part("description") TypedString description);

    @Headers("Cache-Control: max-age=640000")
    @GET("/widget/list")
    public abstract Observable<Contributor> widgetList();

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit2"
    })
    @GET("/users/{username}")
    public abstract Observable<Contributor> getUser(@Path("username") String username);

    @Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit2"
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

    @DELETE("/repos/{owner}/{repo}")
    public abstract Observable<Contributor> deleteRepository(
            @Header("Authorization") String basicCredentials,
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/authorizations")
    public abstract Observable<Authorization> getAuthorizations(@Header("Authorization") String token);

    @POST("/authorizations")
    public abstract Observable<Authorization> createDeleteAuthorization(@Header("Authorization") String basicCredentials,
            @Body Authorization authorization);

    @GET("/users/{username}/repos")
    @QueryBinding(value = "access_token", binder = AuthorizationBinder.class)
    public abstract Observable<Repository> repositoriesBinder(@Header("username") String username);

    //@GET("/users/repos") // TODO intercepter // TODO header-binding
    //public abstract Observable<Repository> repositories();

    public static class AuthorizationBinder implements Bindable<Authorization> {
        @Override public String call(Authorization authorization) {
            return authorization.getToken();
        }
    }

    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Observable<Response> contributorResponse(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    public abstract void contributorResponse(
            @Path("owner") String owner,
            @Path("repo") String repo, Callback<Response> callback);

    @GET("/repos/{owner}/{repo}/contributors")
    public abstract void contributorList(
            @Path("owner") String owner,
            @Path("repo") String repo, Callback<List<Contributor>> callback);

    @GET("/repos/{owner}/{repo}/contributors")
    public abstract List<Contributor> contributorListBlocking(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Response contributorResponseBlocking(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @POST("/user/repos")
    public abstract Observable<Repository> createRepository(@Body Repository repository);

    @DELETE("/repos/{owner}/{repo}")
    public abstract Response deleteRepository(
            @Path("owner") String owner,
            @Path("repo") String repo);

    @POST("/orgs/{org}/repos")
    public abstract Observable<Repository> createRepository(@Query("org") String org, @Body Repository repository);

    @Retrofit.Converter(GsonConverter.class) // optional
    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Observable<List<Contributor>> contributorListWithGson(
            @Path("owner") String owner,
            @Path("repo") String repo);

    public Observable<Contributor> contributorsWithGson(
            String owner,
            String repo) {
        return contributorListWithGson(owner, repo).flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
            @Override public Observable<Contributor> call(List<Contributor> list) {
                return Observable.from(list);
            }
        });
    }

    @Retrofit.Converter(DateGsonConverter.class) // optional
    @GET("/repos/{owner}/{repo}/contributors")
    public abstract Observable<List<Contributor>> contributorListWithDateGson(
            @Path("owner") String owner,
            @Path("repo") String repo);

    public Observable<Contributor> contributorsWithDateGson(
            String owner,
            String repo) {
        return contributorList(owner, repo).flatMap(new Func1<List<Contributor>, Observable<Contributor>>() {
            @Override public Observable<Contributor> call(List<Contributor> list) {
                return Observable.from(list);
            }
        });
    }

    public static class DateGsonConverter extends GsonConverter {
        public DateGsonConverter() {
            super(new com.google.gson.GsonBuilder()
                .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(java.util.Date.class, new com.google.gson.internal.bind.DateTypeAdapter())
                .create());
        }
    }

    @GET("/users/{username}/repos")
    public abstract Observable<List<Repository>> repositoryList(@Path("username") String username);

    public Observable<Repository> repositories(String username) {
        return repositoryList(username).flatMap(new Func1<List<Repository>, Observable<Repository>>() {
            @Override public Observable<Repository> call(List<Repository> list) {
                return Observable.from(list);
            }
        });
    }

    @GET("/orgs/{org}/repos")
    public abstract Observable<List<Repository>> orgRepositoryList(@Path("org") String org);

    public Observable<Repository> orgRepositories(String org) {
        return orgRepositoryList(org).flatMap(new Func1<List<Repository>, Observable<Repository>>() {
            @Override public Observable<Repository> call(List<Repository> list) {
                return Observable.from(list);
            }
        });
    }

    public static GitHub create() {
        return new Retrofit_GitHub();
    }
}
