#Error Handling

To configure the handling of errors, you have to prepare a map as the `errorMapping` parameters when instantiating [StreamServer](api:stream). For example,

    new StreamServer(uriMapping: fooUriMapping, errorMapping: fooErrorMapping).run();

Each entry of the map is a mapping of a status code (such as 404) or an exception to a handler. For example,

    new StreamServer(errorMapping: {
      "404": "/webapp/404.html",
      "500": "/webapp/500.html",
      "300": fooHandler,
      "fooLib.Exception": anotherHandler
    }...

> For a runnable example, you can refer to the [features](source:test) example.

##The Error Handlers

A typical handler is a URI that the request shall be forwarded to. For example,

    errorMapping: {
      "404": "/webapp/404.html"
      ...

In addition to URI, you can specify a handler directly if you'd like:

    errorMapping: {
      "404": e404View //assume you have a template called e404View.rsp.html
      ...

###URI Is Forwarded

Notice that if you specify an URI, Stream actually [forwards](../Request_and_Response/Request_Forwarding_and_Inclusion.md) the request to it. It means the request will go over [the request routing](../Request_and_Response/Request_Routing.html) again. Thus, you can map it to the right handler as normal request if you want to generate the content dynamically.

    uriMapping: { //request routing
      "/webapp/404.html": e404View //assume you have a template called e404View.rsp.html
      ...

Of course, if `404.html` is a static HTML file under `/webapp`, you don't need to map it to a handler.

Since it is forwarded, [HttpRequest.uri](dart:io) is not the original URI. To retrieve, you have to access it from [HttpConnect.forwarder](api:stream). For example, in RSP, you can write:

    Not found: [= connect.request.forwarder.uri]

###The Error Detailed Information

To retrieve the exception, you can access [HttpConnect.errorDetail](api:stream). For example, in RSP, you can write:

    <h1>500: [= connect.errorDetail.error.message]</h1>
    <pre><code>[= connect.errorDetail.stackTrace]</code></pre>

##Mapping Error Codes

The error code can be one of [HTTP status codes](http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html). To map an error code, you can specify the number directly or the string representing the number as shown in above examples.

> Notice that Dart allows only string literals as the keys of a map literal.

For example, if your application throws an instance of [Http404](api:stream), then Stream will check if any URI or handler is mapped to the 404 code.

##Mapping Exceptions

You can map an exception to an URI or a handler too. For example, if you'd like to handle all exceptions that are instances of, say, `StupidError` and its subclasses, you can do as follows:

    errorMapping: {
      "foo.StupidError": stupidErrorHandler //assume StupidError is in the library called foo
      ...

Then, if the application throws an instance of `StupidError`, it will be delegated to the handler called `stupidErrorHandler` for handling.

> Notice that you have to specify both the library's name and the class's name separated with a dot.