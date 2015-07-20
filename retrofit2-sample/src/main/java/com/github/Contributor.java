package com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor {
    public String login;
    public int contributions;

    public Contributor() {
    }

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }
}
