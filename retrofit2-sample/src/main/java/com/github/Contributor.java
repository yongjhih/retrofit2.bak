package com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor {
    public String login;
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
