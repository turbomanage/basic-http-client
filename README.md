[JavaDoc] (http://javadoc.basic-http-client.googlecode.com/git/docs/index.html) | [Blog post](http://turbomanage.wordpress.com/2012/06/12/a-basic-http-client-for-android-and-more/)

#Basic HTTP Client for Java (including App Engine and Android)#

A minimal HTTP client that uses java.net.HttpURLConnection to make requests. It features a simple interface for making Web requests. There is also an [AsyncHttpClient](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/AsyncHttpClient.java) which supports making asynchronous requests with retries and exponential backoff, and [AndroidHttpClient](https://github.com/turbomanage/basic-http-client/blob/master/http-client-android/src/main/java/com/turbomanage/httpclient/android/AndroidHttpClient.java) which wraps requests in an Android [AsyncTask](http://developer.android.com/reference/android/os/AsyncTask.html) so that requests can be safely initiated from the UI thread.

##Lightning Start##
  1. Download one of the jars at left and add it to your lib or libs (Android) folder
  1. (Android only) Add `<uses-permission android:name="android.permission.INTERNET" />` to !AndroidManifest.xml

##Quick Start##
  1. `git clone https://github.com/turbomanage/basic-http-client.git`
  1. In Eclipse, Import | Existing Projects into Workspace and point it to the basic-http-client dir
  1. To include the library in another project, add the basic-http-client project to the build path OR
  1. For Android projects, create a linked source folder named httpclient-src that points to basic-http-client/src

## Maven ##
In your Java project (including App Engine for Java), add this to your pom.xml:
```xml
<dependency>
    <groupId>com.turbomanage.basic-http-client</groupId>
    <artifactId>http-client-java</artifactId>
    <version>0.89</version>
</dependency>
```

## Gradle (Android) ##
If your Android project builds with Gradle, add this to your app's gradle.build:
```gradle
dependencies {
    ...
    implementation 'com.turbomanage.basic-http-client:http-client-android:0.89'
}
```

##Basic Usage##
```java
// Example code to login to App Engine dev server
public void loginDev(String userEmail) {
    BasicHttpClient httpClient = new BasicHttpClient("http://localhost:8888");
    ParameterMap params = httpClient.newParams()
            .add("continue", "/")
            .add("email", userEmail)
            .add("action", "Log In");
    httpClient.addHeader("name", "value");
    httpClient.setConnectionTimeout(2000);
    HttpResponse httpResponse = httpClient.post("/_ah/login", params);
}

// Example code to log in to App Engine production app
public void loginProd(String authToken) {
    BasicHttpClient httpClient = new BasicHttpClient("http://localhost:8888");
    ParameterMap params = httpClient.newParams()
            .add("auth", authToken);
    HttpResponse httpResponse = httpClient.get("/_ah/login", params);
    System.out.println(httpResponse.getBodyAsString());
}
```
##Making Asynchronous Requests##
The project includes an !AsyncTask wrapper for Android so that requests can be safely initiated on the UI thread with a callback. Example:
```java
AndroidHttpClient httpClient = new AndroidHttpClient("http://192.168.1.1:8888");
httpClient.setMaxRetries(5);
ParameterMap params = httpClient.newParams()
        .add("continue", "/")
        .add("email", "test@example.com")
        .add("action", "Log In");
httpClient.post("/_ah/login", params, new AsyncCallback() {
    @Override
    public void onSuccess(HttpResponse httpResponse) {
        System.out.println(httpResponse.getBodyAsString());
    }
    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }
});
```

## App Engine for Java ##
The basic-http-client project simply wraps `java.net.UrlConnection`. On App Engine, this in turn wraps the App Engine URLFetch API. The net result is that you can use version 0.89 or later to make outbound requests from your server application.

## Cookies ##
In [AndroidHttpClient](https://github.com/turbomanage/basic-http-client/blob/master/http-client-android/src/main/java/com/turbomanage/httpclient/android/AndroidHttpClient.java), `java.net.CookieManager` is enabled by default above API version 8 (when it was introduced) in order to save cookies between requests and resend them just like a browser does.

Prior to version 0.89, `BasicHttpClient` would also automatically enable `java.net.CookieManager`. However, this is not supported on App Engine, so in version 0.89 and later, you must explicitly enable cookie support. Before making the first request, simply call the static method
```java
AbstractHttpClient.ensureCookieManager();
```

##Understanding the Code##
The key method is [AbstractHttpClient](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/AbstractHttpClient.java).doHttpMethod(String path, [HttpMethod](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/HttpMethod.java) httpMethod, String contentType, byte[] content). This is the method that actually drives each request, catches any exceptions, and rethrows them wrapped in an [HttpRequestException](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/HttpRequestException.java). It delegates most of the request lifecycle to a [RequestHandler](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/RequestHandler.java) instance. To override the default behaviors (say, to provide your own error handler or custom stream reader/writer), simply extend the [BasicRequestHandler](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/BasicRequestHandler.java) like this:

```java
        BasicHttpClient client = new BasicHttpClient(baseUrl, new BasicRequestHandler() {
            @Override
            public boolean onError(HttpResponse res, Exception e) {
                e.printStackTrace();
                return true; // if recoverable
            }
        });
```

##Advanced Usage##
To create an async client for another platform, simply subclass [AsyncHttpClient](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/AsyncHttpClient.java) and provide it with an instance of a [AsyncRequestExecutorFactory](https://github.com/turbomanage/basic-http-client/blob/master/http-client-java/src/main/java/com/turbomanage/httpclient/AsyncRequestExecutorFactory.java) suitable for your platform. See the com.turbomanage.httpclient.android package for the Android implementation which uses [AsyncTask](http://developer.android.com/reference/android/os/AsyncTask.html).
