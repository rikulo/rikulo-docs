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

    Future foo(HttpConnect connect, Future chain(HttpConnect conn)) {
      ...
    }

A filter chain passed to a filter as the `chain` argument. It provides a mechanism for invoking a series of succeeding filters and the default request handling. If you call back the filter chain, the succeeding filters that match the request URI will be called. If no more matched filter is found, the default request handling will take place (refer to [Request Routing](Request_Routing.md)).

Here is the pseudo code of an authentication filter:

    Future authenticate(HttpConnect connect, Future chain(HttpConnect conn)) {
      if (isAuthenticated(connect))
        return chain(connect);
      else
        return connect.forward("/login");
    }

> For a runnable example, you can refer to the [features](source:test) example.

##Connection Wrapping

You can creates a connect wrapper by use of [HttpConnect.buffer](api:stream), [HttpConnect.chain](api:stream) or [HttpConnectWrapper](api:stream). For example, you can redirect the output to a string buffer and then process it, as follows: 

    Future foo(HttpConnect connect) {
      final buffer = new StringBuffer();
      return another(connect.buffer(connect, buffer)).then((_) {
        connect.response.write(_process(buffer.toString()));
          //assume the application has a function called _process
      });
    }
