# retrofit2

[![Download](https://api.bintray.com/packages/yongjhih/maven/retrofit2/images/download.svg)](https://bintray.com/yongjhih/maven/retrofit2/_latestVersion)
[![JitPack](https://img.shields.io/github/tag/yongjhih/retrofit2.svg?label=JitPack)](https://jitpack.io/#yongjhih/retrofit2)
[![javadoc](https://img.shields.io/github/tag/yongjhih/retrofit2.svg?label=javadoc)](https://jitpack.io/com/github/yongjhih/retrofit2/simpleweibo/1.0.2/javadoc/index.html)
[![Build Status](https://travis-ci.org/yongjhih/retrofit2.svg)](https://travis-ci.org/yongjhih/retrofit2)
[![Join the chat at https://gitter.im/yongjhih/retrofit2](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/yongjhih/retrofit2?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![](https://avatars0.githubusercontent.com/u/5761889?v=3&s=48)](https://github.com/Wendly)
[![](https://avatars3.githubusercontent.com/u/213736?v=3&s=48)](https://github.com/yongjhih)
Contributors..

![retrofit2](art/retrofit2.png)

[![](art/screenshot-yongjhih.jpg)](https://appetize.io/app/3trwbht63k0rkfmbxbt51h84cr)

retrofit2 turns your REST API into a Java interface.

square/retrofit is a great project. So, why reinvent the wheel? Retrofit 2 is the first to implement the full stack with generated code. The guiding principle is to generate code that mimics the code that traceable and performant as it can be.

google/dagger2 has also re-implemented square/dagger.

See Also: [![](art/medium-48.jpg)](https://medium.com/@yongjhih/retrofit2-aa2fffd1a3c0)

Retrofit 2 has implemented almost retrofit’s features. And bonus:

* [@RetryHeaders](#support-retry-headers-retryheaders)
* [Global Headers](#global-headers)
* [@Converter](#custom-converter-for-method)

# Usage

```java
@Retrofit("https://api.github.com")
public abstract class GitHub {
  @GET("/users/{user}/repos")
  public abstract List<Repo> repos(@Path("user") String user);

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
List<Repo> repos = github.repos("octocat");
```

Use annotations to describe the HTTP request:

* URL parameter replacement and query parameter support
* Object conversion to request body (e.g., JSON, protocol buffers)
* Multipart request body and file upload

## API Declaration

Annotations on the interface methods and its parameters indicate how a request will be handled.

## REQUEST METHOD

Every method must have an HTTP annotation that provides the request method and relative URL. There are five built-in annotations: `GET`, `POST`, `PUT`, `DELETE`, and `HEAD`. The relative URL of the resource is specified in the annotation.

```java
@GET("/users/list")
```

You can also specify query parameters in the URL.

```java
@GET("/users/list?sort=desc")
```

## URL MANIPULATION

A request URL can be updated dynamically using replacement blocks and parameters on the method. A replacement block is an alphanumeric string surrounded by `{` and `}`. A corresponding parameter must be annotated with `@Path` using the same string.

```java
@GET("/group/{id}/users")
abstract Observable<List<User>> groupList(@Path("id") int groupId);
```

Query parameters can also be added.

```java
@GET("/group/{id}/users")
abstract Observable<List<User>> groupList(@Path("id") int groupId, @Query("sort") String sort);
```

For complex query parameter combinations a `Map` can be used.

```java
@GET("/group/{id}/users")
abstract Observable<List<User>> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);
```

## REQUEST BODY

An object can be specified for use as an HTTP request body with the `@Body` annotation.

```java
@POST("/users/new")
abstract Observable<User> createUser(@Body User user> cb);
```

The object will also be converted using the converter.

## FORM ENCODED AND MULTIPART

Methods can also be declared to send form-encoded and multipart data.

Form-encoded data is sent when `@FormUrlEncoded` is present on the method. Each key-value pair is annotated with `@Field` containing the name and the object providing the value.

```java
@FormUrlEncoded
@POST("/user/edit")
abstract Observable<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
```

Multipart requests are used when `@Multipart` is present on the method. Parts are declared using the `@Part` annotation.

```java
@Multipart
@PUT("/user/photo")
abstract Observable<User> updateUser(@Part("photo") TypedFile photo, @Part("description") TypedString description);
```

Multipart parts use the converter. In progress: or they can implement `TypedOutput` to handle their own serialization.

## HEADER MANIPULATION

You can set static headers for a method using the `@Headers` annotation.

```java
@Headers("Cache-Control: max-age=640000")
@GET("/widget/list")
abstract Observable<List<Widget>> widgetList();
```

```java
@Headers({
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit2"
})
@GET("/users/{username}")
abstract Observable<User> getUser(@Path("username") String username);
```

Note that headers do not overwrite each other. All headers with the same name will be included in the request.

A request Header can be updated dynamically using the `@Header` annotation. A corresponding parameter must be provided to the `@Header`. If the value is null, the header will be omitted. Otherwise, `toString` will be called on the value, and the result used.

```java
@GET("/user")
Observable<User> getUser(@Header("Authorization") String authorization);
```

### Global Headers

Headers that need to be added to every request can be specified using `@Headers` on your service. The following code uses `@Headers` that will add a User-Agent header to every request.

```java
@Retrofit("https://api.github.com")
@Headers({
    "Accept: application/vnd.github.v3.full+json",
    "User-Agent: Retrofit2"
})
abstract class GitHub {
    // ..
}
```

## SYNCHRONOUS VS. ASYNCHRONOUS VS. OBSERVABLE

Methods can be declared for either synchronous or asynchronous execution.

A method with a return type will be executed synchronously.

```java
@GET("/user/{id}/photo")
Photo getUserPhoto(@Path("id") int id);
```

Asynchronous execution requires the last parameter of the method be a `Callback`.

```java
@GET("/user/{id}/photo")
void getUserPhoto(@Path("id") int id, Callback<Photo> cb);
```

On Android, callbacks will be executed on the main thread. For desktop applications callbacks will happen on the same thread that executed the HTTP request.

Retrofit also integrates [RxJava](https://github.com/ReactiveX/RxJava/wiki) to support methods with a return type of `rx.Observable`

```java
@GET("/user/{id}/photo")
Observable<Photo> getUserPhoto(@Path("id") int id);
```

Observable requests are subscribed asynchronously and observed on the same thread that executed the HTTP request. To observe on a different thread (e.g. Android's main thread) call `observeOn(Scheduler)` on the returned `Observable`.

## RESPONSE OBJECT TYPE

HTTP responses are automatically converted to a specified type using the RestAdapter's converter which defaults to JSON. The desired type is declared as the method return type or using the Callback or Observable.

```java
@GET("/users/list")
List<User> userList();
```

```java
@GET("/users/list")
void userList(Callback<List<User>> cb);
```

```java
@GET("/users/list")
Observable<List<User>> userList();
```

For access to the raw HTTP response use the Response type.

```java
@GET("/users/list")
Response userList();
```

```java
@GET("/users/list")
void userList(Callback<Response> cb);
```

```java
@GET("/users/list")
Observable<Response> userList();
```

## Target Configuration

`Retrofit\_TARGET` is the class through which your API interfaces are turned into callable objects. By default, Retrofit2 will give you sane defaults for your platform but it allows for customization.

### JSON CONVERSION

Retrofit2 uses [LoganSquare](https://github.com/bluelinelabs/LoganSquare) by default to convert HTTP bodies to and from JSON. If you want to specify behavior that is different from Gson's defaults (e.g. naming policies, date formats, custom types), provide a new `Gson` instance with your desired behavior when building a `Retrofit_TARGET`. Refer to the [Gson documentation](https://sites.google.com/site/gson/gson-user-guide) for more details on customization.

### CUSTOM GSON CONVERTER EXAMPLE

The following code creates a new Gson instance that will convert all fields from lower case with underscores to camel case and vice versa. It also registers a type adapter for the `Date` class. This `DateTypeAdapter` will be used anytime Gson encounters a `Date` field.

The gson instance is passed as a parameter to `GsonConverter`, which is a wrapper class for converting types.

```java
public static class DateGsonConverter extends GsonConverter {
    public DateGsonConverter() {
        super(new com.google.gson.GsonBuilder()
            .setFieldNamingPolicy(com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(java.util.Date.class, new com.google.gson.internal.bind.DateTypeAdapter())
            .create());
    }
}

@Retrofit("https://api.github.com")
@Converter(DateGsonConverter.class)
abstract class GitHub {
    // ..
}
```

Each call on the generated `GitHub` will return objects converted using the Gson implementation provided to the `Retrofit_GitHub`.

### CUSTOM CONVERTER FOR METHOD

Specify another converter instance for one of methods by the following code:

```java
@Retrofit("https://api.github.com")
@Converter(DateGsonConverter.class)
abstract class GitHub {
    @GET("/users/{username}")
    @Converter(LoganSquareConverter.class)
    abstract Observable<User> getUser(@Path("username") String username);

    // ..
}
```

### CONTENT FORMAT AGNOSTIC

In addition to JSON, Retrofit can be configured to use other content formats. Retrofit provides alternate converters for XML (using [Simple](http://simple.sourceforge.net/)) and Protocol Buffers (using [protobuf](https://code.google.com/p/protobuf/) or [Wire](https://github.com/square/wire)). Please see the [retrofit-converters](https://github.com/square/retrofit/tree/master/retrofit-converters) directory for the full listing of converters.

The following code shows how to use `SimpleXMLConverter` to communicate with an API that uses XML

```java
@Retrofit("https://api.github.com")
@Converter(SimpleXMLConverter.class)
abstract class GitHub {
    // ..
}
```

### CUSTOM CONVERTERS

If you need to communicate with an API that uses a content-format that Retrofit does not support out of the box (e.g. YAML, txt, custom format) or you wish to use a different library to implement an existing format, you can easily create your own converter. Create a class that implements the [`Converter` interface](https://github.com/square/retrofit/blob/master/retrofit/src/main/java/retrofit/converter/Converter.java) and pass in an instance when building your adapter.

## CUSTOM ERROR HANDLING (in progress)

If you need custom error handling for requests, you may provide your own ErrorHandler. The following code shows how to throw a custom exception when a response returns a HTTP 401 status code

```java
@Retrofit("https://api.github.com")
@ErrorHandler(MyErrorHandler.class)
class GitHub {
    // ..
}
```

Note that if the return exception is checked, it must be declared on the interface method. It is recommended that you pass the supplied RetrofitError as the cause to any new exceptions you throw.

## LOGGING (in progress)

If you need to take a closer look at the requests and responses you can easily add logging levels to the `Retrofit_GitHub` with the `LogLevel` property. The possible logging levels are `BASIC`, `FULL`, `HEADERS`, and `NONE`.

The following code shows the addition of a full log level which will log the headers, body, and metadata for both requests and responses.

```java
@Retrofit("https://api.github.com")
@LogLevel(Retrofit.LogLevel.FULL)
class GitHub {
    // ..
}
```

## Support Retry Headers: `@RetryHeaders`

For Retry Stale example:

```java
@Retrofit("https://api.github.com")
@RetryHeaders(
    value = "Cache-Control: max-age=640000",
    exceptions = retrofit2.RequestException.class
)
public abstract class GitHub {
    // ..
}
```

Retry the request with cache if `RequestException` thats network issue.

## Support Interceptor: `@RequestInterceptor` (in progress)

```java
@Retrofit("https://api.github.com")
@RequestInterceptor(MyRequestInterceptor.class)
```

## Installation

via jcenter:

```java
repositories {
    jcenter()
}

dependencies {
    compile 'com.infstory:retrofit2:1.0.0'
    apt 'com.infstory:retrofit2-processor:1.0.0'
}
```

or via jitpack (in progress):

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

## Demonstration

* https://appetize.io/app/3trwbht63k0rkfmbxbt51h84cr

## Test

Test github client:

```bash
./gradlew clean :retrofit2-github:testDebug
```

All tests:

```bash
./gradlew clean test
```

Github sample app:

```bash
./gradlew clean :retrofit2-github-app:assembleDebug
```

## Development

* Support POST, DELTE, PUT: http://www.twitch.tv/yoandrew/v/7918907

## References

* http://square.github.io/retrofit/
* https://github.com/square/okhttp/wiki/Recipes
* http://square.github.io/okhttp/javadoc/com/squareup/okhttp/RequestBody.html

## Credit

* Square, Inc.

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
