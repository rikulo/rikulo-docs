#Request Filtering

A filter dynamically intercepts requests and responses to transform or use the contained information. Typical functions of filters include but not limited to:

* Authentication
* Logging and auditing
* Data compressing
* Localization

##Filter Mapping

To map a filter to a URI, you have to specify it in a map passed to the `filterMapping` argument of the constructor of [StreamServer](api:stream). For example,

    new StreamServer(filterMapping: {
      "/.*": logFilter
    }, uriMapping: yourUriMapping, errorMapping: yourErrorMapping).start();

##Filter Signature

The signature of a filter is as follows:

    void foo(HttpConnect connect, void chain(HttpConnect conn)) {
      ...
    }

A filter chain passed to a filter as the `chain` argument. It provides a mechanism for invoking a series of filters and the default request handling. If you call back the filter chain, the following filters that match the request URI will be called. If no more matched filter is found, the default request handling will take place (refer to [Request Routing](Request_Routing.md)).

Here is the pseudo code of an authentication filter:

    void authenticate(HttpConnect connect, void chain(HttpConnect conn)) {
      if (isAuthenticated(connect))
        chain(connect);
      else
        connect.forward("/login");
    }

> For a runnable example, you can refer to the [features](source:test) example.

##Connection Wrapper

You can creates a connect wrapper by use of [HttpConnectWrapper](api:stream) to, say, overrides the output stream.
