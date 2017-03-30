# Simple tool for testing REST APIs in Java

## Motivation

Developers are used to write unit tests and unit test frameworks are well developed.
On the other hand java frameworks for REST based integration testing usually end up overly complex, editors do not have all the features developers are looking for and do not reuse the tools developers are used to.

This library offers a very simple tool for extending standard unit test frameworks (like jUnit) to add a very simplistic REST client as
well as wrappers for URLs and REST responses designed to simplify integration in assertion frameworks.

## How to use
 
### URLs
 
URLs are a big part of this library. To create a Url, simply instantiate it:

```java
Url testUrl = new Url("/some/path");
```

URLs are given as relative to the server. This allows to write code agnostic of where the actual server will actually listen.

### URLs with placeholders

Urls also support path parameters using placeholders:

```java
Url testUrl = new Url("/some/${placeholder}/in/path/");
```

Values for each placeholder can be provided later preventing the person writing the test to have to concatenate string over and over:

```java
testUrl.addPathParameter("placeholder", 12);
```

### URLs with query parameters

It is very easy to add query parameters without having to worry about delimiters. Those values can be defined at any point in time as well as redefined if necessary. 

```java
Url testUrl = new Url("/some/path");
testUrl.addQueryParameter("limit", 12);
testUrl.addQueryParameter("user", "bob");
```

### Calling an API

Once you have a URL, you can start using it to connect to the server:

```java
Url testUrl = new Url("/some/path");
RestIt rest = new RestIt("http://my.server:4567");
RestResponse response = rest.GET(testUrl);
```

If there is any problem connecting to the server, an AssertionError will be raised with a clear message.
Otherwise the response object will be ready for use.

### Checking HTTP status

Checking for status is a very common operation when testing REST calls. And it is very easy to do:

```java
RestResponse response = rest.GET(testUrl);
response.assertStatus(200);
```

If the status was 200 nothing will happen, if it was not an AssertionError will be raised with a clear message.

### Checking response time

It is very easy to check if the response came within a specified amount of milliseconds:
```java
RestResponse response = rest.rest(testUrl);
response.assertWithin(10_000);
```

If the response took less than 10 seconds to be received nothing will happen, if it was not an AssertionError will be raised with a clear message.

### Getting the actual response entity

To check for the actual response, it is also very easy:

```java
RestResponse response = rest.GET(testUrl);
MyObject entity = response.getEntity(MyObject.class);
```

If the entity returned by the server could not be deserialized properly as a "MyObject" instance, an assertionError will be raised with a clear message.

### Getting the full response

If you need to check for headers, coockies, ... you can retrieve the full jaxrs response like this:

```java
RestResponse response = rest.GET(testUrl);
Reponse jaxrsResponse = response.getResponse();
```

## License

Released under [MIT](LICENSE). Copyright (c) Clerc Mathias