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

    @JsonField
    public long id;
    @JsonField
    public String avatar_url;
    @JsonField
    public String gravatar_id;
    @JsonField
    public String url;
    @JsonField
    public String html_url;
    @JsonField
    public String followers_url;
    @JsonField
    public String following_url;
    @JsonField
    public String gists_url;
    @JsonField
    public String starred_url;
    @JsonField
    public String subscriptions_url;
    @JsonField
    public String organizations_url;
    @JsonField
    public String repos_url;
    @JsonField
    public String events_url;
    @JsonField
    public String received_events_url;
    @JsonField
    public String type;
    @JsonField
    public boolean site_admin;

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
