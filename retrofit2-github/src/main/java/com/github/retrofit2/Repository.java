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

/**
 * <pre>
 *  "id": 1296269,
 *  "owner": {
 *    "login": "octocat",
 *    "id": 1,
 *    "avatar_url": "https://github.com/images/error/octocat_happy.gif",
 *    "gravatar_id": "",
 *    "url": "https://api.github.com/users/octocat",
 *    "html_url": "https://github.com/octocat",
 *    "followers_url": "https://api.github.com/users/octocat/followers",
 *    "following_url": "https://api.github.com/users/octocat/following{/other_user}",
 *    "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
 *    "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
 *    "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
 *    "organizations_url": "https://api.github.com/users/octocat/orgs",
 *    "repos_url": "https://api.github.com/users/octocat/repos",
 *    "events_url": "https://api.github.com/users/octocat/events{/privacy}",
 *    "received_events_url": "https://api.github.com/users/octocat/received_events",
 *    "type": "User",
 *    "site_admin": false
 *  },
 *  "name": "Hello-World",
 *  "full_name": "octocat/Hello-World",
 *  "description": "This your first repo!",
 *  "private": false,
 *  "fork": true,
 *  "url": "https://api.github.com/repos/octocat/Hello-World",
 *  "html_url": "https://github.com/octocat/Hello-World",
 *  "clone_url": "https://github.com/octocat/Hello-World.git",
 *  "git_url": "git://github.com/octocat/Hello-World.git",
 *  "ssh_url": "git@github.com:octocat/Hello-World.git",
 *  "svn_url": "https://svn.github.com/octocat/Hello-World",
 *  "mirror_url": "git://git.example.com/octocat/Hello-World",
 *  "homepage": "https://github.com",
 *  "language": null,
 *  "forks_count": 9,
 *  "stargazers_count": 80,
 *  "watchers_count": 80,
 *  "size": 108,
 *  "default_branch": "master",
 *  "open_issues_count": 0,
 *  "has_issues": true,
 *  "has_wiki": true,
 *  "has_pages": false,
 *  "has_downloads": true,
 *  "pushed_at": "2011-01-26T19:06:43Z",
 *  "created_at": "2011-01-26T19:01:12Z",
 *  "updated_at": "2011-01-26T19:14:43Z",
 *  "permissions": {
 *    "admin": false,
 *    "push": false,
 *    "pull": true
 *  }
 * <pre>
 *
 * @see "https://developer.github.com/v3/repos/"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonObject
public class Repository {
    @JsonField
    public long id;
    @JsonField
    public Contributor owner;
    @JsonField
    public String name;
    @JsonField
    public String full_name;
    @JsonField
    public String description;

    @JsonField(name = "private")
    public Boolean isPrivate;

    @JsonField
    public Boolean fork;
    @JsonField
    public String url;
    @JsonField
    public String html_url;
    @JsonField
    public String clone_url;
    @JsonField
    public String git_url;
    @JsonField
    public String ssh_url;
    @JsonField
    public String svn_url;
    @JsonField
    public String mirror_url;
    @JsonField
    public String homepage;
    @JsonField
    public String language;
    @JsonField
    public int forks_count;
    @JsonField
    public int stargazers_count;
    @JsonField
    public int watchers_count;
    @JsonField
    public int size;
    @JsonField
    public String default_branch;
    @JsonField
    public int open_issues_count;
    @JsonField
    public Boolean has_issues;
    @JsonField
    public Boolean has_wiki;
    @JsonField
    public Boolean has_pages;
    @JsonField
    public Boolean has_downloads;
    @JsonField
    public String pushed_at;
    @JsonField
    public String created_at;
    @JsonField
    public String updated_at;
    @JsonField
    public Permission permissions;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonObject
    public static class Permission {
        @JsonField
        public boolean admin;
        @JsonField
        public boolean push;
        @JsonField
        public boolean pull;

        public Permission() {
        }
    }

    public Repository() {
    }
}
