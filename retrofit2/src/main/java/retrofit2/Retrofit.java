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
package retrofit2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.List;
import rx.functions.*;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Retrofit {
  /**
   * baseUrl
   */
  String value() default "";

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.TYPE)
  public @interface Builder {
  }

  /**
   * Specifies that the annotated method is a validation method. The method should be a non-private
   * no-argument method in an Retrofit class. It will be called by the {@code build()} method of
   * the {@link Builder @Retrofit.Builder} implementation, immediately after constructing the new
   * object. It can throw an exception if the new object fails validation checks.
   */
  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface Validate {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface GET {
    String value() default "";
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface POST {
    String value();
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface PUT {
    String value();
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface DELETE {
    String value() default "";
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface HEAD {
    String value() default "";
    String[] permissions() default {};
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Body { // For @Post(@Body JsonString/Model.toJsonString()) in mimetype of "application/json; charset=utf8-8"
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface FormUrlEncoded { // For @FormUrlEncoded @POST(@Field)
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface Multipart { // For @Multipart @PUT(@Part)
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface Headers { // For @Headers @GET|@PUT|@POST|@DELETE, @Headers class Retrofit
    String[] value();
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface RetryHeaders { // For @RetryHeaders @GET|@PUT|@POST|@DELETE, @RetryHeaders class
    String[] value();
    Class<? extends Throwable>[] exceptions() default Throwable.class;
    //Class<? extends ErrorHandler> errorHandler() default ErrorHandler.class;
    //Class<? extends ErrorHandler> onRetry() default ErrorHandler.class;
    //Class<? extends ErrorHandler> onNext() default ErrorHandler.class;
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface ErrorHandler { // For @ErrorHandler @GET|@PUT|@POST|@DELETE, @ErrorHandler class
    Class<? extends retrofit.ErrorHandler> value() default retrofit.ErrorHandler.class;
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface Converter { // For @Converter @GET|@PUT|@POST|@DELETE, @Converter class
    Class<? extends retrofit.converter.Converter> value() default retrofit.converter.Converter.class;
  }

  // TODO aspect alternative?
  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.METHOD, ElementType.TYPE})
  public @interface LogLevel { // For @LogLevel @GET|@PUT|@POST|@DELETE, @LogLevel class
    retrofit.RestAdapter.LogLevel value() default retrofit.RestAdapter.LogLevel.NONE;
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Header { // For @GET|@PUT|@POST|@DELETE(@Header)
    String value();
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.METHOD)
  public @interface Streaming { // For @GET @Streaming
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Path { // For @GET|@PUT|@POST|@DELETE(@Path)
    String value() default "";
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Query { // For @GET|@PUT|@POST|@DELETE(@Query)
    String value() default "";
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Field { // For @FormUrlEncoded @POST(@Field)
    String value() default "";
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface Part { // For @Multipart @PUT(@Part)
    String value() default "";
    String mimeType() default ""; // "text/plain"|"text/plain; charset=UTF-8"|"text/x-markdown; charset=utf-8"
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface QueryMap {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target(ElementType.PARAMETER)
  public @interface QueryBundle {
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.TYPE, ElementType.METHOD})
  public @interface QueryBinding {
      String value() default "";
      Class<? extends Bindable> binder() default Bindable.class;
  }

  @Retention(RetentionPolicy.RUNTIME) // RUNTIME, keep annotation for anothor processor
  @Target({ElementType.TYPE, ElementType.METHOD})
  public @interface RequestInterceptor {
      Class<? extends retrofit.RequestInterceptor> value() default retrofit.RequestInterceptor.class;
  }

  public static interface Bindable<T> extends Func1<T, String> {
      public String call(T t);
  }

}
