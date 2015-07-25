/*
 * Copyright (C) 2015 8tory, Inc.
 * Copyright (C) 2012 Google, Inc.
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
package retrofit.http;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.util.List;
import rx.functions.*;

/**
 * baseUrl
 */
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface Retrofit {
  String value() default "";

  @Retention(RUNTIME)
  @Target(TYPE)
  public @interface Builder {
  }

  /**
   * Specifies that the annotated method is a validation method. The method should be a non-private
   * no-argument method in an Retrofit class. It will be called by the {@code build()} method of
   * the {@link Builder @Retrofit.Builder} implementation, immediately after constructing the new
   * object. It can throw an exception if the new object fails validation checks.
   */
  @Retention(RUNTIME)
  @Target(METHOD)
  public @interface Validate {
  }

  /**
   * For @RetryHeaders @GET|@PUT|@POST|@DELETE, @RetryHeaders class
   */
  @Retention(RUNTIME)
  @Target({METHOD, TYPE})
  public @interface RetryHeaders {
    String[] value();
    Class<? extends Throwable>[] exceptions() default Throwable.class;
    //Class<? extends ErrorHandler> errorHandler() default ErrorHandler.class;
    //Class<? extends ErrorHandler> onRetry() default ErrorHandler.class;
    //Class<? extends ErrorHandler> onNext() default ErrorHandler.class;
  }

  /**
   * For @ErrorHandler @GET|@PUT|@POST|@DELETE, @ErrorHandler class
   */
  @Retention(RUNTIME)
  @Target({METHOD, TYPE})
  public @interface ErrorHandler {
    Class<? extends retrofit.ErrorHandler> value() default retrofit.ErrorHandler.class;
  }

  /**
   * For @Converter @GET|@PUT|@POST|@DELETE, @Converter class
   */
  @Retention(RUNTIME)
  @Target({METHOD, TYPE})
  public @interface Converter {
    Class<? extends retrofit.converter.Converter> value() default retrofit.converter.Converter.class;
  }

  /**
   * For @LogLevel @GET|@PUT|@POST|@DELETE, @LogLevel class
   */
  // TODO aspect alternative?
  @Retention(RUNTIME)
  @Target({METHOD, TYPE})
  public @interface LogLevel {
    retrofit.RestAdapter.LogLevel value() default retrofit.RestAdapter.LogLevel.NONE;
  }

  @Retention(RUNTIME)
  @Target(PARAMETER)
  public @interface QueryBundle {
  }

  @Retention(RUNTIME)
  @Target({TYPE, METHOD})
  public @interface QueryBinding {
      String value() default "";
      Class<? extends Bindable> binder() default Bindable.class;
  }

  @Retention(RUNTIME)
  @Target({TYPE, METHOD})
  public @interface RequestInterceptor {
      Class<? extends retrofit.RequestInterceptor> value() default retrofit.RequestInterceptor.class;
  }

  public static interface Bindable<T> extends Func1<T, String> {
      public String call(T t);
  }

  /** Make a DELETE request to a REST path relative to base URL. */
  @Documented
  @Retention(RUNTIME)
  @Target(METHOD)
  @RestMethod("DELETE")
  public @interface DELETE {
    String value() default "";
    String[] permissions() default {};
  }

  /**
   * Use this annotation on a service method param when you want to directly control the request body
   * of a POST/PUT request (instead of sending in as request parameters or form-style request
   * body). If the value of the parameter implements {@link retrofit.mime.TypedOutput TypedOutput},
   * the request body will be written exactly as specified by
   * {@link retrofit.mime.TypedOutput#writeTo(java.io.OutputStream)}. If the value does not implement
   * TypedOutput, the object will be serialized using the {@link retrofit.RestAdapter RestAdapter}'s
   * {@link retrofit.converter.Converter Converter} and the result will be set directly as the
   * request body.
   * <p>
   * Body parameters may not be {@code null}.
   *
   * @author Eric Denman (edenman@squareup.com)
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface Body {
  }

  /**
   * Named replacement in the URL path. Values are converted to string using
   * {@link String#valueOf(Object)}. Values are used literally without URL encoding. See
   * {@link retrofit.http.Path @Path} for URL encoding equivalent.
   * <p>
   * <pre>
   * &#64;GET("/image/{id}")
   * void example(@EncodedPath("id") int id, ..);
   * </pre>
   * <p>
   * Path parameters may not be {@code null}.
   *
   * @see Path
   * @deprecated Use {@link Path} with {@link Path#encode() encode = false}.
   */
  @Documented
  @Deprecated
  @Retention(RUNTIME)
  @Target(PARAMETER)
  public @interface EncodedPath {
    String value();
  }

  /**
   * Encoded query parameter appended to the URL.
   * <p>
   * Values are converted to strings using {@link String#valueOf(Object)}. Values are not URL
   * encoded. {@code null} values will not include the query parameter in the URL. See
   * {@link Query @Query} for URL-encoding equivalent.
   *
   * @see Query
   * @see QueryMap
   * @deprecated Use {@link Query} with {@link Query#encodeValue() encodeValue = false}.
   */
  @Documented
  @Deprecated
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface EncodedQuery {
    String value();
  }

  /**
   * Query keys and values appended to the URL.
   * <p>
   * Both keys and values are converted to strings using {@link String#valueOf(Object)}. Values are
   * not URL encoded. {@code null} values will not include the query parameter in the URL. See
   * {@link QueryMap @QueryMap} for URL-encoding equivalent.
   *
   * @see Query
   * @see QueryMap
   * @deprecated Use {@link QueryMap} with {@link QueryMap#encodeValues() encodeValues = false}.
   */
  @Documented
  @Deprecated
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface EncodedQueryMap {
  }

  /**
   * Named pair for a form-encoded request.
   * <pre>
   * &#64;FormUrlEncoded &#64;POST(&#64;Field)
   * </pre>
   * <p>
   * Values are converted to strings using {@link String#valueOf(Object)} and then form URL encoded.
   * {@code null} values are ignored. Passing a {@link java.util.List List} or array will result in a
   * field pair for each non-{@code null} item.
   * <p>
   * Simple Example:
   * <pre>
   * &#64;FormUrlEncoded
   * &#64;POST("/")
   * void example(@Field("name") String name, @Field("occupation") String occupation);
   * }
   * </pre>
   * Calling with {@code foo.example("Bob Smith", "President")} yields a request body of
   * {@code name=Bob+Smith&occupation=President}.
   * <p>
   * Array Example:
   * <pre>
   * &#64;FormUrlEncoded
   * &#64;POST("/list")
   * void example(@Field("name") String... names);
   * </pre>
   * Calling with {@code foo.example("Bob Smith", "Jane Doe")} yields a request body of
   * {@code name=Bob+Smith&name=Jane+Doe}.
   *
   * @see FormUrlEncoded
   * @see FieldMap
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface Field {
    String value();

    /** Specifies whether {@link #value()} is URL encoded. */
    boolean encodeName() default true;

    /** Specifies whether the argument value to the annotated method parameter is URL encoded. */
    boolean encodeValue() default true;
  }

  /**
   * Named key/value pairs for a form-encoded request.
   * <p>
   * Field values may be {@code null} which will omit them from the request body.
   * <p>
   * Simple Example:
   * <pre>
   * &#64;FormUrlEncoded
   * &#64;POST("/things")
   * void things(@FieldMap Map&lt;String, String&gt; fields);
   * }
   * </pre>
   * Calling with {@code foo.things(ImmutableMap.of("foo", "bar", "kit", "kat")} yields a request
   * body of {@code foo=bar&kit=kat}.
   *
   * @see FormUrlEncoded
   * @see Field
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface FieldMap {
    /** Specifies whether parameter names (keys in the map) are URL encoded. */
    boolean encodeNames() default true;

    /** Specifies whether parameter values (values in the map) are URL encoded. */
    boolean encodeValues() default true;
  }

  /**
   * Denotes that the request body will use form URL encoding. Fields should be declared as
   * parameters and annotated with {@link Field @Field}.
   * For &#64;FormUrlEncoded &#64;POST(&#64;Field)
   * <p>
   * Requests made with this annotation will have {@code application/x-www-form-urlencoded} MIME
   * type. Field names and values will be UTF-8 encoded before being URI-encoded in accordance to
   * <a href="http://tools.ietf.org/html/rfc3986">RFC-3986</a>.
   */
  @Retention(RUNTIME)
  @Target(METHOD)
  public @interface FormUrlEncoded {
  }

  /** Make a GET request to a REST path relative to base URL. */
  @Documented
  @Target(METHOD)
  @Retention(RUNTIME)
  @RestMethod("GET")
  public @interface GET {
    String value() default "";
    String[] permissions() default {};
  }

  /**
   * Replaces the header with the the value of its target.
   * &#64;GET|&#64;PUT|&#64;POST|&#64;DELETE(&#64;Header)
   * <p>
   * <pre>
   * &#64;GET("/")
   * void foo(@Header("Accept-Language") String lang, Callback&lt;Response&gt; cb);
   * </pre>
   * <p>
   * Header parameters may be {@code null} which will omit them from the request. Passing a
   * {@link java.util.List List} or array will result in a header for each non-{@code null} item.
   * <p>
   * <strong>Note:</strong> Headers do not overwrite each other. All headers with the same name will
   * be included in the request.
   *
   * @author Adrian Cole (adrianc@netflix.com)
   */
  @Retention(RUNTIME)
  @Target(PARAMETER)
  public @interface Header {
    String value();
  }

  /**
   * Adds headers literally supplied in the {@code value}.
   * <pre>
   * &#64;Headers &#64;GET|&#64;PUT|&#64;POST|&#64;DELETE, &#64;Headers class Retrofit
   * </pre>
   * <p>
   * <pre>
   * &#64;Headers("Cache-Control: max-age=640000")
   * &#64;GET("/")
   * ...
   *
   * &#64;Headers({
   *   "X-Foo: Bar",
   *   "X-Ping: Pong"
   * })
   * &#64;GET("/")
   * ...
   * </pre>
   * <p>
   * <strong>Note:</strong> Headers do not overwrite each other. All headers with the same name will
   * be included in the request.
   *
   * @author Adrian Cole (adrianc@netflix.com)
   */
  @Documented
  @Target({METHOD, TYPE})
  @Retention(RUNTIME)
  public @interface Headers {
    String[] value();
  }

  /** Make a HEAD request to a REST path relative to base URL. */
  @Documented
  @Target(METHOD)
  @Retention(RUNTIME)
  @RestMethod("HEAD")
  public @interface HEAD {
    String value();
    String[] permissions() default {};
  }

  /**
   * Denotes that the request body is multi-part. Parts should be declared as parameters and
   * annotated with {@link Part @Part}.
   * <pre>
   * &#64;Multipart &#64;PUT(&#64;Part)
   * </pre>
   */
  @Retention(RUNTIME)
  @Target(METHOD)
  public @interface Multipart {
  }

  /**
   * Denotes a single part of a multi-part request.
   * <pre>
   * &#64;Multipart &#64;PUT(&#64;Part)
   * </pre>
   * <p>
   * The parameter type on which this annotation exists will be processed in one of three ways:
   * <ul>
   * <li>If the type implements {@link retrofit.mime.TypedOutput TypedOutput} the headers and
   * body will be used directly.</li>
   * <li>If the type is {@link String} the value will also be used directly with a {@code text/plain}
   * content type.</li>
   * <li>Other object types will be converted to an appropriate representation by calling {@link
   * retrofit.converter.Converter#toBody(Object)}.</li>
   * </ul>
   * <p>
   * Values may be {@code null} which will omit them from the request body.
   * <p>
   * <pre>
   * &#64;Multipart
   * &#64;POST("/")
   * void example(&#64;Part("description") String description,
   *              &#64;Part("image") TypedFile image,
   *              ...
   * );
   * </pre>
   * <p>
   * Part parameters may not be {@code null}.
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface Part {
    String value();
    /** The {@code Content-Transfer-Encoding} of this part. */
    String encoding() default "binary";
    // "text/plain"|"text/plain; charset=UTF-8"|"text/x-markdown; charset=utf-8"
    String mimeType() default "";
  }

 /**
  * Denotes name and value parts of a multi-part request
  * <p>
  * Values of the map on which this annotation exists will be processed in one of three ways:
  * <ul>
  * <li>If the type implements {@link retrofit.mime.TypedOutput TypedOutput} the headers and
  * body will be used directly.</li>
  * <li>If the type is {@link String} the value will also be used directly with a {@code text/plain}
  * content type.</li>
  * <li>Other object types will be converted to an appropriate representation by calling {@link
  * retrofit.converter.Converter#toBody(Object)}.</li>
  * </ul>
  * <p>
  * <pre>
  * &#64;Multipart
  * &#64;POST("/upload")
  * void upload(&#64;Part("file") TypedFile file, &#64;PartMap Map&lt;String, String&gt; params);
  * </pre>
  * <p>
  *
  * @see Multipart
  * @see Part
  */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface PartMap {
    /** The {@code Content-Transfer-Encoding} of this part. */
    String encoding() default "binary";
  }

  /** Make a PATCH request to a REST path relative to base URL. */
  @Documented
  @Target(METHOD)
  @Retention(RUNTIME)
  @RestMethod(value = "PATCH", hasBody = true)
  public @interface PATCH {
    String value();
  }

  /**
   * Named replacement in the URL path. Values are converted to string using
   * {@link String#valueOf(Object)} and URL encoded.
   * <pre>
   * &#64;GET|&#64;PUT|&#64;POST|&#64;DELETE(&#64;Path)
   * </pre>
   * <p>
   * Simple example:
   * <pre>
   * &#64;GET("/image/{id}")
   * void example(@Path("id") int id);
   * </pre>
   * Calling with {@code foo.example(1)} yields {@code /image/1}.
   * <p>
   * Values are URL encoded by default. Disable with {@code encode=false}.
   * <pre>
   * &#64;GET("/user/{name}")
   * void encoded(@Path("name") String name);
   *
   * &#64;GET("/user/{name}")
   * void notEncoded(@Path(value="name", encode=false) String name);
   * </pre>
   * Calling {@code foo.encoded("John+Doe")} yields {@code /user/John%2BDoe} whereas
   * {@code foo.notEncoded("John+Doe")} yields {@code /user/John+Doe}.
   * <p>
   * Path parameters may not be {@code null}.
   */
  @Documented
  @Retention(RUNTIME)
  @Target(PARAMETER)
  public @interface Path {
    String value();

    /** Specifies whether the argument value to the annotated method parameter is URL encoded. */
    boolean encode() default true;
  }

  /** Make a POST request to a REST path relative to base URL. */
  @Documented
  @Target(METHOD)
  @Retention(RUNTIME)
  @RestMethod(value = "POST", hasBody = true)
  public @interface POST {
    String value();
    String[] permissions() default {};
  }

  /** Make a PUT request to a REST path relative to base URL. */
  @Documented
  @Target(METHOD)
  @Retention(RUNTIME)
  @RestMethod(value = "PUT", hasBody = true)
  public @interface PUT {
    String value();
    String[] permissions() default {};
  }

  /**
   * Query parameter appended to the URL. &#64;GET|&#64;PUT|&#64;POST|&#64;DELETE(&#64;Query)
   * <p>
   * Values are converted to strings using {@link String#valueOf(Object)} and then URL encoded.
   * {@code null} values are ignored. Passing a {@link java.util.List List} or array will result in a
   * query parameter for each non-{@code null} item.
   * <p>
   * Simple Example:
   * <pre>
   * &#64;GET("/list")
   * void list(@Query("page") int page);
   * </pre>
   * Calling with {@code foo.list(1)} yields {@code /list?page=1}.
   * <p>
   * Example with {@code null}:
   * <pre>
   * &#64;GET("/list")
   * void list(@Query("category") String category);
   * </pre>
   * Calling with {@code foo.list(null)} yields {@code /list}.
   * <p>
   * Array Example:
   * <pre>
   * &#64;GET("/list")
   * void list(@Query("category") String... categories);
   * </pre>
   * Calling with {@code foo.list("bar", "baz")} yields
   * {@code /list?category=foo&category=bar}.
   * <p>
   * Parameter names are not URL encoded. Specify {@link #encodeName() encodeName=true} to change
   * this behavior.
   * <pre>
   * &#64;GET("/search")
   * void list(@Query(value="foo+bar", encodeName=true) String foobar);
   * </pre>
   * Calling with {@code foo.list("baz")} yields {@code /search?foo%2Bbar=foo}.
   * <p>
   * Parameter values are URL encoded by default. Specify {@link #encodeValue() encodeValue=false} to
   * change this behavior.
   * <pre>
   * &#64;GET("/search")
   * void list(@Query(value="foo", encodeValue=false) String foo);
   * </pre>
   * Calling with {@code foo.list("foo+foo"))} yields {@code /search?foo=foo+bar}.
   *
   * @see QueryMap
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface Query {
    /** The query parameter name. */
    String value();

    /** Specifies whether {@link #value()} is URL encoded. */
    boolean encodeName() default false;

    /** Specifies whether the argument value to the annotated method parameter is URL encoded. */
    boolean encodeValue() default true;
  }

  /**
   * Query parameter keys and values appended to the URL.
   * <p>
   * Both keys and values are converted to strings using {@link String#valueOf(Object)}. Values are
   * URL encoded and {@code null} will not include the query parameter in the URL. {@code null} keys
   * are not allowed.
   * <p>
   * Simple Example:
   * <pre>
   * &#64;GET("/search")
   * void list(@QueryMap Map&lt;String, String&gt; filters);
   * </pre>
   * Calling with {@code foo.list(ImmutableMap.of("foo", "bar", "kit", "kat"))} yields
   * {@code /search?foo=bar&kit=kat}.
   * <p>
   * Map keys representing the parameter names are not URL encoded. Specify
   * {@link #encodeNames() encodeNames=true} to change this behavior.
   * <pre>
   * &#64;GET("/search")
   * void list(@QueryMap(encodeNames=true) Map&lt;String, String&gt; filters);
   * </pre>
   * Calling with {@code foo.list(ImmutableMap.of("foo+bar", "foo+bar"))} yields
   * {@code /search?foo%2Bbar=foo}.
   * <p>
   * Map values representing parameter values are URL encoded by default. Specify
   * {@link #encodeValues() encodeValues=false} to change this behavior.
   * <pre>
   * &#64;GET("/search")
   * void list(@QueryMap(encodeValues=false) Map&lt;String, String&gt; filters);
   * </pre>
   * Calling with {@code foo.list(ImmutableMap.of("foo", "foo+foo"))} yields
   * {@code /search?foo=foo%2Bbar}.
   *
   * @see Query
   */
  @Documented
  @Target(PARAMETER)
  @Retention(RUNTIME)
  public @interface QueryMap {
    /** Specifies whether parameter names (keys in the map) are URL encoded. */
    boolean encodeNames() default false;

    /** Specifies whether parameter values (values in the map) are URL encoded. */
    boolean encodeValues() default true;
  }

  @Documented
  @Target(ANNOTATION_TYPE)
  @Retention(RUNTIME)
  public @interface RestMethod {
    String value();
    boolean hasBody() default false;
  }

  /**
   * Treat the response body on methods returning {@link retrofit.client.Response Response} as is,
   * i.e. without converting {@link retrofit.client.Response#getBody() getBody()} to {@code byte[]}.
   * <pre>
   * &#64;GET &#64;Streaming
   * </pre>
   */
  @Documented
  @Retention(RUNTIME)
  @Target(METHOD)
  public @interface Streaming {
  }
}
