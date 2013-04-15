#Request Routing

To map a request handler to a URI, you have to specify it in a map passed to the `uriMapping` argument of the constructor of [StreamServer](api:stream). For example,

    void main() {
      new StreamServer(uriMapping: {
        "/server-info": serverInfo,
        "/order/.*": order,
        "/user/.*": user
      }).start();
    }

For sake of management, it is suggested to put the mapping in a separated Dart file named `config.dart` for holding all configurations. For example,

    //config.dart
    var _mapping = {
      "/server-info": serverInfo,
      "/order/.*": order,
      "/user/.*": user
    };

Then, you can start your server as follows:

    //main.dart
    void main() {
      new StreamServer(uriMapping: _mapping).start();
    }

The server's administrator can then change the configuration in `config.dart` without much knowledge of Dart language.

##URI Patterns

A URI pattern is *a regular expression*. It must start with `'/'`. It is used to match [HttpRequest.uri](dart:io) when a request is received (including [including or forwarding](Request_Forwarding_and_Inclusion.md)).

Notice the regular expression must match the complete URI. For example, `/server-info` doesn't match `/server-info/`.

> For handling exceptions and missed resources, please refer to [Error Handling](../Configuration/Error_Handling.md).

###Name the Matched Groups of URI Patterns

You can name the matched groups of URI patterns by prefixing with the name and a colon.  For example, 

    "/user/(name:[^/]*)"

It matches any URL starting with `/user/`, and the first matched group (the element following `/user/`) will be called `name` and stored in [HttpConnect.dataset](api:stream).

For example, if the request's URI is `/usr/foo`, then `connect.dataset['name']`  will be `foo`.

###Coerce the Matched Groups into Objects

Instead of accessing [HttpConnect.dataset](api:stream) directly, you can use [ObjectUtil.inject()](http://api.rikulo.org/commons/latest/rikulo_mirrors/ObjectUtil.html#inject) to convert the matched groups into an object. For example,

    return ObjectUtil.inject(new UserInfo(), connect.dataset, silent: true)
      .then((UserInfo userInfo) {
        //...handle userInfo.name
      });

For more information, please refer to the [Form Handling](Form_Handling.md) section.

###Map Request Methods

In [RESTful](http://en.wikipedia.org/wiki/Representational_state_transfer) services, it is common to map [the request methods](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html) to different REST operations. To do so, you can prefix the URI pattern with the method and a colon (no whitespace is allowed). For example,

    new StreamServer(uriMapping: {
      "get:/user/(name:[^/]*)": getUser,
      "delete:/user/(name:[^/]*)": deleteUser,
      "put:/user/(name:[^/]*)": updateUser,
      "get:/user": listUser,
      "post:/user": createUser
    });

##Map URI to a Request Handler

A URI is typically mapped to a request handler (aka., renderer). A request handler is a function (aka., a closure). Its first argument must be [HttpConnect](api:stream).  For more information, please refer to [Request Handling](Request_Handling.md).

##Map URI to Another URI

In additions to mapping a URI to a request handler, you can map it to another URI. It is usefully if you want to delegate links from one to another. For example,

    new StreamServer(uriMapping: {
      "/old-link(info:.*)": "/new-link(info)"
    ...

As shown, you can specify the matched group in the forwarded URI too. To do so, you can simply enclose the name with a parenthesis, such as `(info)` in the previous example.

> Notice that `(name)` retrieves the value from [HttpConnect.dataset](api:stream). Thus, it can be any value set by your application, such as filters, includers and forwarders.
