# retrofit2

![retrofit2](art/retrofit2.png)

Retrofit2 turns your REST API into a Java interface.

Inspired by retrofit, compile-time version.

```java
@Retrofit("https://api.github.com")
public abstract class GitHub {
  @GET("/users/{user}/repos")
  Observable<Repo> repos(@Path("user") String user);
  
  public static GitHub create() {
    return new Retrofit_GitHub();
  }
}
```

```java
GitHub github = GitHub.create();
```

Each call on the generated instance of GitHub makes an HTTP request to the remote webserver.

```java
github.repos("octocat").forEach(System.out::println);
```

Use annotations to describe the HTTP request:

* URL parameter replacement and query parameter support
* Object conversion to request body (e.g., JSON, protocol buffers)
* Multipart request body and file upload

## Support Converter

* Support custom converter: `GsonConveter`, `JacksonConverter`, `MoshiConveter`, `LoganSquareConverter`, etc.

```java
Gson gson = new GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .registerTypeAdapter(Date.class, new DateTypeAdapter())
      .create();

GitHub github = GitHub.create(new GsonConverter(gson));
```

## Installation

```java
repositories {
    jcenter()
    mavne { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.yongjhih.retrofit2:retrofit2:-SNAPSHOT'
    apt 'com.github.yongjhih.retrofit2:retrofit2-processor:-SNAPSHOT'
}
```

## Development

* Support POST, DELTE, PUT: http://www.twitch.tv/yoandrew/v/7918907

## References

* http://square.github.io/retrofit/
* https://github.com/square/okhttp/wiki/Recipes
* http://square.github.io/okhttp/javadoc/com/squareup/okhttp/RequestBody.html

## License

```
Copyright 2015 8tory, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
