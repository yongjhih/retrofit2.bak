package com.github;

import retrofit2.Retrofit;
import retrofit2.Retrofit.*;

import auto.json.AutoJson;

import rx.Observable;
import android.support.annotation.Nullable;

@AutoJson
public abstract class Contributor {
    @Nullable
    @AutoJson.Field
    public abstract String login();
    @Nullable
    @AutoJson.Field
    public abstract int contributions();

    public static Contributor create() {
        return new AutoJson_Contributor();
    }
}
