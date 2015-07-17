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

Each call on the generated GitHubService makes an HTTP request to the remote webserver.

```java
github.repos("octocat").forEach(System.out::println);
```

Use annotations to describe the HTTP request:

* URL parameter replacement and query parameter support
* Object conversion to request body (e.g., JSON, protocol buffers)
* Multipart request body and file upload

## TODO

* Custom converter
