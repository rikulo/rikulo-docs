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

> For handling exceptions and missed resouces, please refer to [Error Handling](../Configuration/Error_Handling.md).

###Naming the Matched Groups of URI Patterns

You can name the matched groups of URI patterns by prefixing with the name and a colon.  For example, 

    "/user/(name:[^/]*)"

It matches any URL starting with `/user/`, and the first matched group (the element following `/user/`) will be called `name` and stored in [HttpConnect.dataset](api:stream).

For example, if the request's URI is `/usr/foo`, then `connect.dataset['name']`  will be `foo`.

###Coerce the Matched Groups into Objects

Instead of accessing [HttpConnect.dataset](api:stream) directly, you can use [ObjectUtil.inject()](http://api.rikulo.org/commons/latest/rikulo_mirrors/ObjectUtil.html#inject) to convert the matched groups into an object. For example,

    ObjectUtil.inject(new UserInfo(), connect.dataset, silent: true)
      .then((UserInfo userInfo) {
        //...handle userInfo.name
      }).catchError(connect.error);

For more information, please refer to the [Form Handling](Form_Handling.md) section.
