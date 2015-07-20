package com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonObject
public class Contributor {
    @JsonField
    public String login;
    @JsonField
    public int contributions;

    //@JsonIgnore
    //public String login() {
        //return login();
    //}
    public Contributor() {
    }

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
