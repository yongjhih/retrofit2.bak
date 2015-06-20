# retrofit2

![retrofit2](art/retrofit2.png)

Retrofit2 turns your REST API into a Java interface.

Inspired by retrofit, compile-time version.

```java
@Retrofit("https://api.github.com")
public abstract class GitHubService {
  @GET("/users/{user}/repos")
  List<Repo> listRepos(@Path("user") String user);
  
  public static GitHubService create() {
    return new Retrofit_GitHubService();
  }
}
```

```java
GitHubService github = GitHubService.create();
```

Each call on the generated GitHubService makes an HTTP request to the remote webserver.

```java
List<Repo> repos = service.listRepos("octocat");
```

Use annotations to describe the HTTP request:

* URL parameter replacement and query parameter support
* Object conversion to request body (e.g., JSON, protocol buffers)
* Multipart request body and file upload
