#Request Forwarding and Inclusion

As described in [Request Handling](Request_Handling.md), chaining the request handlers are as straightforward as chaining the `Future` objects.

Here we describe how to chain a request handler to another URI: forwarding and inclusion.

##Forwarding

To forward the request to another URI, you can use [HttpConnect.forward](api:stream).

For example, we can forward to the login page if the current user is not authenticated as follows:

    Future foo(HttpConnect connect) {
      if (!isAuthenticated(connect))
        return connect.forward("/login");

      //...handle the request
    }

##Inclusion

To include the output of another URI, you can use [HttpConnect.include](api.connect). For example,

    Future foo(HttpConnect connect) {
      connect.response.write("The content shown before the included page...");
      return connect.include("/webapp/another").then((_) {
        connect.response.write("The content shown after the included page...");
      });
    }

> To protect a URI from accessed directly by the user, you can put it under `/webapp`. As shown in the previous example, `/webapp/another` can be accessed only by inclusion (and forwarding).

##Difference between forward and inclusion

As their names suggested, you shall use [HttpConnect.forward](api:stream) to forward a request to another URI, while use [HttpConnect.include](api:stream) to include the output of another URI(s).

From the technical point of view, [HttpConnect.forward](api:stream) and [HttpConnect.include](api:stream) are the same, except:

* The included request handler won't be able to generate any HTTP headers. Any updates to HTTP headers in the included request handler are simply ignored.

This subtle difference helps you to include any request handler even if it will update HTTP headers. On the other hand, you can't write any content to [HttpResponse](dart:io) before invoking [HttpConnect.forward](api:stream).

> Notice that [HttpResponse](dart:io) will throw an exception if you update the HTTP headers after writing some output. In other words, you can update the HTTP headers only before the first invocation of `HttpResponse.write()`.
